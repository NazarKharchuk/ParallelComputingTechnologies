/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task2;

import java.util.Random;

public class Consumer implements Runnable {

    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            drop.take();
            /*try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
            }*/
        }
    }
}
