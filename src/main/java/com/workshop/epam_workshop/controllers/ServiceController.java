package com.workshop.epam_workshop.controllers;

import com.workshop.epam_workshop.model.Converter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getAnswer (@RequestParam(value="value", defaultValue = "") String value){
        if (value.isEmpty())
            return new ResponseEntity<>("<p style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">Please enter non-negative value</p>", HttpStatus.OK);
        logger.info("Trying to parse data...");
        double parsedValue = Converter.validateData(value.strip());
        logger.info("Parsing completed!");
        return new ResponseEntity<>(new Converter(parsedValue), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> home (@RequestParam (value = "user", defaultValue = "User") String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/answer?value=5");
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

}