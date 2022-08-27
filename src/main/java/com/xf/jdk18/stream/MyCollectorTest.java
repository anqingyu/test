package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;

import java.util.ArrayList;
import java.util.List;

public class MyCollectorTest {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "张 三", "M", 19, true));
        studentList.add(new Student(2, "李 四", "M", 18, true));
        studentList.add(new Student(3, "王 五", "F", 21, true));
        studentList.add(new Student(4, "赵 六", "F", 20, false));

        List<Student> list = studentList.stream().collect(new MyCollector());
        System.out.println(list);
    }
}
