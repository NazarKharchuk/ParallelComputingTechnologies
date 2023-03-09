/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task5;

/**
 *
 * @author Назар
 */
public class Agent {

    private boolean flag;
    private boolean finish;
    private int count;

    public Agent() {
        flag = false;
        finish = false;
        count = 0;
    }

    public synchronized void check(boolean control, char symbol) {
        while (control != flag) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException " + ex.getMessage());
            }
        }
        print(symbol);
        notifyAll();
    }

    public synchronized void print(char symbol) {
        System.out.print(symbol);
        count++;
        flag = !flag;
        if (count % 100 == 0) {
            System.out.println();
        }
        if (count == 10000) {
            finish = true;
        }
    }

    public boolean GetFinish() {
        return finish;
    }
}
