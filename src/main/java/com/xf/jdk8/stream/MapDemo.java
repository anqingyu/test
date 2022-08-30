package com.xf.jdk8.stream;

import com.xf.jdk8.lambda.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 映射:从某些对象中选择性的提取某些元素的值，就像编写sql一样，指定获取表中特定的数据列.Stream API中对应的方法是map()，它接收一个
 *   函数作为方法参数，这个函数会被应用到集合中每一个元素上，并最终将其映射为一个新的元素。
 */
public class MapDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"张 三","M",19));
        studentList.add(new Student(1,"李 四","M",18));
        studentList.add(new Student(1,"王 五","F",21));
        studentList.add(new Student(1,"赵 六","F",20));

        // 获取每一个学生的名字，并形成一个新的集合
        List<String> nameList = studentList.stream().map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(nameList);

    }
}
