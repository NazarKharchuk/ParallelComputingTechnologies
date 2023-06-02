/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lab2;

/**
 *
 * @author Назар
 */
public class Lab2 {

    public static void main(String[] args) {
        int matrixSize = 500;
        int numThreads = 4;

        if (!checkInputData(matrixSize, numThreads)) {
            System.out.println("Incorrect input data!;");
            return;
        }

        var firstMatrix = new Matrix(matrixSize, matrixSize);
        firstMatrix.setTheSameValue(1);

        var secondMatrix = new Matrix(matrixSize, matrixSize);
        secondMatrix.setTheSameValue(1);

        /*System.out.println("First matrix:");
        firstMatrix.print();

        System.out.println("Second matrix");
        secondMatrix.print();*/
        
        var stripeMultiplication = new StripeMultiplication(firstMatrix, secondMatrix);
        stripeMultiplication.runStripeMultiplication(numThreads);
        var stripeResult = stripeMultiplication.getResult();

        var foxMultiplication = new FoxMultiplication(firstMatrix, secondMatrix);
        foxMultiplication.runFoxMultiplication(numThreads);
        var foxResult = foxMultiplication.getResult();
        
        var coorrectResult = firstMatrix.multiply(secondMatrix);

        stripeResult.printResult("Stripe", coorrectResult, false);
        foxResult.printResult("Fox", coorrectResult, false);
    }

    private static boolean checkInputData(int matrixSize, int numThreads) {
        int sqrt = (int) Math.sqrt(numThreads);
        if (sqrt * sqrt != numThreads) {
            return false;
        }

        if (matrixSize % sqrt != 0) {
            return false;
        }

        return true;
    }
}
