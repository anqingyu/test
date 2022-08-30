package com.xf.jdk8.stream;

import com.xf.jdk8.lambda.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 查找：1.基于findAny()查找元素：findAny用于获取流中随机的某一个元素，并且利用短路在找到结果时，立即结束
 *      2.基于findFirst()查找元素:不管是在并行还是串行，指定返回流中的第一个元素
 */
public class FindDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "张 三", "M", 18, true));
        studentList.add(new Student(2, "李 四", "M", 19, true));
        studentList.add(new Student(3, "王 五", "F", 21, true));
        studentList.add(new Student(4, "赵 六", "F", 15, false));
        studentList.add(new Student(5, "孙 七", "F", 16, false));
        studentList.add(new Student(6, "周 八", "F", 17, false));
        studentList.add(new Student(7, "吴 九", "F", 18, false));
        studentList.add(new Student(8, "郑 十", "F", 19, false));

        // 1.1基于findAny()查找元素，使用串行流的方式
        // 当为串行流且数据较少时，获取的结果一般为流中第一个元素，但是当为并流行的时候，则会随机获取。
        for (int i = 0; i < 100; i++) {
            Optional<Student> optional2 = studentList.stream().filter(s -> s.getAge() < 20).findAny();
            if (optional2.isPresent()) {
                System.out.println(optional2.get());
            }
        }
        System.out.println("分割线1--------------------------------------------------");

        // 1.2基于findAny()查找元素，使用并行流的方式
        // 当为串行流且数据较少时，获取的结果一般为流中第一个元素，但是当为并流行的时候，则会随机获取。
        for (int i = 0; i < 100; i++) {
            Optional<Student> optional3 = studentList.parallelStream().filter(s -> s.getAge() < 20).findAny();

            optional3.ifPresent(System.out::println);
        }
        System.out.println("分割线2--------------------------------------------------");

        // 2.基于findFirst()查找元素
        for (int i = 0; i < 100; i++) {
            Optional<Student> optional = studentList.stream()
                    //.parallelStream()
                    .filter(s -> s.getAge() < 20)
                    .findFirst();

            optional.ifPresent(System.out::println);
        }
    }
}
