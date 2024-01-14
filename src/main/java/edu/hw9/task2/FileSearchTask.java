package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FileSearchTask extends RecursiveTask<List<File>> {
    private final File directory;
    private final long fileSizeThreshold;
    private final String fileExtension;

    public FileSearchTask(File directory, long fileSizeThreshold, String fileExtension) {
        this.directory = directory;
        this.fileSizeThreshold = fileSizeThreshold;
        this.fileExtension = fileExtension;
    }

    @Override
    protected List<File> compute() {
        List<File> result = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            List<FileSearchTask> subtasks = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    FileSearchTask subtask = new FileSearchTask(file, fileSizeThreshold, fileExtension);
                    subtask.fork();
                    subtasks.add(subtask);
                } else {
                    if (file.length() > fileSizeThreshold && file.getName().endsWith(fileExtension)) {
                        result.add(file);
                    }
                }
            }

            for (FileSearchTask subtask : subtasks) {
                result.addAll(subtask.join());
            }
        }

        return result;
    }

    public static List<File> searchFiles(String path, String fileExtension, int fileSizeThreshold) {
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            FileSearchTask rootTask = new FileSearchTask(new File(path), fileSizeThreshold, fileExtension);

            return forkJoinPool.invoke(rootTask);
        }
    }
}
