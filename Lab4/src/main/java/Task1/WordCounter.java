/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordCounter {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    private class DocumentSearchTask extends RecursiveTask<HashMap<Integer, Integer>> {

        private final Document document;

        DocumentSearchTask(Document document) {
            super();
            this.document = document;
        }

        @Override
        protected HashMap<Integer, Integer> compute() {
            HashMap<Integer, Integer> lengthMap = new HashMap();
            for (String line : document.getLines()) {
                for (String word : wordsIn(line)) {
                    int wordLength = word.length();
                    if (lengthMap.containsKey(wordLength)) {
                        lengthMap.put(wordLength, lengthMap.get(wordLength) + 1);
                    } else {
                        lengthMap.put(wordLength, 1);
                    }
                }
            }
            return lengthMap;
        }
    }

    private class FolderSearchTask extends RecursiveTask<HashMap<Integer, Integer>> {

        private final Folder folder;

        FolderSearchTask(Folder folder) {
            super();
            this.folder = folder;
        }

        @Override
        protected HashMap<Integer, Integer> compute() {
            List<RecursiveTask<HashMap<Integer, Integer>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderSearchTask task = new FolderSearchTask(subFolder);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentSearchTask task = new DocumentSearchTask(document);
                forks.add(task);
                task.fork();
            }

            HashMap<Integer, Integer> lengthMap = new HashMap();
            for (RecursiveTask<HashMap<Integer, Integer>> task : forks) {
                HashMap<Integer, Integer> taskResult = task.join();
                for (HashMap.Entry<Integer, Integer> entry : taskResult.entrySet()) {
                    int key = entry.getKey();
                    int value = entry.getValue();

                    if (lengthMap.containsKey(key)) {
                        lengthMap.put(key, lengthMap.get(key) + value);
                    } else {
                        lengthMap.put(key, value);
                    }
                }
            }
            return lengthMap;
        }
    }

    public HashMap<Integer, Integer> countOccurrencesInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

    public static void main(String[] args) throws IOException {
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File("D:\\Code\\ParallelComputingTechnologies\\Lab4\\NewFolder"));

        long startTime = System.currentTimeMillis();
        HashMap<Integer, Integer> lengthMap = wordCounter.countOccurrencesInParallel(folder);
        long endTime = System.currentTimeMillis();

        int totalWords = calculateTotalWords(lengthMap);
        double averageLength = calculateAverageLength(lengthMap, totalWords);
        double standardDeviation = calculateStandardDeviation(lengthMap, totalWords, averageLength);

        System.out.println("Parallel algorithm");
        System.out.println("Time (ms): " + (endTime - startTime) + "ms");
        System.out.println("Total words count: " + totalWords);
        System.out.println("Average length: " + averageLength);
        System.out.println("Standard deviation: " + standardDeviation);
        System.out.println("Number of used processors: " + wordCounter.forkJoinPool.getParallelism());
    }

    public static int calculateTotalWords(HashMap<Integer, Integer> wordLengthMap) {
        int totalWords = 0;

        for (int count : wordLengthMap.values()) {
            totalWords += count;
        }

        return totalWords;
    }

    public static double calculateAverageLength(HashMap<Integer, Integer> wordLengthMap, int totalWords) {
        double sumOfLengths = 0;

        for (HashMap.Entry<Integer, Integer> entry : wordLengthMap.entrySet()) {
            int length = entry.getKey();
            int count = entry.getValue();

            sumOfLengths += length * count;
        }

        return sumOfLengths / totalWords;
    }

    public static double calculateStandardDeviation(HashMap<Integer, Integer> wordLengthMap, int totalWords, double averageLength) {
        double sumOfDeviationsSquared = 0;

        for (HashMap.Entry<Integer, Integer> entry : wordLengthMap.entrySet()) {
            int length = entry.getKey();
            int count = entry.getValue();

            double deviation = length - averageLength;
            sumOfDeviationsSquared += Math.pow(deviation, 2) * count;
        }

        double meanOfSquaredDeviations = sumOfDeviationsSquared / totalWords;
        return Math.sqrt(meanOfSquaredDeviations);
    }
}
