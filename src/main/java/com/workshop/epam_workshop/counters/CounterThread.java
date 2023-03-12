package com.workshop.epam_workshop.counters;

public class CounterThread extends Thread{
    @Override
    public synchronized void run() {
        Counter.add();
    }
}