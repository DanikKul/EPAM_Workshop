package com.workshop.epamworkshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "converter")

public class Converter implements Comparable<Converter> {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "input", nullable = false)
    private Double userInput;
    @Column(name = "meters", nullable = false)
    private Double meters;
    @Column(name = "inches", nullable = false)
    private Double inches;

    public Double getInches() {
        return inches;
    }

    public Double getMeters() {
        return meters;
    }

    public Double getUserInput() {
        return this.userInput;
    }

    public void setInches(Double inches) {
        this.inches = inches;
    }

    public void setMeters(Double meters) {
        this.meters = meters;
    }

    public void setUserInput(Double input) {
        this.userInput = input;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(Converter converter) {
        return this.getUserInput()
                .compareTo(converter.getUserInput());
    }

    @Override
    public String toString() {
        return "Converter{" + "id=" + id + ", input=" + userInput
                + ", meters=" + meters + ", inches=" + inches + '}';
    }

}