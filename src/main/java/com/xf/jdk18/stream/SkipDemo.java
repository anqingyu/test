package com.xf.jdk18.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于skip()实现数据跳过
 */
public class SkipDemo {
    public static void main(String[] args) {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);
        List<Integer> collect = numberList.stream().limit(5).skip(2).collect(Collectors.toList());
        System.out.println(collect);
    }
}
