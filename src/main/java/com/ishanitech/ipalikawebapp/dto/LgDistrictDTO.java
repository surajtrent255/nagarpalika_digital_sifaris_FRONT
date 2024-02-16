package com.ishanitech.ipalikawebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgDistrictDTO {
    private int districtId;
    private String districtName;
    private String districtNameNep;
    private int provinceId;
}
