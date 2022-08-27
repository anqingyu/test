package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 构建流：
 * 1.基于集合创建流
 * 2.基于值创建流
 * 3.
 */
public class StreamCreateDemo {
    /**
     * 基于值创建流:
     * 在Stream中提供了一个静态方法of，它可以接收任意数量参数，显式的创建一个流。并且会根据传入的参数类型，构建不同泛型的流。
     */
    @Test
    public void ofTest() {
        Stream<String> stringStream = Stream.of("1", "2", "3");
        stringStream.forEach(System.out::println);

        Stream<Object> stream = Stream.of("1", "2", 3, true, new Student(1, "张 三", "M", 19));
        stream.forEach(d -> System.out.println(d));
    }

    /**
     * 基于数组创建流
     */
    @Test
    public void arraysStreamTest() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6};
        Stream<Integer> integerStream = Arrays.stream(numbers);
        integerStream.forEach(System.out::println);
    }

    /**
     * 基于文件创建流
     */
    @Test
    public void filesTest() throws IOException {
        //获取指定目录下的所有子文件路径
        //Files.list(Paths.get("C:/Users/zuiwu/IdeaProjects/test/src/main/java/com/xf/jdk18/stream")).forEach(path -> System.out.println(path));

        Files.list(Paths.get("C:/Users/zuiwu/IdeaProjects/test/src/main/java/com/xf/jdk18/stream"))
                .forEach(path -> {
                    //读取每一个文件中的内容
                    try {
                        System.out.println(path);
                        Files.lines(path).forEach(content -> System.out.println(content));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
