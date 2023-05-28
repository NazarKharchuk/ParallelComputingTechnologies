/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Назар
 */
public class Queue {

    private final ReentrantLock locker;
    private final Condition notEmpty;

    private final int itemsCount;
    private int putPtr, takePtr, count;
    private final int[] items;

    private int rejectionCount;
    private int addedCount;

    public Queue(int maxCount) {
        locker = new ReentrantLock();
        notEmpty = locker.newCondition();
        itemsCount = maxCount;
        putPtr = takePtr = count = 0;
        items = new int[itemsCount];
        addedCount = 0;
        rejectionCount = 0;
    }

    public int getAddedCount() {
        return addedCount;
    }

    public int getRejectionCount() {
        return rejectionCount;
    }

    public int getQueueCount() {
        return count;
    }

    public int take() {
        locker.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int item = items[takePtr];
            --count;
            if (++takePtr == items.length) {
                takePtr = 0;
            }
            return item;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            locker.unlock();
        }
    }

    public void put(int item) {
        locker.lock();
        try {
            addedCount++;
            if (count == items.length) {
                rejectionCount++;
                return;
            }
            items[putPtr] = item;
            ++count;
            if (++putPtr == items.length) {
                putPtr = 0;
            }
            notEmpty.signal();
        } finally {
            locker.unlock();
        }
    }
}
