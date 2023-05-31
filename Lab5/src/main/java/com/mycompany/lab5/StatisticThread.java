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
    private final int imitationNumber;

    public StatisticThread(Queue queue, long timeInterval, boolean showStatistic, int imitationNumber) {
        this.queue = queue;
        this.timeInterval = timeInterval;
        count = 0;
        queueSizeSum = 0;
        this.showStatistic = showStatistic;
        this.imitationNumber = imitationNumber;
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
                    String statisticStr = "\n\t\tStatistic in imitation #" + imitationNumber;
                    statisticStr += "\n\tTime: " + LocalTime.now() + ";";
                    statisticStr += "\nAdded count: " + queue.getAddedCount() + ";";
                    statisticStr += "\nRejection count: " + queue.getRejectionCount() + ";";

                    statisticStr += "\n\tRejected/Added: " + ((float) queue.getRejectionCount() / queue.getAddedCount()) + "; ";
                    statisticStr += "\n\tAverage queue size: " + ((float) queueSizeSum / count) + ";";
                    System.out.println(statisticStr);
                }
            }
        } catch (InterruptedException e) {
            if (showStatistic) {
                System.out.println("\nStopped statistics of imitation #" + imitationNumber);
            }
        }
    }
}
