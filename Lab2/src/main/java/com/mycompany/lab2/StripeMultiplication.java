/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Назар
 */
public class StripeMultiplication {

    private final Matrix firstMatrix;
    private final Matrix secondMatrix;
    private final Matrix resultMatrix;

    private final int resultMatrixRowsCount;
    private final int resultMatrixColsCount;

    private long startTime;
    private long endTime;

    public Result getResult() {
        return new Result(resultMatrix, endTime - startTime);
    }

    public StripeMultiplication(Matrix firstMatrix, Matrix secondMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrixRowsCount = firstMatrix.getRows();
        this.resultMatrixColsCount = secondMatrix.getColumns();
        this.resultMatrix = new Matrix(resultMatrixRowsCount, resultMatrixColsCount);
    }

    public void runStripeMultiplication(int numThreads) {
        if (firstMatrix.getColumns() != secondMatrix.getRows()) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        startTime = System.currentTimeMillis();

        Matrix secondMatrixTransposed = secondMatrix.transpose();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            Future<Integer>[] futures = new Future[resultMatrixRowsCount * resultMatrixColsCount];

            int index = 0;
            for (int i = 0; i < resultMatrixRowsCount; i++) {
                for (int j = 0; j < resultMatrixColsCount; j++) {
                    int[] row = firstMatrix.getRow(i);
                    int[] column = secondMatrixTransposed.getRow(j);
                    StripeTask task = new StripeTask(row, column);
                    futures[index] = executor.submit(task);
                    index++;
                }
            }

            index = 0;
            for (int i = 0; i < resultMatrixRowsCount; i++) {
                for (int j = 0; j < resultMatrixColsCount; j++) {
                    int value = futures[index].get();
                    resultMatrix.setElement(i, j, value);
                    index++;
                }
            }

            endTime = System.currentTimeMillis();
        } catch (InterruptedException | ExecutionException e) {
        } finally {
            executor.shutdown();
        }
    }
}
