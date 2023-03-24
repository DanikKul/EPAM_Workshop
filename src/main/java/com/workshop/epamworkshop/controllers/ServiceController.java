package com.workshop.epamworkshop.controllers;

import com.workshop.epamworkshop.cache.CacheResult;
import com.workshop.epamworkshop.counters.CounterThread;
import com.workshop.epamworkshop.model.Converter;

import com.workshop.epamworkshop.model.ConverterLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

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
    public ResponseEntity<?> getAnswer(@RequestParam(value = "value", defaultValue = "") List<String> values) {
        // Данный обработчик переделан, учитывая тот факт, что в queryParams может быть передан список значений
        CounterThread counter = new CounterThread();
        counter.start();
        if (values.isEmpty())
            return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<form action=\"/answer\" method=\"get\">" + "<p><input type=\"search\" name=\"value\" placeholder=\"Your Value\"></p>" + "<p><input type=\"submit\" value=\"Convert\"></p>" + "</form></div>", HttpStatus.OK);
        logger.info("Trying to parse data...");
        if (!values.stream().allMatch(ConverterLogic::validateData)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Double> valuesParsed = values.stream().map(ConverterLogic::parseData).sorted().toList();
        logger.info("Parsing completed!");
        List<Converter> answers = Stream.concat(
                valuesParsed.stream()
                        .filter(value -> cache.contains(value))
                        .map(value -> cache.getCache(value)),
                valuesParsed.stream()
                        .filter(value -> !cache.contains(value))
                        .map(value -> {
                            Converter conv = new Converter(value);
                            cache.putCache(conv);
                            return conv;
                        })
                )
                .sorted()
                .toList();
        if (answers.size() == 1) return new ResponseEntity<>(answers.get(0), HttpStatus.OK);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @PostMapping(value = "/bulkAnswers")
    public ResponseEntity<?> getBulkAnswers(@RequestBody String params) {
        JSONObject jsonRequest = new JSONObject(params);
        List<String> values = jsonRequest.toMap().values().stream().map(Object::toString).toList();
        if (!values.stream().allMatch(ConverterLogic::validateData)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Double> valuesParsed = values.stream().map(ConverterLogic::parseData).sorted().toList();
        JSONObject jsonResponse = new JSONObject();
        // Следующий код не является оптимизированным, его можно сделать гораздо проще, однако
        // он предназначен для демонстрации работы с lambda выражениями и Stream API.
        // В данном случае сначала берутся значения, объекты которых уже подсчитаны в кеше
        // и соединяются с объектами, которых нет в кеше.
        List<Converter> answers = Stream.concat(
                        valuesParsed.stream()
                                .filter(value -> cache.contains(value))
                                .map(value -> cache.getCache(value)),
                        valuesParsed.stream()
                                .filter(value -> !cache.contains(value))
                                .map(value -> {
                                    Converter conv = new Converter(value);
                                    cache.putCache(conv);
                                    return conv;
                                })
                )
                .sorted()
                .toList();
        // Подсчет аггрегирующих значений (min, max, average, amount)
        jsonResponse.put("answers", answers);
        jsonResponse.put("minUserInput", answers.stream()
                .mapToDouble(Converter::getUserInput)
                .min().orElse(-1));
        jsonResponse.put("maxUserInput", answers.stream()
                .mapToDouble(Converter::getUserInput)
                .max()
                .orElse(-1));
        jsonResponse.put("avgUserInput", answers.stream()
                .mapToDouble(Converter::getUserInput)
                .average()
                .orElse(-1));
        jsonResponse.put("amount", answers.size());
        if (answers.size() == 1) return new ResponseEntity<>(answers.get(0), HttpStatus.OK);

        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    @Tag(name = "Home")
    @Operation(summary = "Home")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully converted", content = {@Content(mediaType = "application/json")})})
    @GetMapping(value = "/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("<div style=\"font-size: 40px; text-align:center; height:100%; display: flex; flex-direction: column; justify-content: center;\">" + "<p style=\"color: orange; text-decoration: none;\">Actions</p>" + "<a style=\"color: red; text-decoration: none;\" href=\"/answer\">Get converted value</a>" + "<a style=\"color: red; text-decoration: none;\" href=\"/counter\">Get counter</a>" + "</div>", HttpStatus.OK);
    }
}