package com.workshop.epam_workshop.controllers;

import com.workshop.epam_workshop.counters.Counter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    @Tag(name = "Counter")
    @Operation(summary = "Get amount of entries")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Got amount of entries", content = {@Content(mediaType = "application/json")})})
    @GetMapping(value = "/counter")
    public ResponseEntity<?> getCounter() {
        logger.info("Get counter");
        return new ResponseEntity<>(Counter.getCounter(), HttpStatus.OK);
    }
}
