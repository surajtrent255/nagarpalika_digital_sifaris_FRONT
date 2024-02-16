package com.ishanitech.ipalikawebapp.controller;


import com.ishanitech.ipalikawebapp.dto.StandardLogDTO;
import com.ishanitech.ipalikawebapp.service.StandardLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/online-sifaris")
@Controller
@Slf4j
public class StandardLogController {
    private final StandardLogService standardLogService;

    public StandardLogController(StandardLogService standardLogService) {
        this.standardLogService = standardLogService;
    }

    @GetMapping("/standardlog")
    public String getAllStandardLogData(Model model){
        List<StandardLogDTO> standardLogDTOS= standardLogService.getAllStandardLogData();
        model.addAttribute("logDatas",standardLogService.getAllStandardLogData());
        return "private/common/onlineSifaris/logdata/log";
    }
}