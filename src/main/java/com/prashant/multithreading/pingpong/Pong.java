package com.prashant.multithreading.pingpong;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

class Pong implements Runnable {
    private volatile long counter = 0;
    private volatile boolean running = true;

    private Semaphore ping;
    private Semaphore pong;

    public Pong(Semaphore ping, Semaphore pong) {
        this.ping = ping;
        this.pong = pong;
    }

    public void exit() {
        this.running = false;
    }

    public void run() {
        while(running) {
            ping.release();
            try {
                //pong.tryAcquire(1, 1000, TimeUnit.MILLISECONDS);
                pong.acquire();
                System.out.println("\tPong: " + ++counter );
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ping.release();

    }
}