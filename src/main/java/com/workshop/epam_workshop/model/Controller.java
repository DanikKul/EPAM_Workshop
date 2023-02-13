package com.workshop.epam_workshop.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller{
    @GetMapping(value = "/answer")
    public Converter getAnswer (@RequestParam(value="value") String value){
        try {
            double d = Double.parseDouble(value);
        } catch (Exception e) {
            return new Converter(1);
        }
        return new Converter(Double.valueOf(value));
    }
}