/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import java.time.LocalTime;

/**
 *
 * @author Назар
 */
public class StatisticThread extends Thread {

    private final Queue queue;
    private final long timeInterval;
    private int count;
    private int queueSizeSum;
    private final boolean showStatistic;

    public StatisticThread(Queue queue, long timeInterval, boolean showStatistic) {
        this.queue = queue;
        this.timeInterval = timeInterval;
        count = 0;
        queueSizeSum = 0;
        this.showStatistic = showStatistic;
    }

    public int getIterationCount() {
        return count;
    }

    public int getQueueSizeZum() {
        return queueSizeSum;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(timeInterval);
                count++;
                queueSizeSum += queue.getQueueCount();

                if (showStatistic) {
                    System.out.println("\n\tTime: " + LocalTime.now() + ";");
                    System.out.println("Added count: " + queue.getAddedCount() + ";");
                    System.out.println("Rejection count: " + queue.getRejectionCount() + ";");

                    System.out.println("\tRejected/Added: " + ((float) queue.getRejectionCount() / queue.getAddedCount()) + "; ");
                    System.out.println("\nAverage queue size: " + ((float) queueSizeSum / count) + ";");
                }
            }
        } catch (InterruptedException e) {
            if (showStatistic) {
                System.out.println("Stopped statistics");
            }
        }
    }
}
