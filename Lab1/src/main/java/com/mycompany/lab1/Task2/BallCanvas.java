/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Назар
 */
public class BallCanvas extends JPanel {

    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Pocket> pockets;

    public BallCanvas(ArrayList<Pocket> p) {
        pockets = p;
    }

    public void add(Ball b) {
        this.balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            b.draw(g2);
        }
        for (int i = 0; i < pockets.size(); i++) {
            Pocket p = pockets.get(i);
            p.draw(g2);
        }
    }
}
