package com.ishanitech.ipalikawebapp.controller;

import com.ishanitech.ipalikawebapp.dto.*;
import com.ishanitech.ipalikawebapp.service.RegistrationFormCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/e-governance-dashboard")
@Controller
public class RegistrationFormCountController {

    private final RegistrationFormCountService registrationFormCountService;


    public RegistrationFormCountController(RegistrationFormCountService registrationFormCountService) {
        this.registrationFormCountService = registrationFormCountService;
    }


    @GetMapping
    public String eGovernancePortalView(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDTO user) {
        List<RegistrationFormCountDTO> registrationFormCounts = registrationFormCountService.getRegistrationFormCount(user.getToken());
        model.addAttribute("registrationFormCounts", registrationFormCounts);
        return "private/common/e-governance-dashboard";
    }

    @GetMapping("/{formId}/list")
    public String getAllCertificates(@PathVariable("formId") Integer formId, Model model, @RequestParam(defaultValue = "0") Integer status) {
//		model.addAttribute("wards", wardService.getAllWards());

//        Hashtable<String, ElectricityConnectionDTO> ds = new Hashtable();
//        HashMap<String ,ElectricityConnectionDTO> hs = new HashMap<>();
//        TreeMap<String, ElectricityConnectionDTO> tm = new TreeMap<>();
//        HashSet<ElectricityConnectionDTO> ec = new HashSet<>();
//        TreeSet<ElectricityConnectionDTO> ecd = new LinkedHashMap<>();
        List<OnlineSifarisDetailsFieldDTO> certificates = registrationFormCountService.getAllCertificates(formId).getData();
        List<OnlineSifarisDetailsFieldDTO> filteredCertificates = new ArrayList<>();
        switch (status){
            case 0:
                filteredCertificates = certificates;
                break;
            case 1:
                filteredCertificates = certificates.stream().filter(c -> c.getStatus() == 1).collect(Collectors.toList());
                break;
            case 2:
                filteredCertificates = certificates.stream().filter(c -> c.getStatus() == 2).collect(Collectors.toList());
                break;
            case 3:
                filteredCertificates = certificates.stream().filter( c -> c.getStatus() == 3).collect(Collectors.toList());
                break;
            case 4:
                filteredCertificates = certificates.stream().filter(c -> c.getStatus() == 4).collect(Collectors.toList());
                break;
            case 5:
                filteredCertificates = certificates.stream().filter( c -> c.getStatus() == 5).collect(Collectors.toList());
                break;
            default:
                filteredCertificates = null;
        }
        model.addAttribute("certificates", filteredCertificates);
        return "private/common/onlineSifaris/list/certificates_list";
    }

    @PutMapping("/updateCertificateStatus/{formId}")
    public @ResponseBody int updateEntityStatus(
            @RequestBody SifarisMetaData sifarisMetaData,
            @AuthenticationPrincipal UserDTO loggedInUser) {
        try {

            registrationFormCountService.updateStatusLevel(sifarisMetaData, loggedInUser.getToken());

            return 1;
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @GetMapping("/view/formDetail/{tokenId}/{formId}")
    public String getSifarisDetailView(@PathVariable("tokenId") String tokenId,
                                       @PathVariable("formId") Integer formId,
                                       Model model,
                                       @AuthenticationPrincipal UserDTO loggedInUser){
        List<SifarisDetailDTO> onlineSifaris = registrationFormCountService.getSifarisDetailView(tokenId, formId, loggedInUser.getToken());
        model.addAttribute("certificateFields", onlineSifaris);
        model.addAttribute("tokenId", tokenId);
        model.addAttribute("formId", formId);
        model.addAttribute("status", registrationFormCountService.getSifarisPresentStatus(tokenId, formId, loggedInUser.getToken()));
        return "private/common/onlineSifaris/detail/certificate_detail";
    }

    @GetMapping("/edit/{tokenNumber}/{formId}")
    public String getSifarisEditView(@PathVariable("formId") Integer formId,
                                     @PathVariable("tokenNumber") String tokenId,
                                     @AuthenticationPrincipal UserDTO loggedInUser,
                                     Model model){
        List<SifarisDetailDTO> onlineSifaris = registrationFormCountService.getSifarisDetailView(tokenId, formId, loggedInUser.getToken());
        model.addAttribute("questionAndOptions", onlineSifaris);
        model.addAttribute("formId", formId);
        model.addAttribute("tokenId", tokenId);
        return "private/common/onlineSifaris/edit/certificate_edit";
    }

    @PutMapping("/sifaris/edit/{tokenId}/{formId}")
    public @ResponseBody int editSifaris(
            @RequestBody OnlineSIfarisFormAnswerDTO onlineSifarisFormAnswer,
            @PathVariable("formId") Integer formId,
            @PathVariable("tokenId") String tokenId,
            @AuthenticationPrincipal UserDTO loggedInUser,
            Model model
    ){
        try {
            registrationFormCountService.editOnlineSifarisForm(onlineSifarisFormAnswer, null);
            return 1;
        } catch(Exception e ) {
            e.printStackTrace();
            log.info(e.getMessage());
            return 0;
        }
    }

    @GetMapping("sifaris/preview/{tokenId}/{formId}")
    public String getCertificatePreview(@PathVariable("tokenId") String tokenId, Model model,@PathVariable("formId") Integer formId,  @AuthenticationPrincipal UserDTO loggedInUser) {
        List<SifarisDetailDTO> onlineSifaris = registrationFormCountService.getSifarisDetailView(tokenId, formId, loggedInUser.getToken());
        model.addAttribute("fields", onlineSifaris );
        model.addAttribute("tokenId", tokenId);
        model.addAttribute("formId", formId);
        model.addAttribute("status", registrationFormCountService.getSifarisPresentStatus(tokenId, formId, loggedInUser.getToken()));
        model.addAttribute("letterMetaData", registrationFormCountService.getSifarisLetterMetaData(tokenId, formId, loggedInUser.getToken()));
//        model.addAttribute("certificate", electricityConnectionService.getCertificateByTokenId(tokenId).getData());
        return "private/common/onlineSifaris/preview/preview-electricity-connection-certificate";
    }
    @GetMapping("sifaris/print/{tokenId}/{formId}")
    public String getCertificatePrintView(@PathVariable("tokenId") String tokenId, Model model,@PathVariable("formId") Integer formId,  @AuthenticationPrincipal UserDTO loggedInUser) {
        System.out.println("logged user " + loggedInUser);

        List<SifarisDetailDTO> onlineSifaris = registrationFormCountService.getSifarisDetailView(tokenId, formId, loggedInUser.getToken());
        model.addAttribute("fields", onlineSifaris );
        model.addAttribute("tokenId", tokenId);
        model.addAttribute("formId", formId);
        model.addAttribute("status", registrationFormCountService.getSifarisPresentStatus(tokenId, formId, loggedInUser.getToken()));
        model.addAttribute("letterMetaData", registrationFormCountService.getSifarisLetterMetaData(tokenId, formId, loggedInUser.getToken()));
        return "private/common/onlineSifaris/print/print-electricity-certificate";
    }
}
