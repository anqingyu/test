package com.xf.jdk18;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("1" +2);

        String[] arr = {"java", "php", "python"};
        List<String> strings = Arrays.asList(arr);
//        Arrays.sort(arr, (o1,o2) -> o1.compareTo(o2));
        strings.forEach(s -> {
            if (s.equals("java")){
                System.out.println(true);
            }
        });
        strings.sort((o1,o2) -> o1.compareTo(o2));
        strings.forEach(s -> System.out.println(s));
    }
}
