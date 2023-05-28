/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Назар
 */
public class RunImitation implements Callable<ArrayList<Float>> {

    private final boolean showStatistic;
    private final boolean showResult;

    public RunImitation(boolean showStatistic, boolean showResult) {
        this.showStatistic = showStatistic;
        this.showResult = showResult;
    }

    @Override
    public ArrayList<Float> call() {
        try {
            int numChannels = 2;
            int queueCapacity = 5;

            double addingMean = 10.0;
            double processingMean = 120.0;

            long timeInterval = 200;

            ExecutorService executor = Executors.newFixedThreadPool(numChannels + 1);
            Queue queue = new Queue(queueCapacity);

            var addingTask = new AddingTask(queue, addingMean);
            executor.execute(addingTask);

            ProcessingTask[] processingTasks = new ProcessingTask[numChannels];
            for (int i = 0; i < numChannels; i++) {
                processingTasks[i] = new ProcessingTask(queue, processingMean);
                executor.execute(processingTasks[i]);
            }

            StatisticThread statisticThread = new StatisticThread(queue, timeInterval, showStatistic);
            statisticThread.start();

            Thread.sleep(5000);

            executor.shutdown();
            addingTask.stop();
            for (int i = 0; i < numChannels; i++) {
                processingTasks[i].stop();
            }
            statisticThread.interrupt();

            Thread.sleep((long) processingMean * 2);

            int processedCount = 0;
            for (int i = 0; i < numChannels; i++) {
                processedCount += processingTasks[i].getProcessedCount();
            }

            int count = statisticThread.getIterationCount();
            int queueSizeSum = statisticThread.getQueueSizeZum();

            int addedCount = queue.getAddedCount();
            int rejectionCount = queue.getRejectionCount();

            if (showResult) {
                System.out.println("\nAverage queue size" + ((float) queueSizeSum / count) + ";");
                System.out.println("\nAdded count: " + addedCount + ";");
                System.out.println("Rejection count: " + rejectionCount + ";");
                System.out.println("Processed count: " + processedCount + ";");
                System.out.println("Queue count: " + queue.getQueueCount() + ";");
                System.out.println("\tRejected/Added: " + ((float) rejectionCount / addedCount) + "; ");
            }

            ArrayList<Float> statisticArray = new ArrayList<>();
            statisticArray.add((float) queueSizeSum / count);
            statisticArray.add((float) rejectionCount / addedCount);

            return statisticArray;

        } catch (InterruptedException ex) {
            Logger.getLogger(RunImitation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
