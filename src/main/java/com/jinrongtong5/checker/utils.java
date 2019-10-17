package com.jinrongtong5.checker;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Utils {
    public static void outputResult(Set<String> enqueueSet, AtomicLong enqueueCount, AtomicLong dequeueCount) {
        System.out.println("result:");
        System.out.println("enqueue count: " + enqueueCount);
        System.out.println("dequeue count: " + dequeueCount);
        System.out.println("no missing messages: " + enqueueSet.isEmpty());
    }
}
