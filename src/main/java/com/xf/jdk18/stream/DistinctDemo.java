package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于distinct实现数据去重:
 *   在java7之前对集合中的内容去重，有多种的实现方式，如通过set去重、遍历后赋值给另一个集合
 */
public class DistinctDemo {
    public static void main(String[] args) {
        // 1.基本数据去重
        // 对数据模2后结果去重
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);

        // java7 将一个集合的值赋给另一个集合去重
        List<Integer> result1 = demo1(numberList);
        System.out.println(result1);

        // java7 利用set去重
        List<Integer> result2 = demo2(numberList);
        System.out.println(result2);

        // java8 distinct使用去重
        List<Integer> result3 = demo3(numberList);
        System.out.println(result3);

        // 2.对象去重:利用distinct对对象去重，需要重写hashCode()和equals()方法，否则不起作用
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"张 三","M", 19, true));
        studentList.add(new Student(1,"李 四","M",18, false));
        studentList.add(new Student(1,"王 五","F",21, true));
        studentList.add(new Student(1,"赵 六","F",20, false));

        studentList.add(new Student(1,"张 三","M", 19, true));
        studentList.add(new Student(1,"李 四","M",18, false));
        studentList.add(new Student(1,"王 五","F",21, true));
        studentList.add(new Student(1,"赵 六","F",20, false));

        List<Student> collect = studentList.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * java7 将一个集合的值赋给另一个集合
     * @param numberList
     * @return
     */
    public static List<Integer> demo1(List<Integer> numberList){
        List<Integer> resultList = new ArrayList<>();
        for (Integer number : numberList) {
            if (number % 2 ==0){
                resultList.add(number);
            }
        }

        List<Integer> newList = new ArrayList<>();
        for (Integer integer : resultList) {
            if (!newList.contains(integer)){
                newList.add(integer);
            }
        }
        return newList;
    }


    /**
     * java7 利用set去重
     * @param numberList
     * @return
     */
    public static List<Integer> demo2(List<Integer> numberList){
        List<Integer> newList = new ArrayList();

        Set set = new HashSet();
        for (Integer integer : numberList) {
            if(integer % 2 == 0){
                if (set.add(integer)) {
                    newList.add(integer);
                }
            }
        }
        return newList;
    }

    /**
     * java8 distinct使用
     * @param numberList
     * @return
     */
    public static List<Integer> demo3(List<Integer> numberList){
        List<Integer> result = numberList.stream().filter(n -> n % 2 == 0)
                .distinct()
                .collect(Collectors.toList());
        return result;
    }
}
