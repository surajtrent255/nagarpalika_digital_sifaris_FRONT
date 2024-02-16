package com.ishanitech.ipalikawebapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LgWardDTO {
    private int wardId;
    private String wardDescription;
    private int municipalityId;
}
