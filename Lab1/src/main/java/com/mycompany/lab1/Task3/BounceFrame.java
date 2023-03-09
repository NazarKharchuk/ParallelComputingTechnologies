/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.Task3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Назар
 */
public class BounceFrame extends JFrame {

    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart10 = new JButton("Start 10");
        JButton buttonStart100 = new JButton("Start 100");
        JButton buttonStart1000 = new JButton("Start 1000");
        JButton buttonStop = new JButton("Stop");
        buttonStart10.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 10; i++) {
                    Ball b = new Ball(canvas, false);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.setPriority(3); 
                    thread.start();
                }

                Ball b = new Ball(canvas, true);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.setPriority(9); 
                thread.start();
            }
        });
        buttonStart100.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 100; i++) {
                    Ball b = new Ball(canvas, false);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.setPriority(3); 
                    thread.start();
                }

                Ball b = new Ball(canvas, true);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.setPriority(9); 
                thread.start();
            }
        });
        buttonStart1000.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 1000; i++) {
                    Ball b = new Ball(canvas, false);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.setPriority(3); 
                    thread.start();
                }

                Ball b = new Ball(canvas, true);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.setPriority(9); 
                thread.start();
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart10);
        buttonPanel.add(buttonStart100);
        buttonPanel.add(buttonStart1000);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
