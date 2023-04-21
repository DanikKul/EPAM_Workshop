package com.workshop.epamworkshop.repositories;

import com.workshop.epamworkshop.model.Converter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConverterRepository extends JpaRepository<Converter, Long> {
    Converter findByUserInput(Double input);
}
