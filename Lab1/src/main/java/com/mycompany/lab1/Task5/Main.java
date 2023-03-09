/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task5;

/**
 *
 * @author Назар
 */
public class Main {

    public static void main(String[] args) {
        Agent agent = new Agent();
        SymbolThread plus = new SymbolThread(true, '+', agent);
        SymbolThread minus = new SymbolThread(false, '|', agent);
//        SymbolThread plus = new SymbolThread(true, '+');
//        SymbolThread minus = new SymbolThread(false, '|');
        plus.start();
        minus.start();
    }
}
