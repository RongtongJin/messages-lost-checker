package com.jinrongtong5.checker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class SingleFileChecker {

    private static final String FILE_NAME = "/Users/jinrongtong/Desktop/msglogaa";

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        Set<String> dequeueSet = new HashSet<>();
        Set<String> enqueueSet = new HashSet<>();
        AtomicLong enqueueCount = new AtomicLong();
        AtomicLong dequeueCount = new AtomicLong();
        checkFile(FILE_NAME, enqueueSet, dequeueSet, enqueueCount, dequeueCount);
        Utils.outputResult(enqueueSet, enqueueCount, dequeueCount);
        System.out.println(
            "check " + FILE_NAME + " file spend " + (System.currentTimeMillis() - startTime) / 1000 + " s.");
    }

    public static void checkFile(String fileName, Set<String> enqueueSet, Set<String> dequeueSet,
        AtomicLong enqueueCount, AtomicLong dequeueCount) throws IOException {
        System.out.println("start check " + fileName + " file....");
        Files.lines(Paths.get(fileName)).map(x->x.split(" ")).forEach(line -> {
            if (line[1].equals("enqueue")) {
                enqueueCount.incrementAndGet();
                enqueueSet.add(line[2]);
            } else {
                dequeueCount.incrementAndGet();
                if (enqueueSet.contains(line[2])) {
                    enqueueSet.remove(line[2]);
                } else {
                    dequeueSet.add(line[2]);
                }
            }
        });
        check(enqueueSet, dequeueSet);
        System.out.println("end check " + fileName + " file....");
    }

    private static void check(Set<String> enqueueSet, Set<String> dequeueSet) {
        dequeueSet.removeIf(x -> {
            boolean exist = enqueueSet.contains(x);
            if (exist) {
                enqueueSet.remove(x);
            }
            return exist;
        });
    }
}
