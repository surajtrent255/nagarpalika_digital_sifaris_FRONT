package com.ishanitech.ipalikawebapp.controller;

import com.ishanitech.ipalikawebapp.dto.PasswordResetDTO;
import com.ishanitech.ipalikawebapp.dto.ResetEmailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.exception.CustomExceptionThrowerUtil;
import com.ishanitech.ipalikawebapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/forgot-password")
@Controller
@Slf4j
public class PasswordResetController {

    private final UserService userService;

    public PasswordResetController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getPasswordResetView(){
        return "public/onlineSifarisForm/forgot-password";
    }

    @PostMapping("/checkEmail")
    public @ResponseBody boolean checkEmailValidity(@RequestBody ResetEmailDTO emailData) {
        System.out.println("*****************************************8");
        Response<Boolean> response = (Response<Boolean>) userService.checkDuplicateEmail(emailData.getResetEmail());
        return response.getData();
    }

    @RequestMapping(value = "/sendPasswordResetMail", method = RequestMethod.GET)
    public @ResponseBody String sendPasswordResetMail(@RequestParam("email") String email){
        log.info("Password reset is being called.");
        Response<String> emailSentResponse = (Response<String>) userService.sendPasswordResetEmail(email);
        log.info(emailSentResponse.toString());
        return emailSentResponse.getData();
    }

    @GetMapping("/verifyPasswordToken")
    public String verifyPasswordToken(@RequestParam("token") String token, Model model){
        log.info("Token is ->"+token);
        Response<Integer> response = (Response<Integer>) userService.verifyPasswordResetToken(token);
        log.info("The verified token user id for password reset is ->"+response.getData());
        if(response.getData() != null) {
            model.addAttribute("userId", response.getData());
            return "public/onlineSifarisForm/password-reset";
        }
        throw CustomExceptionThrowerUtil.throwException(response.getStatus(), response.getMessage());
    }

    @PostMapping("/resetPassword")
    public @ResponseBody boolean resetUserPassword(@RequestBody PasswordResetDTO passwordResetDTO){
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        log.info("Resetting is being going on...");
        try{
            ResponseEntity<List> updateResponse = (ResponseEntity<List>) userService.updateUserPassword(Integer.parseInt(passwordResetDTO.getUserId()), passwordResetDTO.getPassword());
            return true;
        } catch(RuntimeException jex) {
            return false;
        }
    }

}
