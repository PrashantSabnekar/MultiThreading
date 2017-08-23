package com.prashant.multithreading.pingpong;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

class PingPong implements Runnable {
            private volatile long counter = 0L;
            private volatile boolean running = true;
            private volatile String message;

            private Semaphore ping;
            private Semaphore pong;

            public PingPong(Semaphore ping, Semaphore pong, String message) {
                this.ping = ping;
                this.pong = pong;
                this.message = message;
            }

            public void exit() {
                this.running = false;
            }

            public synchronized void run() {
                while(running) {
                    try {
                        ping.acquire();
                        System.out.println(message + " : " + ++counter);
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }