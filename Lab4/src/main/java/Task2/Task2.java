/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task2;

/**
 *
 * @author Назар
 */
public class Task2 {

    public static void main(String[] args) {
        Boolean isParallel = false;

        int countWeeks = 10;
        int countStudents = 30;
        int countGroups = 50;

        long startTimeF, endTimeF;
        long startTimeT, endTimeT;

        startTimeT = System.currentTimeMillis();
        var threadsAlgo = new Threads();
        threadsAlgo.runThreadsAlgo(countWeeks, countStudents, countGroups);
        endTimeT = System.currentTimeMillis();

        startTimeF = System.currentTimeMillis();
        var forkJoinAlgo = new ForkJoin();
        forkJoinAlgo.runForkJoinAlgo(countWeeks, countStudents, countGroups);
        endTimeF = System.currentTimeMillis();

        System.out.println("Weeks count: " + countWeeks);
        System.out.println("Students count: " + countStudents);
        System.out.println("Groups count: " + countGroups);
        System.out.println("Time (Threads): " + (endTimeT - startTimeT) + "ms");
        System.out.println("Time (ForkJoin): " + (endTimeF - startTimeF) + "ms");
    }
}
