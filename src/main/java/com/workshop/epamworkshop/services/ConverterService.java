package com.workshop.epamworkshop.services;

import com.workshop.epamworkshop.model.Converter;
import com.workshop.epamworkshop.model.ConverterLogic;
import com.workshop.epamworkshop.repositories.ConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConverterService {
    @Autowired
    private ConverterRepository repository;

    @Autowired
    public ConverterService(ConverterRepository converterRepository) {
        this.repository = converterRepository;
    }

    public Converter findOrCreateByInput(Double input) {

        Converter converter = repository.findByUserInput(input);
        if (converter == null) {
            converter = new Converter();
            ConverterLogic.setAll(converter, input);
            converter = repository.save(converter);
        }
        return converter;
    }

    public Converter getConverter(Double input) {
        return repository.findByUserInput(input);
    }

    public Optional<Converter> getConverterById(Long id) {
        return repository.findById(id);
    }

    public void deleteConverter(Converter converter) {
        repository.delete(converter);
    }

    public Iterable<Converter> getAllConverters() {
        return repository.findAll();
    }
}
