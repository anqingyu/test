package com.xf.jdk8.stream;

import com.xf.jdk8.lambda.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 收集器：collect() 接收一个类型为 Collector 的参数，这个参数决定了如何把流中的元素聚合到其它数据结构中。
 *    Collectors 类包含了大量常用收集器的工厂方法，比如：toList() 和 toSet()，借助Collectors就可以完成复杂的数据转换
 */
public class CollectDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "张 三", "M", 19, true));
        studentList.add(new Student(2, "李 四", "M", 18, false));
        studentList.add(new Student(3, "王 五", "F", 21, true));
        studentList.add(new Student(4, "赵 六", "F", 20, false));
        studentList.add(new Student(5, "孙 七", "F", 20, false));

        // javau7 按性别分组
        Map<String, List<Student>> map = new HashMap<>();
        for (Student student : studentList) {
            String sex = student.getSex();
            if (map.get(sex) == null) {
                List<Student> list = new ArrayList<>();
                list.add(student);
                map.put(sex, list);
            } else {
                List<Student> list = map.get(sex);
                list.add(student);
            }
        }
        System.out.println(map);

        // java8 按年龄分组
        Map<Integer, List<Student>> map2 = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(map2);

        // 1.Collectors常用方法使用
        // 1.1通过counting()统计集合总数
//        Long collect = studentList.stream().collect(Collectors.counting());
        Long collect = studentList.stream().count();
        System.out.println(collect);

        // 1.2 通过maxBy()与minBy()获取最大值最小值
        //获取年龄最大的学生
//        Optional<Student> optional = studentList.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
        Optional<Student> optional = studentList.stream().max(Comparator.comparing(Student::getAge));
        optional.ifPresent(System.out::println);
        // 获取年龄最小的学生
        Optional<Student> optional2 = studentList.stream().min(Comparator.comparing(Student::getAge));
        optional2.ifPresent(System.out::println);

        // 1.3 通过summingInt()进行数据汇总
        // 对所有学生年龄的和
        Integer collect1 = studentList.stream().collect(Collectors.summingInt(Student::getAge));
        // 简化写法
        int ageSum = studentList.stream().mapToInt(Student::getAge ).sum();
        System.out.println("collect1=" + collect1 + ", ageSum=" + ageSum);

        // 1.4 通过averagingInt()进行平均值获取
        // 求学生的平均年龄
        Double collect2 = studentList.stream().collect(Collectors.averagingInt(Student::getAge));
        // 推荐写法
        OptionalDouble averageAge = studentList.stream().mapToDouble(Student::getAge).average();
        if (averageAge.isPresent()) {
            double asDouble = averageAge.getAsDouble();
            System.out.println(asDouble);
        }

        // 1.5 复杂结果返回
        // IntSummaryStatistics类，其内部提供了相关getter方法用于获取汇总值、总和、最大值最小值等方法，直接调用即可
        IntSummaryStatistics collect3 = studentList.stream().collect(Collectors.summarizingInt(Student::getAge));
        long count = collect3.getCount();
        long sum = collect3.getSum();
        int max = collect3.getMax();
        int min = collect3.getMin();
        double average = collect3.getAverage();

        // 1.6 通过joining()进行数据拼接
        String collect4 = studentList.stream().map(Student::getName).collect(Collectors.joining());
        String collect5 = studentList.stream().map(Student::getName).collect(Collectors.joining(","));
        System.out.println(collect4);
        System.out.println(collect5);

        // 2.分组
        // 2.1 多级分组使用
        Map<Integer, Map<String, List<Student>>> collect6 = studentList.stream().collect(Collectors.groupingBy(Student::getAge,
                Collectors.groupingBy(student -> {
                    if (student.isPass()) {
                        return "pass";

                    } else {
                        return "not pass";

                    }
                })));
        System.out.println(collect6);

        // 2.2 多级分组变形
        // 2.2.1 根据年龄进行分组，获取并汇总人数
        Map<Integer, Long> collect7 = studentList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));

        // 2.2.2 根据年龄与是否及格进行分组，并汇总人数
        Map<Integer, Map<Boolean, Long>> collect8 = studentList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.groupingBy(Student::isPass, Collectors.counting())));

        // 2.2.3 根据年龄与是否及格进行分组，并每组中分数最高的学生
        Map<Integer, Map<Boolean, Student>> collect9 = studentList.stream().collect( Collectors.groupingBy(Student::getAge, Collectors.groupingBy(Student::isPass, Collectors.collectingAndThen( Collectors.maxBy( Comparator.comparing(Student::getScore)), Optional::get))));
    }
}
