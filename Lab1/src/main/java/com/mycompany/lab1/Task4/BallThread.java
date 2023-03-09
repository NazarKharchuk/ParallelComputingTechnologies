/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task4;

/**
 *
 * @author Назар
 */
public class BallThread extends Thread {

    private Ball b;
    private BallThread prev;

    public BallThread(Ball ball, BallThread p) {
        b = ball;
        prev = p;
    }

    public BallThread(Ball ball) {
        b = ball;
        prev = null;
    }

    @Override
    public void run() {
        System.out.println("Thread name start = "
                + Thread.currentThread().getName());
        try {
            if (prev != null) {
                prev.join();
            }
            for (int i = 1; i < 100; i++) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);

            }
        } catch (InterruptedException ex) {

        }
    }
}
