/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task2;

import javax.swing.JLabel;

/**
 *
 * @author Назар
 */
public class Counter {

    private int count;
    private JLabel label;

    public Counter(JLabel l){
        count = 0;
        label = l;
    }
    
    public synchronized void increment() {
        count++;
        label.setText("The pocket has balls - " + count);
    }

    public int getCount() {
        return count;
    }
}
