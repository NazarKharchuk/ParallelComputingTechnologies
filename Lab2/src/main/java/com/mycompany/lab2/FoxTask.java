/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import java.util.concurrent.Callable;

/**
 *
 * @author Назар
 */
public class FoxTask implements Callable<Matrix> {

    private final Matrix firstMatrix;
    private final Matrix secondMatrix;

    public FoxTask(Matrix firstMatrix, Matrix secondMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
    }

    @Override
    public Matrix call() {
        return firstMatrix.multiply(secondMatrix);
    }
}
