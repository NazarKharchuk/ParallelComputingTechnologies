/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task6;

/**
 *
 * @author Назар
 */
public class Main {

    public static void main(String[] args) {
        int type = 0;
        Counter counter = new Counter();
        CounterThread inc = new CounterThread(true, counter, type);
        CounterThread dec = new CounterThread(false, counter, type);
        inc.start();
        dec.start();
        try {
            inc.join();
            dec.join();
        } catch (InterruptedException ex) {

        }
        System.out.println("Count = " + counter.GetCount());
    }
}
