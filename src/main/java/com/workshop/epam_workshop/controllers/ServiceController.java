package com.workshop.epam_workshop.controllers;

import com.workshop.epam_workshop.cache.CacheResult;
import com.workshop.epam_workshop.counters.Counter;
import com.workshop.epam_workshop.counters.CounterThread;
import com.workshop.epam_workshop.model.Converter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    private CacheResult cache;

    @Autowired
    public void setCache(CacheResult cache) {
        this.cache = cache;
    }

    @Tag(name = "Converter")
    @Operation(summary = "Get converted value")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully converted", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "400", description = "Negative number", content = @Content), @ApiResponse(responseCode = "500", description = "Can't parse into double", content = @Content)})
    @GetMapping(value = "/answer")
    public ResponseEntity<?> getAnswer(@RequestParam(value = "value", defaultValue = "") String value) {
        CounterThread counter = new CounterThread();
        counter.start();
        if (value.isEmpty())
            return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<form action=\"/answer\" method=\"get\">" + "<p><input type=\"search\" name=\"value\" placeholder=\"Your Value\"></p>" + "<p><input type=\"submit\" value=\"Convert\"></p>" + "</form></div>", HttpStatus.OK);
        logger.info("Trying to parse data...");
        double parsedValue = Converter.validateData(value.strip());
        logger.info("Parsing completed!");
        if (cache.contains(parsedValue)) {
            logger.info("Found result in cache");
            return new ResponseEntity<>(cache.getCache(parsedValue), HttpStatus.OK);
        } else {
            Converter converter = new Converter(parsedValue);
            cache.putCache(converter);
            return new ResponseEntity<>(converter, HttpStatus.OK);
        }
    }

    @Tag(name = "Home")
    @Operation(summary = "Home")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully converted", content = {@Content(mediaType = "application/json")})})
    @GetMapping(value = "/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<p style=\"color: orange; text-decoration: none;\">Actions</p>" + "<a style=\"color: red; text-decoration: none;\" href=\"/answer\">Get converted value</a>" + "<a style=\"color: red; text-decoration: none;\" href=\"/counter\">Get counter</a>" + "</div>", HttpStatus.OK);
    }

}