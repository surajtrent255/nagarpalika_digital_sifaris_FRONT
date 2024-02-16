package com.ishanitech.ipalikawebapp.serviceImpl;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalikawebapp.dto.OnlineSifarisFormFieldsDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.OnlineSifarisService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class OnlineSifarisServiceImpl implements OnlineSifarisService {
    private final RestTemplate restTemplate;
    private final RestApiProperties restApiProperties;
    private final String ONLINE_SIFARIS_BASE_URL = "online-sifaris/";
    public OnlineSifarisServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
        this.restTemplate = restTemplate;
        this.restApiProperties = restApiProperties;
    }


    @Override
    public List<OnlineSifarisFormFieldsDTO> getFullFormDetailById(int id, String token) {
        String template = ONLINE_SIFARIS_BASE_URL + "/formData/" + id;
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
        ParameterizedTypeReference<Response<List<OnlineSifarisFormFieldsDTO>>> responseType = new ParameterizedTypeReference<Response<List<OnlineSifarisFormFieldsDTO>>>() {};
        Response<List<OnlineSifarisFormFieldsDTO>> formDetail = restTemplate.exchange(requestEntity, responseType).getBody();
        return formDetail.getData();
    }

    @Override
    public void addOnlineSifarisForm(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswer, String token) {
        String template = String.format("%s/formData", ONLINE_SIFARIS_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        RequestEntity<OnlineSIfarisFormAnswerDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, onlineSIfarisFormAnswer, MediaType.APPLICATION_JSON, token, url);
        restTemplate.exchange(requestEntity, String.class);

    }

    @Override
    public void addOnlineSifarisFormImage(MultipartFile file, String imageName) {
        String template = String.format("%s/formData/image", ONLINE_SIFARIS_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("picture", file.getResource());
        log.info("###########################" + file.getOriginalFilename());
        HttpHeaders headers = HttpUtils.createHeader(MediaType.MULTIPART_FORM_DATA, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        restTemplate.postForLocation(url, requestEntity);
    }

    @Override
    public void editOnlineSifarisFormImage(MultipartFile file, String imageName) {
        String template = String.format("%s/formData/image/edit", ONLINE_SIFARIS_BASE_URL);
        String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("picture", file.getResource());
        log.info("###########################" + file.getOriginalFilename());
        HttpHeaders headers = HttpUtils.createHeader(MediaType.MULTIPART_FORM_DATA, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        restTemplate.postForLocation(url, requestEntity);
    }

}
