package com.xf.concurrentprogramming.concurrenthashmap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateTestData {
    // 26个英文字母（Alpha（阿尔法）是希腊字母中的第一位，在软件工程中，代表了最初的版本。）
    static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        // ALPHA的长度
        int length = ALPHA.length();
        int count = 200;
        // 1、创建一个大小为length*count的list集合，循环ALPHA.length()次，每次添加200个相同的字符到集合
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }

        // 2、Collections.shuffle()的作用是对集合进行重新打乱(随机排序)
        Collections.shuffle(list);

        // 3、把list集合中的数据拆成26份，并保存到文件中
        for (int i = 0; i < 26; i++) {
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("D:/test_temp/" + (i+1) + ".txt")))) {
                String collect = list.subList(i * count, (i + 1) * count).stream().collect(Collectors.joining("\n"));
                out.print(collect);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
