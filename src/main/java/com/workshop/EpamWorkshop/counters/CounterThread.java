package com.workshop.EpamWorkshop.counters;

public class CounterThread extends Thread{
    @Override
    public void run() {
        Counter.add();
    }
}