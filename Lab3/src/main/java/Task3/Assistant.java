/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.Random;

/**
 *
 * @author Назар
 */
public class Assistant implements Runnable {

    private Group group;
    private Journal journal;
    private int countWeeks;
    private Random random;

    public Assistant(Journal journal, Group group, int countWeeks) {
        this.journal = journal;
        this.group = group;
        this.countWeeks = countWeeks;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < countWeeks; i++) {
            for (Student s : group.students) {
                journal.setMark(group, s, random.nextInt(101));
            }
        }
    }
}
