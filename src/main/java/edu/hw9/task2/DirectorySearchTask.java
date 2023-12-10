package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DirectorySearchTask extends RecursiveTask<Integer> {
    private static final int LENGTH = 1000;
    private final File directory;

    public DirectorySearchTask(File directory) {
        this.directory = directory;
    }

    @Override
    protected Integer compute() {
        int fileCount = 0;
        File[] files = directory.listFiles();

        if (Objects.nonNull(files)) {
            List<DirectorySearchTask> subtasks = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    DirectorySearchTask subtask = new DirectorySearchTask(file);
                    subtask.fork();
                    subtasks.add(subtask);
                } else {
                    fileCount++;
                }
            }

            for (DirectorySearchTask subtask : subtasks) {
                fileCount += subtask.join();
            }
        } else {
            return 0;
        }

        return fileCount;
    }

    public static boolean countFiles(String path) {
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            DirectorySearchTask rootTask = new DirectorySearchTask(new File(path));

            return forkJoinPool.invoke(rootTask) > LENGTH;
        }
    }
}
