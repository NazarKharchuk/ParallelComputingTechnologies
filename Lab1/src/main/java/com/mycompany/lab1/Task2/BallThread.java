/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task2;

import java.util.ArrayList;

/**
 *
 * @author Назар
 */
public class BallThread extends Thread {

    private Ball b;
    private final ArrayList<Pocket> pockets;

    public BallThread(Ball ball, ArrayList<Pocket> p) {
        b = ball;
        pockets = p;
    }

    @Override
    public void run() {
        boolean flag;
        try {
            for (int i = 1; i < 10000; i++) {
                b.move();
                flag = false;
                for(int j = 0; j < pockets.size(); j++) {
                    if(pockets.get(j).caughtBall(b)){
                        flag = true;
                        break;
                    }
                }
                if(flag) break;
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);

            }
        } catch (InterruptedException ex) {

        }
    }
}
