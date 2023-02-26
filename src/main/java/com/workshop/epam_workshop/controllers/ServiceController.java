package com.workshop.epam_workshop.controllers;

import com.workshop.epam_workshop.model.Converter;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController{

    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    @GetMapping(value = "/answer")
    public ResponseEntity<?> getAnswer (@RequestParam(value="value") String value){
        logger.info("Trying to parse data...");
        double parsedValue = Converter.validateData(value.strip());
        logger.info("Parsing completed!");
        return new ResponseEntity<>(new Converter(parsedValue), HttpStatusCode.valueOf(200));
    }

}