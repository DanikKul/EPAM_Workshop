package com.workshop.epam_workshop.controller;

import com.workshop.epam_workshop.model.Converter;
import com.workshop.epam_workshop.errors.NegativeException;

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
            double d = Double.parseDouble(value);
            if (d < 0) {
                throw new NegativeException("Value can't be negative!");
            }
            logger.info("Parsing completed!");
        } catch (NumberFormatException e) {
            logger.error("Parsing error: " + value);
            return ResponseEntity.badRequest().body("Parse Error");
        } catch (NegativeException e) {
            logger.error("Negative error: " + value);
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unhandled error");
            return ResponseEntity.badRequest().body("Unhandled Error");
        }
        return ResponseEntity.ok(new Converter(Double.parseDouble(value)));
    }

}