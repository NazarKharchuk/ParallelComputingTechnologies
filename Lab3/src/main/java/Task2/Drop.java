/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Drop {

    private ReentrantLock locker;
    private Condition notFull;
    private Condition notEmpty;

    private int itemsCount;
    private int putPtr, takePtr, count;
    private final int[] items;

    public Drop() {
        locker = new ReentrantLock();
        notFull = locker.newCondition();
        notEmpty = locker.newCondition();
        itemsCount = 100;
        putPtr = takePtr = count = 0;
        items = new int[itemsCount];
    }

    public int take() {
        locker.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int item = items[takePtr];
            --count;
            System.out.println("    Take item: " + item + " from place: " + takePtr + "; Count: " + count + ";");
            if (++takePtr == items.length) {
                takePtr = 0;
            }
            notFull.signal();
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
            while (count == items.length) {
                notFull.await();
            }
            items[putPtr] = item;
            ++count;
            System.out.println("Put item: " + item + " in place: " + putPtr + "; Count: " + count + ";");
            if (++putPtr == items.length) {
                putPtr = 0;
            }
            notEmpty.signal();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            locker.unlock();
        }
    }
}
