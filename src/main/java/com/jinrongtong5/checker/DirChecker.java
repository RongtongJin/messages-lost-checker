package com.jinrongtong5.checker;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class DirChecker {

    public static final String DIR = "/Users/jinrongtong/Desktop/test/";

    public static void main(String[] args) {
        mutilFileCheck(DIR, null);
    }

    public static void mutilFileCheck(String directory, String prefix) {
        File dir = new File(directory);
        if (!dir.exists()) {
            System.out.println("Directory not exist.");
            System.exit(0);
        }
        if (!dir.isDirectory()) {
            System.out.println("Must be a directory.");
            System.exit(0);
        }
        long startTime = System.currentTimeMillis();
        Set<String> dequeueSet = new HashSet<>();
        Set<String> enqueueSet = new HashSet<>();
        AtomicLong enqueueCount = new AtomicLong();
        AtomicLong dequeueCount = new AtomicLong();

        Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(
            file -> prefix == null || file.getName().startsWith(prefix))
            .forEach(file -> {
                try {
                    FileChecker.checkFile(file.getPath(), enqueueSet, dequeueSet, enqueueCount, dequeueCount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        Utils.outputResult(enqueueSet, enqueueCount, dequeueCount);
        System.out.println("spend " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }
}
