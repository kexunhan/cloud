package com.example.batchsavetest.controller;

import com.example.batchsavetest.service.QueryGrantTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrantTypeController {
   
     

    @Autowired
    private QueryGrantTypeService queryGrantTypeService;

    @GetMapping("/grantType")
    public String test(String resourceName){
   
     
        return queryGrantTypeService.getResult(resourceName);
    }
}