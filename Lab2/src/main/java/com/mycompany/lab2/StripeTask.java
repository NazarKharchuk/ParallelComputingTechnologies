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
public class StripeTask implements Callable<Integer> {

    private final int[] row;
    private final int[] col;

    public StripeTask(int[] row, int[] col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public Integer call() {
        if (row.length != col.length) {
            throw new IllegalArgumentException("Row and column lengths must be equal.");
        }

        int result = 0;
        for (int i = 0; i < row.length; i++) {
            result += row[i] * col[i];
        }

        return result;
    }
}
