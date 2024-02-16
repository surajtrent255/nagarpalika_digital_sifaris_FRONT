package com.ishanitech.ipalikawebapp.dto;

import lombok.Data;
@Data
public class SifarisDetailDTO {
    private int fieldId;
    private String fieldName;
    private String fieldType;
    private String fieldAnswer;
    private String formId;
    private boolean required;
    private boolean fillableBySystem;
}
