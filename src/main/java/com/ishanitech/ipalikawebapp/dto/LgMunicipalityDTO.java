package com.ishanitech.ipalikawebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgMunicipalityDTO {
    private int municipalityId;
    private String municipalityName;
    private String municipalityNameNep;
    private int provinceId;
    private int districtId;
}
