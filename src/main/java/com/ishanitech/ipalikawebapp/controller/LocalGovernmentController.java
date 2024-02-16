package com.ishanitech.ipalikawebapp.controller;

import com.ishanitech.ipalikawebapp.dto.LgDistrictDTO;
import com.ishanitech.ipalikawebapp.dto.LgMunicipalityDTO;
import com.ishanitech.ipalikawebapp.dto.LgWardDTO;
import com.ishanitech.ipalikawebapp.service.LocalGovernmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequestMapping("/lg")
@Controller
public class LocalGovernmentController {
    private final LocalGovernmentService localGovernmentService;

    public LocalGovernmentController(LocalGovernmentService localGovernmentService){
        this.localGovernmentService = localGovernmentService;
    }
    @GetMapping("/provinces/{provinceId}/districts")
    public @ResponseBody List<LgDistrictDTO> getDistrictsByProvinceId(@PathVariable("provinceId") int provinceId) {
        List<LgDistrictDTO> districtList = localGovernmentService.getDistrictsByProvinceId(provinceId);
        return districtList;
    }


    @GetMapping("/districts/{districtId}/municipalities")
    public @ResponseBody List<LgMunicipalityDTO> getMunicipalitiesByDistrictId(@PathVariable("districtId") int districtId) {
        List<LgMunicipalityDTO> municipalityList = localGovernmentService.getMunicipalitiesByDistrictId(districtId);
        return municipalityList;
    }


    @GetMapping("/municipalities/{municipalityId}/wards")
    public @ResponseBody List<LgWardDTO> getWardsByMunicipalityId(@PathVariable("municipalityId") int municipalityId) {
        List<LgWardDTO> wardList = localGovernmentService.getWardsByMunicipalityId(municipalityId);
        log.info(wardList.toString());
        return wardList;
    }
}
