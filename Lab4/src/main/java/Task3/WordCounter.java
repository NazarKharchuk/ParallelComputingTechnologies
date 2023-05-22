/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordCounter {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    private class DocumentSearchTask extends RecursiveTask<Set<String>> {

        private final Document document;

        DocumentSearchTask(Document document) {
            super();
            this.document = document;
        }

        @Override
        protected Set<String> compute() {
            Set<String> wordsSet = new HashSet<String>();
            for (String line : document.getLines()) {
                String[] words = wordsIn(line);
                for (String word : words) {
                    var newWord = word.toLowerCase();
                    wordsSet.add(newWord);
                }
            }
            return wordsSet;
        }
    }

    private class FolderSearchTask extends RecursiveTask<Set<String>> {

        private final Folder folder;

        FolderSearchTask(Folder folder) {
            super();
            this.folder = folder;
        }

        @Override
        protected Set<String> compute() {
            List<RecursiveTask<Set<String>>> forks = new LinkedList<>();
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

            Boolean isFirst = true;
            Set<String> wordsSet = new HashSet<String>();
            for (RecursiveTask<Set<String>> task : forks) {
                Set<String> taskResult = task.join();
                if (isFirst) {
                    wordsSet = taskResult;
                    isFirst = false;
                } else {
                    wordsSet.retainAll(taskResult);
                }
            }
            return wordsSet;
        }
    }

    public Set<String> startForkJoin(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

    public static void main(String[] args) throws IOException {
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File("D:\\Code\\ParallelComputingTechnologies\\Lab4\\Folder"));

        Set<String> wordsSet = wordCounter.startForkJoin(folder);

        System.out.println("Words set: ");
        for (String word : wordsSet) {
            System.out.println(word + ";");
            
        }
    }
}
