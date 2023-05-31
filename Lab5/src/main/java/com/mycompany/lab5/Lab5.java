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
        int imitationCount = 4;

        ExecutorService executor = Executors.newFixedThreadPool(imitationCount);

        ArrayList<Future<ArrayList<Float>>> statisticArrays = new ArrayList<>();

        RunImitation runImitation1 = new RunImitation(true, true, 1);
        statisticArrays.add(executor.submit(runImitation1));

        RunImitation runImitation2 = new RunImitation(true, true, 2);
        statisticArrays.add(executor.submit(runImitation2));

        RunImitation runImitation3 = new RunImitation(true, true, 3);
        statisticArrays.add(executor.submit(runImitation3));

        RunImitation runImitation4 = new RunImitation(true, true, 4);
        statisticArrays.add(executor.submit(runImitation4));

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

        System.out.println("\n\t\tFinal result:");
        System.out.println("\tAverage average queue size: " + (averageQueueSize / imitationCount) + ";");
        System.out.println("\tAverage failure probabilities: " + (averageFailureProbabilities / imitationCount) + "; ");
    }
}
