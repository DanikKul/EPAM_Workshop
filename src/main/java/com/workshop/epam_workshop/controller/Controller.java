package com.workshop.epam_workshop.controller;

import com.workshop.epam_workshop.model.Converter;
import com.workshop.epam_workshop.errors.NegativeException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller{

    private static final Logger logger = LogManager.getLogger(Controller.class);

    @GetMapping(value = "/answer")
    public ResponseEntity<?> getAnswer (@RequestParam(value="value") String value){
        try {
            logger.info("Trying to parse data...");
            Converter.validateData(value);
            logger.info("Parsing completed!");
        } catch (NumberFormatException e) {
            logger.error("Parsing error: " + value);
            return new ResponseEntity<>("Parsing error", HttpStatusCode.valueOf(400));
        } catch (NegativeException e) {
            logger.error("Negative error: " + value);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        } catch (Exception e) {
            logger.error("Unhandled error");
            return new ResponseEntity<>("Unhandled Error", HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new Converter(Double.parseDouble(value)), HttpStatusCode.valueOf(200));
    }

}