/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

/**
 *
 * @author Назар
 */
public class Result {

    private final Matrix resultMatrix;
    private final long time;

    public Result(Matrix resultMatrix, long time) {
        this.resultMatrix = resultMatrix;
        this.time = time;
    }

    public void printResult(String algoName, Matrix correctResult, boolean printMatrix) {
        System.out.println("\n" + algoName + " algorithm multiplied matrixes in " + time + " milliseconds;");

        if (resultMatrix.equals(correctResult)) {
            System.out.println(algoName + " algorithm multiplied matrixes correctly;");
        } else {
            System.out.println(algoName + " algorithm multiplied matrixes not correctly;");
        }

        if (printMatrix) {
            System.out.println(algoName + " algorithm multiplied matrix:");
            resultMatrix.print();
        }
    }
}
