package com.ishanitech.ipalikawebapp.controller;


import com.ishanitech.ipalikawebapp.configs.properties.UploadDirectoryProperties;
import com.ishanitech.ipalikawebapp.dto.LgMunicipalityDTO;
import com.ishanitech.ipalikawebapp.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalikawebapp.dto.OnlineSifarisFormFieldsDTO;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.LocalGovernmentService;
import com.ishanitech.ipalikawebapp.service.OnlineSifarisService;
import com.ishanitech.ipalikawebapp.serviceImpl.OnlineSifarisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/online-sifaris")
@Controller
@Slf4j
public class OnlineSifarisController {

    private final OnlineSifarisService onlineSifarisService;
    private LocalGovernmentService localGovernmentService;
    private final UploadDirectoryProperties uploadDirectoryProperties;
    public  OnlineSifarisController(OnlineSifarisService onlineSifarisService, LocalGovernmentService localGovernmentService, UploadDirectoryProperties uploadDirectoryProperties){
        this.onlineSifarisService = onlineSifarisService;
        this.localGovernmentService = localGovernmentService;
        this.uploadDirectoryProperties = uploadDirectoryProperties;
    }

    @GetMapping("/online-form")
    public String getOnlineFormList() {
        return "public/onlineSifarisForm/online-form-list";
    }

    @GetMapping("/form/{formId}")
    public String getFullFormDetailById(@PathVariable("formId") Integer formId,  Model model, @AuthenticationPrincipal UserDTO user) {
        // Added for tabbed layout
        List<OnlineSifarisFormFieldsDTO> formDetails = onlineSifarisService.getFullFormDetailById(formId, null);
        model.addAttribute("questionAndOptions", formDetails);
        model.addAttribute("provinceList", localGovernmentService.getAllProvinces());
        model.addAttribute("DistrictsList", localGovernmentService.getAllDistricts());
        model.addAttribute("MunicipalitiesList", localGovernmentService.getAllMunicipalities());
        model.addAttribute("wardList", localGovernmentService.getAllWards());

        return "public/onlineSifarisForm/online_sifaris_form";
    }

    @PostMapping("/form/add")
    public @ResponseBody int getAddOnlineSifarisForm(@RequestBody OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswerDTO, Model model, @AuthenticationPrincipal UserDTO user) {
        // Added for tabbed layout
        log.info(onlineSIfarisFormAnswerDTO.toString());
        try {
            onlineSifarisService.addOnlineSifarisForm(onlineSIfarisFormAnswerDTO, null);
            return 1;
        } catch(Exception e ) {
            e.printStackTrace();
            log.info(e.getMessage());
           return 0;
        }
    }

    @RequestMapping(value = "/form/image", method = RequestMethod.POST)
    public @ResponseBody String addImage(MultipartHttpServletRequest request, @AuthenticationPrincipal UserDTO user) {

        log.info("=+++++++++*++++++>> " + request.getFile(request.getParameter("imgIndex")));
        String inputTagName = request.getParameter("imgIndex");
        String fileName = request.getParameter("fileName");
        Path rootLocation = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory());
        try {
            MultipartFile photo = request.getFile(inputTagName);

            String imageName = fileName;

            //For copying the file to upload directory
            Files.copy(photo.getInputStream(), rootLocation.resolve(imageName));

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

//			advertisementService.addAdvertisementTaxImage(multipartFile, imageName);
            onlineSifarisService.addOnlineSifarisFormImage(multipartFile, imageName);
        } catch(Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        try {
            String imageName = fileName;
            Path path = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
			Files.delete(path);
        }
//		catch(NoSuchFileException e) {
//			System.out.println("No such file/directory exists");
//		}
//		catch(DirectoryNotEmptyException e) {
//			System.out.println("Directory is not empty");
//		}
        catch(Exception e) {
            System.out.println("Invalid Permissions.");
            e.printStackTrace();
        }
        return "1";
    }


    @RequestMapping(value = "/form/image/edit", method = RequestMethod.PUT)
    public @ResponseBody String EditSifarisImage(MultipartHttpServletRequest request, @AuthenticationPrincipal UserDTO user) {

        log.info("=+++++++++*++++++>> " + request.getFile(request.getParameter("imgIndex")));
        String inputTagName = request.getParameter("imgIndex");
        String fileName = request.getParameter("fileName");
        Path rootLocation = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory());
        try {
            MultipartFile photo = request.getFile(inputTagName);

            String imageName = fileName;

            //For copying the file to upload directory
            Files.copy(photo.getInputStream(), rootLocation.resolve(imageName));

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

//			advertisementService.addAdvertisementTaxImage(multipartFile, imageName);
            onlineSifarisService.editOnlineSifarisFormImage(multipartFile, imageName);
        } catch(Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        try {
            String imageName = fileName;
            Path path = Paths.get(uploadDirectoryProperties.getTempFileUploadingDirectory() + imageName);
            Files.delete(path);
        }
//		catch(NoSuchFileException e) {
//			System.out.println("No such file/directory exists");
//		}
//		catch(DirectoryNotEmptyException e) {
//			System.out.println("Directory is not empty");
//		}
        catch(Exception e) {
            System.out.println("Invalid Permissions.");
            e.printStackTrace();
        }
        return "1";
    }
}
