package com.ishanitech.ipalikawebapp.service;

import com.ishanitech.ipalikawebapp.dto.*;

import java.util.List;
import java.util.Map;

public interface RegistrationFormCountService {
    List<RegistrationFormCountDTO> getRegistrationFormCount(String token);

    Response<List<OnlineSifarisDetailsFieldDTO>> getAllCertificates(Integer formId);

    void updateStatusLevel(SifarisMetaData sifarisMetaData, String token);

    List<SifarisDetailDTO> getSifarisDetailView(String tokenId, Integer formId, String authToken);
    public Integer getSifarisPresentStatus(String tokenId, Integer formId, String authToken);

    void editOnlineSifarisForm(OnlineSIfarisFormAnswerDTO onlineSifarisFormAnswer, String token);

    Map<String, String> getSifarisLetterMetaData(String tokenId, Integer formId, String token);
}
