package com.ishanitech.ipalikawebapp.serviceImpl;
import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.*;
import com.ishanitech.ipalikawebapp.service.RegistrationFormCountService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RegistrationFormCountServiceImpl implements RegistrationFormCountService {

    private final RestTemplate restTemplate;
    private final RestApiProperties restApiProperties;
    private final String REGISTRATION_REPORT_BASE = "/registration-report";


    public RegistrationFormCountServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
        this.restTemplate = restTemplate;
        this.restApiProperties = restApiProperties;
    }


    @Override
    public List<RegistrationFormCountDTO> getRegistrationFormCount(String token) {
        String template = REGISTRATION_REPORT_BASE;
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
        ParameterizedTypeReference<Response<List<RegistrationFormCountDTO>>> bean = new ParameterizedTypeReference<Response<List<RegistrationFormCountDTO>>>() {
        };
        return restTemplate.exchange(requestEntity, bean).getBody().getData();


    }

    @Override
    public Response<List<OnlineSifarisDetailsFieldDTO>> getAllCertificates(Integer formId) {
        String template = String.format("%s/sifarisList/%s", REGISTRATION_REPORT_BASE, formId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<List<OnlineSifarisDetailsFieldDTO>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<OnlineSifarisDetailsFieldDTO>>> responseType = new ParameterizedTypeReference<Response<List<OnlineSifarisDetailsFieldDTO>>>() {};
        Response<List<OnlineSifarisDetailsFieldDTO>> certificates = restTemplate.exchange(requestEntity, responseType).getBody();
        return certificates;
    }

    @Override
    public void updateStatusLevel(SifarisMetaData sifarisMetaData, String token) {
        String template = String.format("%s/form/status/%s", REGISTRATION_REPORT_BASE, sifarisMetaData.getTokenId() );
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT,sifarisMetaData , MediaType.APPLICATION_JSON, token, url);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        restTemplate.exchange(requestEntity, responseType);


    }

    @Override
    public List<SifarisDetailDTO> getSifarisDetailView(String tokenId, Integer formId, String authToken) {
        String template = String.format("%s/sifaris/%s/%s", REGISTRATION_REPORT_BASE, tokenId, formId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<List<List<SifarisDetailDTO>>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<List<SifarisDetailDTO>>> responseType = new ParameterizedTypeReference<Response<List<SifarisDetailDTO>>>() {};
        Response<List<SifarisDetailDTO>> certificate = restTemplate.exchange(requestEntity, responseType).getBody();
        return certificate.getData();
    }



    @Override
    public Integer getSifarisPresentStatus(String tokenId, Integer formId, String authToken) {
        String template = String.format("%s/sifaris/status/%s/%s", REGISTRATION_REPORT_BASE, tokenId, formId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<Integer> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<Integer>> responseType = new ParameterizedTypeReference<Response<Integer>>() {};
        Response<Integer> status = restTemplate.exchange(requestEntity, responseType).getBody();
        return status.getData();
    }

    @Override
    public void editOnlineSifarisForm(OnlineSIfarisFormAnswerDTO onlineSifarisFormAnswer, String token) {
        String template = String.format("%s/sifaris/edit/", REGISTRATION_REPORT_BASE);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<OnlineSIfarisFormAnswerDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, onlineSifarisFormAnswer, MediaType.APPLICATION_JSON, token, url);
        restTemplate.exchange(requestEntity, String.class);
    }

    @Override
    public Map<String, String> getSifarisLetterMetaData(String tokenId, Integer formId, String token) {
        String template = String.format("%s/sifaris/letter/metadata/%s/%s", REGISTRATION_REPORT_BASE, tokenId, formId);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<Map<String, String>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
        ParameterizedTypeReference<Response<Map<String, String>>> responseType = new ParameterizedTypeReference<Response<Map<String, String>>>() {};
        Response<Map<String, String>> certificate = restTemplate.exchange(requestEntity, responseType).getBody();
        return certificate.getData();
    }

}

