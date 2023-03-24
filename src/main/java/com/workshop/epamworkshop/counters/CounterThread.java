package com.workshop.epamworkshop.counters;

public class CounterThread extends Thread{
    @Override
    public void run() {
        Counter.add();
    }
}