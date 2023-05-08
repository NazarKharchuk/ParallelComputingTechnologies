/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Назар
 */
public class Group {

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
