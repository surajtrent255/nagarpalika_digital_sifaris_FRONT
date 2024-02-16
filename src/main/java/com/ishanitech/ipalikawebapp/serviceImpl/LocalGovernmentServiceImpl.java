package com.ishanitech.ipalikawebapp.serviceImpl;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.*;
import com.ishanitech.ipalikawebapp.configs.*;
import com.ishanitech.ipalikawebapp.service.LocalGovernmentService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
@AllArgsConstructor
public class LocalGovernmentServiceImpl implements LocalGovernmentService {
    private final String LG_BASE_URL = "lg";
    private final RestTemplate restTemplate ;
    private final RestApiProperties restApiProperties;




    @Override
    public List<LgProvinceDTO> getAllProvinces() {
        String template = String.format("%s/provinces", LG_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgProvinceDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgProvinceDTO>>>() {};
        Response<List<LgProvinceDTO>> provinceInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return provinceInfo.getData();
    }

    @Override
    public List<LgDistrictDTO> getAllDistricts() {
        String template = String.format("%s/districts", LG_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgDistrictDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgDistrictDTO>>>() {};
        Response<List<LgDistrictDTO>> districtInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return districtInfo.getData();
    }

    @Override
    public List<LgMunicipalityDTO> getAllMunicipalities() {
        String template = String.format("%s/municipalities", LG_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgMunicipalityDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgMunicipalityDTO>>>() {};
        Response<List<LgMunicipalityDTO>> municipalityInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return municipalityInfo.getData();
    }

    @Override
    public List<LgWardDTO> getAllWards() {
        String template = String.format("%s/wards", LG_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgWardDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgWardDTO>>>() {};
        Response<List<LgWardDTO>> wardInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return wardInfo.getData();
    }

    @Override
    public List<LgDistrictDTO> getDistrictsByProvinceId(int provinceId) {
        String template = String.format("%s/provinces/{provinceId}/districts", LG_BASE_URL);
        Map<String, Object> uriValues = new HashMap<>();
        uriValues.put("provinceId", provinceId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, uriValues);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgDistrictDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgDistrictDTO>>>() {};
        Response<List<LgDistrictDTO>> districtInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return districtInfo.getData();
    }

    @Override
    public List<LgMunicipalityDTO> getMunicipalitiesByDistrictId(int districtId) {
        String template = String.format("%s/districts/{districtId}/municipalities", LG_BASE_URL);
        Map<String, Object> uriValues = new HashMap<>();
        uriValues.put("districtId", districtId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, uriValues);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgMunicipalityDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgMunicipalityDTO>>>() {};
        Response<List<LgMunicipalityDTO>> municipalityInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return municipalityInfo.getData();
    }

    @Override
    public List<LgWardDTO> getWardsByMunicipalityId(int municipalityId) {
        String template = String.format("%s/municipalities/{municipalityId}/wards", LG_BASE_URL);
        Map<String, Object> uriValues = new HashMap<>();
        uriValues.put("municipalityId", municipalityId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, uriValues);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<LgWardDTO>>> responseType = new ParameterizedTypeReference<Response<List<LgWardDTO>>>() {};
        Response<List<LgWardDTO>> wardInfo = restTemplate.exchange(requestEntity, responseType).getBody();
        return wardInfo.getData();
    }
}
