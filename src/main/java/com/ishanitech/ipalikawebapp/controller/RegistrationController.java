package com.ishanitech.ipalikawebapp.controller;

import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.exception.CustomExceptionThrowerUtil;
import com.ishanitech.ipalikawebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/registration")
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @GetMapping("/registrationConfirm")
    public String activateAccount(@RequestParam("token") String token, Model model) {
        Response<?> response = userService.activateUserAccount(token);
        if(response.getStatus() == HttpStatus.OK.value()) {
            model.addAttribute("response", response);
            return "public/onlineSifarisForm/account-activation";
        }

        throw CustomExceptionThrowerUtil.throwException(response.getStatus(), response.getMessage());
    }
}
