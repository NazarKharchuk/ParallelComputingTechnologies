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
public class ProcessingTask implements Runnable {

    private static final Random random = new Random();

    private final Queue queue;
    private final double processingMean;
    private int processedCount;
    private boolean stopFlag;

    public ProcessingTask(Queue queue, double processingMean) {
        this.queue = queue;
        this.processingMean = processingMean;
        processedCount = 0;
        this.stopFlag = false;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public void stop() {
        stopFlag = true;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && !stopFlag) {
                queue.take();

                double processingTime = normalDistribution(processingMean);

                Thread.sleep((long) processingTime);

                processedCount++;
            }
        } catch (InterruptedException e) {
            System.out.println("Processing task: interrapted;");
        }
    }

    private double normalDistribution(double mean) {
        double stdDev = mean / 4;
        return random.nextGaussian() * stdDev + mean;
    }
}
