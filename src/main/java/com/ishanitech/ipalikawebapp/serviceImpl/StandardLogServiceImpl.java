package com.ishanitech.ipalikawebapp.service.serviceImpl;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.StandardLogDTO;
import com.ishanitech.ipalikawebapp.service.StandardLogService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StandardLogServiceImpl implements StandardLogService {
    private final String LG_BASE_URL = "online-sifaris";
    private final RestTemplate restTemplate ;
    private final RestApiProperties restApiProperties;

    @Override
    public List<StandardLogDTO> getAllStandardLogData() {
        String template = String.format("%s/standardlog",LG_BASE_URL);
        String url= HttpUtils.createRequestUrl(restApiProperties,template,null);
        RequestEntity<?> requestEntity= HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON,url);
        ParameterizedTypeReference<Response<List<StandardLogDTO>>> responseType = new ParameterizedTypeReference<Response<List<StandardLogDTO>>>() {};
        Response<List<StandardLogDTO>> logInfo = restTemplate.exchange(requestEntity,responseType).getBody();
        return logInfo.getData();
    }

}