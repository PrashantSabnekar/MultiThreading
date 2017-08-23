package com.prashant.multithreading.pingpong;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

class Ping implements Runnable {
            private volatile long counter = 0L;
            private boolean running = true;

            private Semaphore ping;
            private Semaphore pong;

            public Ping(Semaphore ping, Semaphore pong) {
                this.ping = ping;
                this.pong = pong;
            }

            public void exit() {
                this.running = false;
            }

            public void run() {
                while(running) {
                    try {
                      //  ping.tryAcquire(1,1000, TimeUnit.MILLISECONDS);
                        ping.acquire();
                        System.out.println("Ping: " + ++counter);
                        sleep(500);
                        pong.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                pong.release();
            }
        }