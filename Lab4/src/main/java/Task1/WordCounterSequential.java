/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WordCounterSequential {

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    private HashMap<Integer, Integer> countWords(Document document) {

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

    private HashMap<Integer, Integer> countOccurrences(Folder folder) {
        HashMap<Integer, Integer> lengthMap = new HashMap<>();
        for (Folder subFolder : folder.getSubFolders()) {
            var res = (countOccurrences(subFolder));
            for (HashMap.Entry<Integer, Integer> entry : res.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();

                if (lengthMap.containsKey(key)) {
                    lengthMap.put(key, lengthMap.get(key) + value);
                } else {
                    lengthMap.put(key, value);
                }
            }
        }
        for (Document document : folder.getDocuments()) {
            HashMap<Integer, Integer> documentMap = countWords(document);
            for (HashMap.Entry<Integer, Integer> entry : documentMap.entrySet()) {
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

    public HashMap<Integer, Integer> countOccurrencesInSequential(Folder folder) {
        return countOccurrences(folder);
    }

    public static void main(String[] args) throws IOException {
        WordCounterSequential wordCounter = new WordCounterSequential();
        Folder folder = Folder.fromDirectory(new File("D:\\Code\\ParallelComputingTechnologies\\Lab4\\NewFolder"));

        long startTime = System.currentTimeMillis();
        HashMap<Integer, Integer> lengthMap = wordCounter.countOccurrencesInSequential(folder);
        long endTime = System.currentTimeMillis();

        int totalWords = calculateTotalWords(lengthMap);
        double averageLength = calculateAverageLength(lengthMap, totalWords);
        double standardDeviation = calculateStandardDeviation(lengthMap, totalWords, averageLength);

        System.out.println("Sequential algorithm");
        System.out.println("Time (ms): " + (endTime - startTime) + "ms");
        System.out.println("Total words count: " + totalWords);
        System.out.println("Average length: " + averageLength);
        System.out.println("Standard deviation: " + standardDeviation);
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
