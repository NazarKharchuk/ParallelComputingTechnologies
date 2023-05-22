/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    private class DocumentSearchTask extends RecursiveTask<HashMap<String, HashMap<String, Integer>>> {

        private final Document document;
        private final List<String> keyWords;

        DocumentSearchTask(Document document, List<String> keyWords) {
            super();
            this.document = document;
            this.keyWords = keyWords;
        }

        @Override
        protected HashMap<String, HashMap<String, Integer>> compute() {
            HashMap<String, Integer> countKeykords = new HashMap();
            for (String line : document.getLines()) {
                for (String word : wordsIn(line)) {
                    word = word.toLowerCase();
                    if (keyWords.contains(word)) {
                        if (countKeykords.containsKey(word)) {
                            countKeykords.put(word, countKeykords.get(word) + 1);
                        } else {
                            countKeykords.put(word, 1);
                        }
                    }
                }
            }
            HashMap<String, HashMap<String, Integer>> result = new HashMap();
            result.put(document.getPath(), countKeykords);
            return result;
        }
    }

    private class FolderSearchTask extends RecursiveTask<HashMap<String, HashMap<String, Integer>>> {

        private final Folder folder;
        private final List<String> keyWords;

        FolderSearchTask(Folder folder, List<String> keyWords) {
            super();
            this.folder = folder;
            this.keyWords = keyWords;
        }

        @Override
        protected HashMap<String, HashMap<String, Integer>> compute() {
            List<RecursiveTask<HashMap<String, HashMap<String, Integer>>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderSearchTask task = new FolderSearchTask(subFolder, keyWords);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentSearchTask task = new DocumentSearchTask(document, keyWords);
                forks.add(task);
                task.fork();
            }

            HashMap<String, HashMap<String, Integer>> resultMap = new HashMap();
            for (RecursiveTask<HashMap<String, HashMap<String, Integer>>> task : forks) {
                HashMap<String, HashMap<String, Integer>> taskResult = task.join();
                for (HashMap.Entry<String, HashMap<String, Integer>> entry : taskResult.entrySet()) {
                    resultMap.put(entry.getKey(), entry.getValue());
                }
            }
            return resultMap;
        }
    }

    public HashMap<String, HashMap<String, Integer>> startSearch(Folder folder, List<String> keyWords) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, keyWords));
    }

    public static void main(String[] args) throws IOException {
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File("D:\\Code\\ParallelComputingTechnologies\\Lab4\\Folder"));

        List<String> keyWords = new ArrayList<>(List.of("thread", "programming",
                "technique", "layer", "technologies", "systems", "processes"));

        HashMap<String, HashMap<String, Integer>> map = wordCounter.startSearch(folder, keyWords);

        for (String file : map.keySet()) {
            System.out.println("File: " + file);
            HashMap<String, Integer> wordCountMap = map.get(file);
            for (String word : wordCountMap.keySet()) {
                int count = wordCountMap.get(word);
                System.out.println("\t" + word + ": " + count);
            }
            System.out.println();
        }
    }
}
