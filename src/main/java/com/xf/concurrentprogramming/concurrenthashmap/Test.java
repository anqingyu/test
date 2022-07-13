package com.xf.concurrentprogramming.concurrenthashmap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        // 单词计数不安全测试
        demo(
                // 创建 map 集合
                // 创建 ConcurrentHashMap 对不对？
                () -> new HashMap<String, Integer>(),
                // 进行计数
                (map, words) -> {
                    for (String word : words) {
                        Integer counter = map.get(word);
                        int newValue = counter == null ? 1 : counter + 1;
                        map.put(word, newValue);
                    }
                }
        );

        // 改进方案一的测试
        demo(
                () -> new ConcurrentHashMap<String, LongAdder>(),
                (map, words) -> {
                    for (String word : words) {
                        // 注意不能使用 putIfAbsent，此方法返回的是上一次的 value，首次调用返回 null
                        map.computeIfAbsent(word, (key) -> new LongAdder()).increment();
                    }
                }
        );

        // 改进方案二的测试
        demo(
                () -> new ConcurrentHashMap<String, Integer>(),
                (map, words) -> {
                    for (String word : words) {
                        // 函数式编程，无需原子变量
                        map.merge(word, 1, Integer::sum);
                    }
                }
        );
    }

    /**
     * 实现单词计数
     * @param supplier 提供一个 map 集合，用来存放每个单词的计数结果，key 为单词，value 为计数
     * @param consumer 提供一组操作，保证计数的安全性，会传递 map 集合以及 单词 List
     * @param <V>
     */
    private static <V> void demo(Supplier<Map<String,V>> supplier,
                                 BiConsumer<Map<String,V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        // 创建一个线程集合
        List<Thread> ts = new ArrayList<>();
        // 创建26个线程，每个线程都统计一个文件中的单词个数
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                // 根据文件名读取D:/test_temp/中的文件内容，并拼成一个字符串返回
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }
        // 循环启动线程
        ts.forEach(t->t.start());

        // 等待所有线程处理完毕
        ts.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 打印
        System.out.println(counterMap);
    }

    /**
     * 根据文件名读取D:/test_temp/中的文件内容，并拼成一个字符串返回
     * @param i
     * @return
     */
    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:/test_temp/" + i +".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
