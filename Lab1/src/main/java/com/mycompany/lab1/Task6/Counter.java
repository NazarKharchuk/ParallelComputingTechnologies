/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task6;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Назар
 */
public class Counter {

    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    public int GetCount() {
        return count;
    }

    //без синхронізації
    public void incrementSimple() {
        count++;
    }

    public void decrementSimple() {
        count--;
    }

    //синхронізований метод
    public synchronized void incrementSync() {
        count++;
    }

    public synchronized void decrementSync() {
        count--;
    }

    //блокування об’єкта
    public void incrementLock() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void decrementLock() {
        lock.lock();
        try {
            count--;
        } finally {
            lock.unlock();
        }
    }
}
