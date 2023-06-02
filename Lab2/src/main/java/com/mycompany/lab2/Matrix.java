/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import java.util.Random;

/**
 *
 * @author Назар
 */
public class Matrix {

    private final int rows;
    private final int columns;
    private final int[][] matrix;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getElement(int row, int column) {
        return matrix[row][column];
    }

    public void setElement(int row, int column, int value) {
        matrix[row][column] = value;
    }

    public int[] getRow(int row) {
        return matrix[row];
    }

    public void setData(Matrix other) {
        if (this.rows != other.rows || this.columns != other.columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.setElement(i, j, other.getElement(i, j));
            }
        }
    }

    public void setTheSameValue(int value) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.setElement(i, j, (i * 10 + j));//(i * 10 + j));
            }
        }
    }

    public void generateMatrix() {
        Random random = new Random();
        int minValue = 0, maxValue = 1000;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int value = random.nextInt(maxValue - minValue + 1) + minValue;
                this.setElement(i, j, value);
            }
        }
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(this.getElement(i, j) + " ");
            }
            System.out.println();
        }
    }

    public Matrix transpose() {
        Matrix result = new Matrix(columns, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setElement(j, i, this.getElement(i, j));
            }
        }

        return result;
    }

    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.columns != other.columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions.");
        }

        Matrix result = new Matrix(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setElement(i, j, this.getElement(i, j) + other.getElement(i, j));
            }
        }

        return result;
    }

    public Matrix multiply(Matrix other) {
        if (this.columns != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        Matrix result = new Matrix(this.rows, other.columns);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.columns; j++) {
                int sum = 0;
                for (int k = 0; k < this.columns; k++) {
                    sum += this.getElement(i, k) * other.getElement(k, j);
                }
                result.setElement(i, j, sum);
            }
        }

        return result;
    }

    public Matrix getSubMatrix(int startRow, int endRow, int startCol, int endCol) {
        int subMatrixRows = endRow - startRow + 1;
        int subMatrixCols = endCol - startCol + 1;
        Matrix subMatrix = new Matrix(subMatrixRows, subMatrixCols);

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                int subMatrixRow = i - startRow;
                int subMatrixCol = j - startCol;
                int element = this.getElement(i, j);
                subMatrix.setElement(subMatrixRow, subMatrixCol, element);
            }
        }

        return subMatrix;
    }

    public void setSubMatrix(int startRow, int startCol, Matrix subMatrix) {
        int subMatrixRows = subMatrix.getRows();
        int subMatrixCols = subMatrix.getColumns();
        for (int i = 0; i < subMatrixRows; i++) {
            for (int j = 0; j < subMatrixCols; j++) {
                int rowIndex = startRow + i;
                int colIndex = startCol + j;
                int value = subMatrix.getElement(i, j);
                setElement(rowIndex, colIndex, value);
            }
        }
    }

    public Matrix[][] getBlocks(int blockSize) {
        int blocksCount = rows / blockSize;

        Matrix[][] blocks = new Matrix[blocksCount][blocksCount];

        for (int i = 0; i < blocksCount; i++) {
            for (int j = 0; j < blocksCount; j++) {
                int startRow = i * blockSize;
                int endRow = startRow + blockSize - 1;
                int startCol = j * blockSize;
                int endCol = startCol + blockSize - 1;
                blocks[i][j] = getSubMatrix(startRow, endRow, startCol, endCol);
            }
        }

        return blocks;
    }

    public boolean equals(Matrix other) {
        if (rows != other.rows || columns != other.columns) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (getElement(i, j) != other.getElement(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }
}
