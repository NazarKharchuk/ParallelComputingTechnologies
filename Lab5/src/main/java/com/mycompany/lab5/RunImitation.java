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
    private final int imitationNumber;

    public RunImitation(boolean showStatistic, boolean showResult, int imitationNumber) {
        this.showStatistic = showStatistic;
        this.showResult = showResult;
        this.imitationNumber = imitationNumber;
    }

    @Override
    public ArrayList<Float> call() {
        try {
            int numChannels = 5;
            int queueCapacity = 20;

            double addingMean = 10.0;
            double processingMean = 80.0;

            long timeInterval = 500;

            ExecutorService executor = Executors.newFixedThreadPool(numChannels + 1);
            Queue queue = new Queue(queueCapacity);

            var addingTask = new AddingTask(queue, addingMean);
            executor.execute(addingTask);

            ProcessingTask[] processingTasks = new ProcessingTask[numChannels];
            for (int i = 0; i < numChannels; i++) {
                processingTasks[i] = new ProcessingTask(queue, processingMean);
                executor.execute(processingTasks[i]);
            }

            StatisticThread statisticThread = new StatisticThread(queue, timeInterval, showStatistic, imitationNumber);
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
                String resultStr = "\n\t\tResult of imitation #" + imitationNumber;
                resultStr += "\nAdded count: " + addedCount + ";";
                resultStr += "\nRejection count: " + rejectionCount + ";";
                resultStr += "\nProcessed count: " + processedCount + ";";
                resultStr += "\nQueue count: " + queue.getQueueCount() + ";";
                resultStr += "\n\tAverage queue size: " + ((float) queueSizeSum / count) + ";";
                resultStr += "\n\tRejected/Added: " + ((float) rejectionCount / addedCount) + "; ";
                System.out.println(resultStr);
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
