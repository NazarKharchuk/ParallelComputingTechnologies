/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.ArrayList;

/**
 *
 * @author Назар
 */
public class Task3 {

    public static void main(String[] args) {
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i = 0; i < 3; i++) {
            groups.add(new Group(10));
        }

        Journal journal = new Journal(groups);

        int countWeeks = 10;
        ArrayList<Thread> assistants = new ArrayList<Thread>();
        for (int i = 0; i < 3; i++) {
            assistants.add(new Thread(new Assistant(journal, groups.get(i), countWeeks)));
        }
        Thread lecturer = new Thread(new Lecturer(journal, groups, countWeeks));

        for (int i = 0; i < 3; i++) {
            assistants.get(i).start();
        }
        lecturer.start();

        try {
            for (int i = 0; i < 3; i++) {
                assistants.get(i).join();
            }
            lecturer.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        journal.print();
    }
}
