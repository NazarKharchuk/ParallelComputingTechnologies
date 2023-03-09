/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task6;

/**
 *
 * @author Назар
 */
public class CounterThread extends Thread {

    private boolean flag;
    private Counter counter;
    private int type;

    public CounterThread(boolean f, Counter c, int t) {
        flag = f;
        counter = c;
        type = t;
    }

    public void run() {
        switch (type) {
            case (0) -> {
                System.out.println("Метод: без синхронізації");
            }
            case (1) -> {
                System.out.println("Метод: синхронізований метод");
            }
            case (2) -> {
                System.out.println("Метод: синхронізований блок");
            }
            case (3) -> {
                System.out.println("Метод: блокування об'єкта");
            }
        }
        for (int i = 0; i < 100000; i++) {
            switch (type) {
                case (0) -> {
                    if (flag) {
                        counter.incrementSimple();
                    } else {
                        counter.decrementSimple();
                    }
                }
                case (1) -> {
                    if (flag) {
                        counter.incrementSync();
                    } else {
                        counter.decrementSync();
                    }
                }
                case (2) -> {
                    if (flag) {
                        synchronized (counter) {
                            counter.incrementSimple();
                        }
                    } else {
                        synchronized (counter) {
                            counter.decrementSimple();
                        }
                    }
                }
                case (3) -> {
                    if (flag) {
                        counter.incrementLock();
                    } else {
                        counter.decrementLock();
                    }
                }
                default -> {
                    System.out.println("Illegal instruction");
                }
            }
        }
    }
}
