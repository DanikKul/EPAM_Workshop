package com.workshop.EpamWorkshop.counters;

public class Counter {

    public static Integer counter = 0;

    public static synchronized void add() {
        counter++;
    }

    public static synchronized Integer getCounter() {
        return counter;
    }

}
