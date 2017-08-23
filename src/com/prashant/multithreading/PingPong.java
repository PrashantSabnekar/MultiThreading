package com.prashant.multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by prashant.sabnekar on 8/23/2017.
 */
public class PingPong {

    public void demo() throws InterruptedException {

        Semaphore ping = new Semaphore(0);
        Semaphore pong = new Semaphore(0);

        class Ping implements Runnable {
            private volatile long counter = 0L;
            private boolean running = true;

            public void exit() {
                this.running = false;
            }

            public void run() {
                while(running) {
                    try {
                        ping.tryAcquire(1,1000, TimeUnit.MILLISECONDS);
                        System.out.println("Ping: " + ++counter);
                        pong.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        class Pong implements Runnable {
            private volatile long counter = 0;
            private volatile boolean running = true;

            public void exit() {
                this.running = false;
            }

            public void run() {
                while(running) {
                    ping.release();
                    try {
                        pong.tryAcquire(1, 1000, TimeUnit.MILLISECONDS);
                        System.out.println("\tPong: " + ++counter );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        }

        Ping pingThread = new Ping();
        Pong pongThread = new Pong();

        Thread t1 = new Thread(pingThread);
        Thread t2 = new Thread(pongThread);

        t1.start();
        t2.start();

        sleep(5000);

        pingThread.exit();
        pongThread.exit();

        sleep(5000);

    }

    public static void main(String args[]) throws InterruptedException {
        PingPong pingPong = new PingPong();
        pingPong.demo();
    }
}
