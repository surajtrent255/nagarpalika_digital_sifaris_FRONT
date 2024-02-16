package com.ishanitech.ipalikawebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnlineSifarisFormFieldsDTO {

    private String formId;
    private String fieldId;
    private String fieldName;
    private String fieldType;
    private boolean required;
    private boolean fillableBySystem;

}
