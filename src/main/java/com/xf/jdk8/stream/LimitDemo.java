package com.xf.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于limit()实现数据截取:该方法会返回一个不超过给定长度的流。
 */
public class LimitDemo {
    public static void main(String[] args) {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);
        List<Integer> collect = numberList.stream().limit(5).collect(Collectors.toList());
        System.out.println(collect);
    }
}
