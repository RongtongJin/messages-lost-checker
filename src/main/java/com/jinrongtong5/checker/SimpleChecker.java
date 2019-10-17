package com.jinrongtong5.checker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleChecker {

    private static final String FILE_NAME = "/Users/jinrongtong/Desktop/msglogaa";

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        Set<String> enqueueSet = getLinesStream().filter(x -> x[1].equals("enqueue")).map(x -> x[2]).collect(
            Collectors.toSet());
        Set<String> dequeueSet = getLinesStream().filter(x -> x[1].equals("dequeue")).map(x -> x[2]).collect(
            Collectors.toSet());
        long enqueueCount = getLinesStream().filter(x -> x[1].equals("enqueue")).map(x -> x[2]).count();
        long dequeueCount = getLinesStream().filter(x -> x[1].equals("dequeue")).map(x -> x[2]).count();
        System.out.println("enqueue count: " + enqueueCount);
        System.out.println("dequeue count: " + dequeueCount);
        boolean noMissingMessages = dequeueSet.containsAll(enqueueSet);
        boolean noUnexpectMessages = enqueueSet.containsAll(dequeueSet);
        System.out.println("No missing messages: " + noMissingMessages);
        System.out.println("No unexpect messages: " + noUnexpectMessages);
        if (noUnexpectMessages) {
            System.out.println("Number of duplicate messages: " + (dequeueCount - dequeueSet.size()));
        }
        System.out.println("valid: " + (noMissingMessages & noUnexpectMessages));
        System.out.println("spend "+(System.currentTimeMillis()-startTime)/1000+"s");
    }

    public static Stream<String[]> getLinesStream() throws IOException {
        return Files.lines(Paths.get(FILE_NAME)).map(line -> {
            String[] array = line.split(" ");
            return array;
        });
    }
}
