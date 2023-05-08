/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task2;

import java.util.Random;

public class Producer implements Runnable {

    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            drop.put(random.nextInt(1000));
            /*try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
            }*/
        }
    }
}
