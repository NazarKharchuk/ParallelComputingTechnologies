/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 *
 * @author Назар
 */
public class ForkJoin {

    public void runForkJoinAlgo(int countWeeks, int countStudents, int countGroups) {
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i = 0; i < countGroups; i++) {
            groups.add(new Group(countStudents));
        }

        Journal journal = new Journal(groups);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ArrayList<ForkJoinTask<?>> tasks = new ArrayList<>();

        for (int i = 0; i < countGroups; i++) {
            Assistant assistant = new Assistant(journal, groups.get(i), countWeeks);
            tasks.add(forkJoinPool.submit(assistant));
        }

        Lecturer lecturer = new Lecturer(journal, groups, countWeeks);
        tasks.add(forkJoinPool.submit(lecturer));

        for (ForkJoinTask<?> task : tasks) {
            task.join();
        }

        forkJoinPool.shutdown();

        //journal.print();
    }
}

class Student {

    public UUID studentId;

    public Student() {
        this.studentId = UUID.randomUUID();
    }
}

class Group {

    public UUID groupId;
    public ArrayList<Student> students;

    public Group(int countStudents) {
        this.groupId = UUID.randomUUID();
        this.students = new ArrayList<>();
        for (int i = 0; i < countStudents; i++) {
            students.add(new Student());
        }
    }
}

class Journal {

    public HashMap<Group, HashMap<Student, ArrayList<Integer>>> journal = new HashMap<>();

    public Journal(ArrayList<Group> groups) {
        for (Group g : groups) {
            HashMap<Student, ArrayList<Integer>> marks = new HashMap<>();
            for (Student s : g.students) {
                marks.put(s, new ArrayList<Integer>());
            }
            this.journal.put(g, marks);
        }
    }

    public void setMark(Group g, Student s, Integer mark) {
        HashMap<Student, ArrayList<Integer>> groupMarks = journal.get(g);
        ArrayList<Integer> studentMarks = groupMarks.get(s);

        synchronized (studentMarks) {
            studentMarks.add(mark);
        }
    }

    public void print() {
        for (HashMap.Entry<Group, HashMap<Student, ArrayList<Integer>>> groupEntry : journal.entrySet()) {
            Group group = groupEntry.getKey();
            System.out.println("Group: " + group.groupId);

            HashMap<Student, ArrayList<Integer>> studentMap = groupEntry.getValue();
            for (HashMap.Entry<Student, ArrayList<Integer>> studentEntry : studentMap.entrySet()) {
                Student student = studentEntry.getKey();
                System.out.println("\tStudent: " + student.studentId);

                ArrayList<Integer> gradesList = studentEntry.getValue();
                System.out.println("\t\tMarks: " + gradesList.toString());
            }
        }
    }
}

class Assistant implements Runnable {

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

class Lecturer implements Runnable {

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
