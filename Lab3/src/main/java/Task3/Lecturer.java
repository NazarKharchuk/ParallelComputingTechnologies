/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Назар
 */
public class Lecturer implements Runnable {

    private ArrayList<Group> groups;
    private Journal journal;
    private int countWeeks;
    private Random random;

    public Lecturer(Journal journal, ArrayList<Group> groups, int countWeeks) {
        this.journal = journal;
        this.groups = groups;
        this.countWeeks = countWeeks;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < countWeeks; i++) {
            for (Group g : groups) {
                for (Student s : g.students) {
                    journal.setMark(g, s, random.nextInt(101));
                }
            }
        }
    }
}
