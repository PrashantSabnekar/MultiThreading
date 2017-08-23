package com.prashant.multithreading.pingpong;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by prashant.sabnekar on 8/23/2017.
 */
public class Main {

    public static void main(String args[]) throws InterruptedException {

        Semaphore pingSemaphore = new Semaphore(0);
        Semaphore pongSemaphore = new Semaphore(0);

      //  pingSemaphore.release();

        Ping ping = new Ping(pingSemaphore, pongSemaphore);
        Pong pong = new Pong(pingSemaphore, pongSemaphore);
   //     PingPong ping = new PingPong(pingSemaphore, pongSemaphore, "Ping");
  //      PingPong pong = new PingPong(pongSemaphore, pingSemaphore, "...Pong");

        Thread pingThread = new Thread(ping);
        Thread pongThread = new Thread(pong);

        System.out.println("Starting the application");

        pingThread.start();
        pongThread.start();

        sleep(5000);

        ping.exit();
        pong.exit();

        sleep(1000);

        System.out.println("Closing the application");

    }
}
