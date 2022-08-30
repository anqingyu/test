package com.xf.jdk8.stream;

import com.xf.jdk8.lambda.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基于filter()实现数据过滤：该方法会接收一个返回boolean的函数作为参数，最终返回一个包括所有符合条件元素的流。
 */
public class FilterDemo {
    public static void main(String[] args) {
        // 建立测试数据
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"张 三","M", 19, true));
        studentList.add(new Student(1,"李 四","M",18, false));
        studentList.add(new Student(1,"王 五","F",21, true));
        studentList.add(new Student(1,"赵 六","F",20, false));

        // 1.获取所有年龄20岁以下的学生
        //java7写法
        List<Student> resultList = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getAge() < 20){
                resultList.add(student);
            }
        }

        // java8Stream写法
        Stream<Student> studentStream = studentList.stream().filter(s -> s.getAge() < 20);
        List<Student> list = studentStream.collect(Collectors.toList());
        System.out.println(list);

        // 2.获取所有及格学生的信息
        // java7写法
        List<Student> resultList2 = new ArrayList<>();
        for (Student student : studentList) {
            if (student.isPass()){
                resultList2.add(student);
            }
        }

        // java8Stream写法
        Stream<Student> studentStream2 = studentList.stream().filter(Student::isPass);
        List<Student> list2 = studentStream2.collect(Collectors.toList());
        System.out.println(list2);
    }
}
