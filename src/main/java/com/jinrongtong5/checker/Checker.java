package com.jinrongtong5.checker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checker {

    private static final String fileName = "/Users/jinrongtong/output.txt";

    public static void main(String[] args) throws IOException {

        Set<String> enqueueSet = getLinesStream().filter(x -> x[1].equals("enqueue")).map(x -> x[2]).collect(
            Collectors.toSet());
        System.out.println("enqueueSet size: " + enqueueSet.size());
        Set<String> dequeueSet = getLinesStream().filter(x -> x[1].equals("dequeue")).map(x -> x[2]).collect(
            Collectors.toSet());
        System.out.println("dequeueSet size: " + enqueueSet.size());
        boolean noMissingMessages = dequeueSet.containsAll(enqueueSet);
        boolean noUnexpectMessages = enqueueSet.containsAll(dequeueSet);

        System.out.println("No missing messages: " + noMissingMessages);
        System.out.println("No unexpect messages: " + noUnexpectMessages);
        if (noUnexpectMessages) {
            long dequeueCount = getLinesStream().filter(x -> x[1].equals("dequeue")).map(x -> x[2]).count();
            System.out.println("Number of duplicate messages: "+(dequeueCount-dequeueSet.size()));
        }
        System.out.println("valid: "+(noMissingMessages&noUnexpectMessages));
    }

    public static Stream<String[]> getLinesStream() throws IOException {
        return Files.lines(Paths.get(fileName)).map(line -> {
            String[] array = line.split(" ");
            return array;
        });
    }
}
