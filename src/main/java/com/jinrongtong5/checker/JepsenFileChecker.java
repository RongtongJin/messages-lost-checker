package com.jinrongtong5.checker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class JepsenFileChecker {

    private static final String FILE_NAME = "/Users/jinrongtong/Desktop/history.txt";

    public static void main(String[] args) throws IOException {
        singleFileCheck(FILE_NAME);
    }

    public static void singleFileCheck(String fileName) throws IOException {
        if (!new File(fileName).exists()) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        long startTime = System.currentTimeMillis();
        Set<String> dequeueSet = new HashSet<>();
        Set<String> enqueueSet = new HashSet<>();
        AtomicLong enqueueCount = new AtomicLong();
        AtomicLong dequeueCount = new AtomicLong();
        checkFile(fileName, enqueueSet, dequeueSet, enqueueCount, dequeueCount);
        Utils.outputResult(enqueueSet, enqueueCount, dequeueCount);
        System.out.println(
            "check " + fileName + " file spend " + (System.currentTimeMillis() - startTime) / 1000 + " s.");
    }

    public static void checkFile(String fileName, Set<String> enqueueSet, Set<String> dequeueSet,
        AtomicLong enqueueCount, AtomicLong dequeueCount) throws IOException {
        System.out.println("start check " + fileName + " file....");
        Files.lines(Paths.get(fileName)).map(x -> x.split("\t")).filter(x -> x[1].equals(":ok")).forEach(line -> {
            if (line[2].equals(":enqueue")) {
                enqueueCount.incrementAndGet();
                enqueueSet.add(line[3]);
            } else if (line[2].equals(":dequeue")) {
                dequeueCount.incrementAndGet();
                if (enqueueSet.contains(line[3])) {
                    enqueueSet.remove(line[3]);
                } else {
                    dequeueSet.add(line[3]);
                }
            } else if(line[2].equals(":drain")){
                if(!line[3].equals("[ ]")){
                    Arrays.stream(line[3].substring(1,line[3].length()-1).split(" ")).forEach(v->{
                        dequeueCount.incrementAndGet();
                        if (enqueueSet.contains(v)) {
                            enqueueSet.remove(v);
                        } else {
                            dequeueSet.add(v);
                        }
                    });
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
