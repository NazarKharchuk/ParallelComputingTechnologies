/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JLabel;

/**
 *
 * @author Назар
 */
public class Pocket {

    private Component canvas;
    private static final int SIZE = 50;
    private int x = 0;
    private int y = 0;
    private Counter counter;

    public Pocket(Component c, Counter _counter, int x, int y) {
        this.canvas = c;
        counter = _counter;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));
    }

    public boolean caughtBall(Ball b) {
        int ball_x = b.getX();
        int ball_y = b.getY();
        int ball_size = b.getSize();
        if (Point2D.distance(x + SIZE / 2, y + SIZE / 2, ball_x + ball_size / 2, ball_y + ball_size / 2)
                < (SIZE / 2 - ball_size / 2)) {
            counter.increment();
            return true;
        }
        return false;
    }
}
