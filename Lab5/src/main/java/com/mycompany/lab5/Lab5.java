/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lab5;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Назар
 */
public class Lab5 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int imitationCount = 2;

        ExecutorService executor = Executors.newFixedThreadPool(imitationCount);

        ArrayList<Future<ArrayList<Float>>> statisticArrays = new ArrayList<>();

        RunImitation runImitation1 = new RunImitation(false, true);
        statisticArrays.add(executor.submit(runImitation1));

        RunImitation runImitation2 = new RunImitation(false, true);
        statisticArrays.add(executor.submit(runImitation2));

        executor.shutdown();

        ArrayList<Float> resultArray;
        float averageQueueSize = 0;
        float averageFailureProbabilities = 0;
        for (int i = 0; i < imitationCount; i++) {
            resultArray = statisticArrays.get(i).get();
            if (resultArray != null) {
                averageQueueSize += resultArray.get(0);
                averageFailureProbabilities += resultArray.get(1);
            }
        }

        System.out.println("\nAverage average queue size: " + (averageQueueSize / imitationCount) + ";");
        System.out.println("\tAverage failure probabilities: " + (averageFailureProbabilities / imitationCount) + "; ");
    }
}
