/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task5;

/**
 *
 * @author Назар
 */
public class SymbolThread extends Thread {

    private boolean flag;
    private char symbol;
    private Agent agent;

    public SymbolThread(boolean f, char s) {
        flag = f;
        symbol = s;
        agent = null;
    }

    public SymbolThread(boolean f, char s, Agent a) {
        flag = f;
        symbol = s;
        agent = a;
    }

    public void run() {
        if (agent != null) {
            while (!agent.GetFinish()) {
                agent.check(flag, symbol);
            }
        } else {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 100; j++) {
                    System.out.print(symbol);
                }
                System.out.println();
            }
        }
    }
}
