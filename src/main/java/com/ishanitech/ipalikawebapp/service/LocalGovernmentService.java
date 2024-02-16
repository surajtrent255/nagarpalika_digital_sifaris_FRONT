package com.ishanitech.ipalikawebapp.service;

import com.ishanitech.ipalikawebapp.dto.LgDistrictDTO;
import com.ishanitech.ipalikawebapp.dto.LgMunicipalityDTO;
import com.ishanitech.ipalikawebapp.dto.LgProvinceDTO;
import com.ishanitech.ipalikawebapp.dto.LgWardDTO;

import java.util.List;

public interface LocalGovernmentService {
    List<LgProvinceDTO> getAllProvinces();

    List<LgDistrictDTO> getAllDistricts();

    List<LgMunicipalityDTO> getAllMunicipalities();

    List<LgWardDTO> getAllWards();

    List<LgDistrictDTO> getDistrictsByProvinceId(int provinceId);

    List<LgMunicipalityDTO> getMunicipalitiesByDistrictId(int districtId);

    List<LgWardDTO> getWardsByMunicipalityId(int municipalityId);
}
