package com.workshop.epamworkshop.controllers;

import com.workshop.epamworkshop.cache.CacheResult;
import com.workshop.epamworkshop.counters.CounterThread;
import com.workshop.epamworkshop.model.Converter;

import com.workshop.epamworkshop.model.ConverterLogic;
import com.workshop.epamworkshop.services.ConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
public class ServiceController {

    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    private CacheResult cache;

    @Autowired
    private ConverterService service;

    @Autowired
    public void setCache(CacheResult cache) {
        this.cache = cache;
    }

    @Tag(name = "Converter")
    @Operation(summary = "Get converted value")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully converted", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "400", description = "Negative number", content = @Content), @ApiResponse(responseCode = "500", description = "Can't parse into double", content = @Content)})
    @GetMapping(value = "/answer")
    public ResponseEntity<?> getAnswer(@RequestParam(value = "value", defaultValue = "") List<String> values) {
        CounterThread counter = new CounterThread();
        counter.start();
        if (values.isEmpty())
            return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<form action=\"/answer\" method=\"get\">" + "<p><input type=\"search\" name=\"value\" placeholder=\"Your Value\"></p>" + "<p><input type=\"submit\" value=\"Convert\"></p>" + "</form></div>", HttpStatus.OK);
        logger.info("Trying to parse data...");
        if (!values.stream().allMatch(ConverterLogic::validateData)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Parsing completed!");
        List<Double> valuesParsed = values.stream().map(ConverterLogic::parseData).toList();
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            ids.add((long) valuesParsed.get(i).hashCode());
        }
        CompletableFuture<List<Converter>> answers = CompletableFuture.supplyAsync(() -> processData(values));
        answers.thenAccept(this::saveToDatabase);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping(value = "/bulkAnswers")
    public ResponseEntity<?> getBulkAnswers(@RequestBody String params) {
        JSONObject jsonRequest = new JSONObject(params);
        List<String> values = jsonRequest.toMap().values().stream().map(Object::toString).toList();
        if (!values.stream().allMatch(ConverterLogic::validateData)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Double> valuesParsed = values.stream().map(ConverterLogic::parseData).toList();
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < valuesParsed.size(); i++) {
            ids.add((long) valuesParsed.get(i).hashCode());
        }
        CompletableFuture<List<Converter>> answers = CompletableFuture.supplyAsync(() -> processData(values));
        answers.thenAccept(this::saveToDatabase);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping(value = "/getFromDB")
    public ResponseEntity<?> getFromDB(@RequestParam(value="id", defaultValue = "") Long value) {
        Converter converter = service.getConverterById(value).orElse(null);
        if (converter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(converter, HttpStatus.OK);
    }

    @GetMapping(value = "/getFromDBByInput")
    public ResponseEntity<?> getFromDBByInput(@RequestParam(value="value", defaultValue = "") Double value) {
        Converter converter = service.getConverter(value);
        if (converter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(converter, HttpStatus.OK);
    }

    @Tag(name = "Home")
    @Operation(summary = "Home")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully converted", content = {@Content(mediaType = "application/json")})})
    @GetMapping(value = "/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<p style=\"color: orange; text-decoration: none;\">Actions</p>" + "<a style=\"color: red; text-decoration: none;\" href=\"/answer\">Get converted value</a>" + "<a style=\"color: red; text-decoration: none;\" href=\"/counter\">Get counter</a>" + "</div>", HttpStatus.OK);
    }

    public void saveToDatabase(List<Converter> answers) {
        for (Converter answer : answers) {
            service.findOrCreateByInput(answer.getUserInput());
        }
    }

    public List<Converter> processData(List<String> values) {
        List<Double> valuesParsed = values.stream().map(ConverterLogic::parseData).toList();
        return Stream.concat(
                        valuesParsed.stream()
                                .filter(value -> cache.contains(value))
                                .map(value -> cache.getCache(value)),
                        valuesParsed.stream()
                                .filter(value -> !cache.contains(value))
                                .map(value -> {
                                    Converter conv = new Converter();
                                    ConverterLogic.setAll(conv, value);
                                    cache.putCache(conv);
                                    return conv;
                                })
                )
                .sorted()
                .toList();
    }

}