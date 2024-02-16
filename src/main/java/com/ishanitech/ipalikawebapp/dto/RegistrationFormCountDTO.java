package com.ishanitech.ipalikawebapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFormCountDTO {
    private String formId;
    private String formTitle;
    private int unverified;
    private int stamped;
    private int registered;
    private int processed;
    private int verified;
    private int total;
}
