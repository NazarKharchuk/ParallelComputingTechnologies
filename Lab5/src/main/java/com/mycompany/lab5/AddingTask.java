/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import java.util.Random;

/**
 *
 * @author Назар
 */
public class AddingTask implements Runnable {

    private static final Random random = new Random();

    private final Queue queue;
    private final double addingMean;
    private int integerToPut;
    private boolean stopFlag;

    public AddingTask(Queue queue, double addingMean) {
        this.queue = queue;
        this.addingMean = addingMean;
        integerToPut = 1;
        this.stopFlag = false;
    }

    public void stop() {
        stopFlag = true;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && !stopFlag) {
                double addingTime = exponentialDistribution(addingMean);

                Thread.sleep((long) addingTime);

                queue.put(integerToPut++);
            }
        } catch (InterruptedException e) {
            System.out.println("Adding task: interrapted;");
        }
    }

    private double exponentialDistribution(double mean) {
        return -mean * Math.log(1 - random.nextDouble());
    }
}
