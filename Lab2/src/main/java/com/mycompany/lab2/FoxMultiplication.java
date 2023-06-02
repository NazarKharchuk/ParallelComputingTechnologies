/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Назар
 */
public class FoxMultiplication {

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

    public FoxMultiplication(Matrix firstMatrix, Matrix secondMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrixRowsCount = firstMatrix.getRows();
        this.resultMatrixColsCount = secondMatrix.getColumns();
        this.resultMatrix = new Matrix(resultMatrixRowsCount, resultMatrixColsCount);
    }

    public void runFoxMultiplication(int numThreads) {
        if (firstMatrix.getColumns() != secondMatrix.getRows()) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }
        
        startTime = System.currentTimeMillis();

        int blockSize = resultMatrixRowsCount / (int) Math.sqrt(numThreads);
        Matrix[][] firstBlockMatrix = firstMatrix.getBlocks(blockSize);
        Matrix[][] secondBlockMatrix = secondMatrix.getBlocks(blockSize);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        Future<Matrix>[] futures;
        try {
            for (int i = 0; i < firstBlockMatrix.length; i++) {
                for (int j = 0; j < secondBlockMatrix.length; j++) {
                    futures = new Future[firstBlockMatrix.length];

                    for (int k = 0; k < firstBlockMatrix.length; k++) {
                        futures[k] = executor.submit(new FoxTask(firstBlockMatrix[i][k], secondBlockMatrix[k][j]));
                    }

                    Matrix blockResult = futures[0].get();

                    for (int k = 1; k < firstBlockMatrix.length; k++) {
                        blockResult = blockResult.add(futures[k].get());
                    }
                    int startRow = i * blockSize;
                    int startCol = j * blockSize;
                    resultMatrix.setSubMatrix(startRow, startCol, blockResult);
                }
            }
            
            endTime = System.currentTimeMillis();
        } catch (InterruptedException | ExecutionException e) {
        } finally {
            executor.shutdown();
        }
    }
}
