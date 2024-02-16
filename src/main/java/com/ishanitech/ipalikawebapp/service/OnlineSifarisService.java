package com.ishanitech.ipalikawebapp.service;

import com.ishanitech.ipalikawebapp.dto.OnlineSIfarisFormAnswerDTO;
import com.ishanitech.ipalikawebapp.dto.OnlineSifarisFormFieldsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OnlineSifarisService {

    List<OnlineSifarisFormFieldsDTO> getFullFormDetailById(int id, String token);

    void addOnlineSifarisForm(OnlineSIfarisFormAnswerDTO onlineSIfarisFormAnswerDTO, String token);

    void addOnlineSifarisFormImage(MultipartFile multipartFile, String imageName);

    void editOnlineSifarisFormImage(MultipartFile multipartFile, String imageName);
}
