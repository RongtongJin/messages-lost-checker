package com.jinrongtong5.checker;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class MutilFileChecker {

    public static final String DIR = "/Users/jinrongtong/Desktop/test/";

    public static void main(String[] args) {
        File dir = new File(DIR);
        if (!dir.isDirectory()) {
            System.out.println("must be a directory");
            System.exit(-1);
        }
        long startTime = System.currentTimeMillis();

        Set<String> dequeueSet = new HashSet<>();
        Set<String> enqueueSet = new HashSet<>();
        AtomicLong enqueueCount = new AtomicLong();
        AtomicLong dequeueCount = new AtomicLong();

        Arrays.stream(dir.listFiles()).forEach(file -> {
            try {
                SingleFileChecker.checkFile(file.getPath(), enqueueSet, dequeueSet, enqueueCount, dequeueCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Utils.outputResult(enqueueSet, enqueueCount, dequeueCount);
        System.out.println("spend " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }
}
