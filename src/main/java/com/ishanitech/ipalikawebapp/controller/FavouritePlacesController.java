package com.ishanitech.ipalikawebapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ishanitech.ipalikawebapp.configs.properties.UploadDirectoryProperties;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;
import com.ishanitech.ipalikawebapp.service.SurveyorService;
import com.ishanitech.ipalikawebapp.service.WardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/favourite-place")
@Controller
public class FavouritePlacesController {

	private final FavouritePlacesService favouritePlacesService;
	private final WardService wardService;
	private final UploadDirectoryProperties uploadDirectoryProperties;
	private final SurveyorService surveyorService;
	
	public FavouritePlacesController(FavouritePlacesService favouritePlacesService, WardService wardService, UploadDirectoryProperties uploadDirectoryProperties, SurveyorService surveyorService) {
		this.favouritePlacesService = favouritePlacesService;
		this.wardService = wardService;
		this.uploadDirectoryProperties = uploadDirectoryProperties;
		this.surveyorService = surveyorService;
	}
	
	@GetMapping
	public String getFavouritePlaces(Model model) {
		model.addAttribute("wards", wardService.getAllWards());
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		model.addAttribute("favouritePlaceList", favouritePlacesService.getAllFavouritePlaces().getData());
		model.addAttribute("surveyors", surveyorService.getAllSurveyors());
		return "public/favourite-place";
	}
	
	@GetMapping("/surveyor")
	public String getFavouritePlacesBySurveyor(Model model, @AuthenticationPrincipal UserDTO currentUser) {
		model.addAttribute("wards", wardService.getAllWards());
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		model.addAttribute("favouritePlaceList", favouritePlacesService.getAllFavouritePlacesBySurveyor(currentUser.getUserId()).getData());
		return "private/common/favourite-place-surveyor";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@PostMapping
	public @ResponseBody int addFavouritePlace(@RequestBody FavouritePlaceDTO favPlaceDTO, @AuthenticationPrincipal UserDTO user, HttpServletRequest httpServletRequest) {
		log.info(favPlaceDTO.toString());
		try {
			favouritePlacesService.addFavouritePlaceInfo(favPlaceDTO, user.getToken());
			return 1;
		} catch(Exception e ) {
			e.printStackTrace();
			log.info(e.getMessage());
			return 0;
		}
	}
	
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@PutMapping("/{favPlaceId}")
	public @ResponseBody int editFavouritePlace(@RequestBody FavouritePlaceDTO favPlaceDTO, @PathVariable("favPlaceId") String favPlaceId, @AuthenticationPrincipal UserDTO user, HttpServletRequest httpServletRequest) {
		log.info(favPlaceDTO.toString());
		try {
			favouritePlacesService.editFavouritePlaceInfo(favPlaceDTO, favPlaceId, user.getToken());
			return 1;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@PostMapping("/image")
	public @ResponseBody String addFavImage(MultipartHttpServletRequest request, @AuthenticationPrincipal UserDTO user) {
		String inputTagName = request.getParameter("imgIndex");
		String fileName = request.getParameter("fileName");
		Path rootLocation = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory());
		try {
			MultipartFile favPlaceImage = request.getFile(inputTagName);
			
			String imageName = fileName;
			
			//For copying the file to upload directory
			Files.copy(favPlaceImage.getInputStream(), rootLocation.resolve(imageName));
			
			//For retrieving saved multipart file
			File file = new File(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
			
			InputStream input = null;
			OutputStream os = null;
			
			try {
				input = new FileInputStream(file);
				os = fileItem.getOutputStream();
				IOUtils.copy(input, os);
			} catch (IOException ex) {
				
			} finally {
				if(input != null) {
					input.close();
				}
				if(os != null) {
					os.close();
				}
			}
			
			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			
			favouritePlacesService.addFavouritePlaceImage(multipartFile, imageName, user.getToken());
			
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		try {
			String imageName = fileName;
			Path path = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
			Files.delete(path);
		} catch(NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch(DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty");
		} catch(IOException e) {
			System.out.println("Invalid Permissions.");
			e.printStackTrace();
		}
		return "1";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@RequestMapping(value = "/editImage", method = RequestMethod.POST)
	public @ResponseBody String addEditedImage(MultipartHttpServletRequest request, @AuthenticationPrincipal	UserDTO user) {
		String inputTagName = request.getParameter("imgIndex");
		String fileName = request.getParameter("fileName");
		Path rootLocation = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory());
		try {
			MultipartFile favPlaceImage = request.getFile(inputTagName);
			
			String imageName = fileName;
			
			//For copying the file to upload directory
			Files.copy(favPlaceImage.getInputStream(), rootLocation.resolve(imageName));
			
			//For retrieving saved multipart file
			File file = new File(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
			
			InputStream input = null;
			OutputStream os = null;
			
			try {
				input = new FileInputStream(file);
				os = fileItem.getOutputStream();
				IOUtils.copy(input, os);
			} catch (IOException ex) {
				
			} finally {
				if(input != null) {
					input.close();
				}
				if(os != null) {
					os.close();
				}
			}
			
			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			
			favouritePlacesService.addEditedFavouritePlaceImage(multipartFile, imageName, user.getToken());
			
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		try {
			String imageName = fileName;
			Path path = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
			Files.delete(path);
		} catch(NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch(DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty");
		} catch(IOException e) {
			System.out.println("Invalid Permissions.");
			e.printStackTrace();
		}
		return "1";
	}
	
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{favPlaceId}")
	public @ResponseBody int deleteFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId, @AuthenticationPrincipal UserDTO user) {
		try {
		favouritePlacesService.deleteFavouritePlacebyPlaceId(favPlaceId, user.getToken());
		return 1;
		} catch(Exception e ) {
			e.printStackTrace();
			log.info(e.getMessage());
			return 0;
		}
	}
	
	@GetMapping("/{favPlaceId}")
	public String getFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId,
			@AuthenticationPrincipal UserDTO user, Model model) {
		Response<FavouritePlaceDTO> favouritePlaceResponse = (Response<FavouritePlaceDTO>) favouritePlacesService.getFavouritePlaceByPlaceId(favPlaceId);
		model.addAttribute("favouritePlaceInfo", favouritePlaceResponse.getData());
		return "public/favourite-place-details";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@GetMapping("/add")
	public String getFavouritePlaceEntryView(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("wardList", wardService.getAllWards());
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		return "private/common/add-favourite-place";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@GetMapping("/edit/{favPlaceId}")
	public String getFavouritePlaceEditView(@PathVariable("favPlaceId") String favPlaceId, Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("wardList", wardService.getAllWards());
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		model.addAttribute("favPlaceObj", favouritePlacesService.getFavouritePlaceByPlaceId(favPlaceId).getData());
		model.addAttribute("favPlaceId", favPlaceId);
		return "private/common/edit-favourite-place";
	}
	
	
	@GetMapping("/search")
	public @ResponseBody List<FavouritePlaceDTO> getFavouritePlaceBySearchKey(HttpServletRequest request, @RequestParam("searchKey") String searchKey, @RequestParam("wardNo") String wardNo, @RequestParam("placeType") String placeType) {
		log.info("searchKey----->" + searchKey);
		log.info("wardNo----->" + wardNo);
		return favouritePlacesService.searchFavouritePlaceByKey(request, searchKey, wardNo, placeType);
	}
	
	
	@GetMapping("/ward")
	public @ResponseBody List<FavouritePlaceDTO> getFavouritePlaceByWard(@RequestParam("wardNo") String wardNo, HttpServletRequest request) {
		log.info("WardNo---->" + wardNo);
		log.info("PagedLimited---->" + request.getParameter("pageSize"));
		return favouritePlacesService.searchFavouritePlaceByWard(request, wardNo);
	}
	
	@GetMapping("/placeType")
	public @ResponseBody List<FavouritePlaceDTO> getFavouritePlaceByPlaceType(@RequestParam("placeType") String placeType, HttpServletRequest request) {
		log.info("PagedLimited---->" + request.getParameter("pageSize"));
		return favouritePlacesService.searchFavouritePlaceByPlaceType(request, placeType);
	}
	
	@GetMapping("/surveyor-id")
	public @ResponseBody List<FavouritePlaceDTO> getFavouritePlaceBySurveyor(@RequestParam("surveyor") String surveyor, HttpServletRequest request) {
		return favouritePlacesService.searchFavouritePlaceBySurveyor(request, surveyor);
	}
	
	@GetMapping("/pageLimit")
	public @ResponseBody List<FavouritePlaceDTO> getFavouritePlaceByPageLimit(@RequestParam("wardNo") String wardNo,HttpServletRequest request) {
		log.info("PagedLimited---->" + request.getParameter("pageSize"));
		return favouritePlacesService.getFavouritePlaceByPageLimit(request, wardNo);
	}
	
	@GetMapping("/nextLot")
	public @ResponseBody List<FavouritePlaceDTO> getNextLotResidents(HttpServletRequest request) {
		return favouritePlacesService.getNextLotFavouritePlace(request);
	}
	
	@GetMapping("/sortBy")
	public @ResponseBody List<FavouritePlaceDTO> getSortedResidents(HttpServletRequest request) {
		return favouritePlacesService.getSortedFavouritePlace(request);
	}
	
	
}
