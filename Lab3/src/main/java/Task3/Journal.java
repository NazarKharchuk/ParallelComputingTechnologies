/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Назар
 */
public class Journal {

    public int countMarks;
    public HashMap<Group, HashMap<Student, ArrayList<Integer>>> journal = new HashMap<>();

    public Journal(ArrayList<Group> groups) {
        this.countMarks = 0;
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
            //groupMarks.put(s, studentMarks);
            countMarks++;
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
