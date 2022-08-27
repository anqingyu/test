package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   stream不是一种数据结构，它只是某种数据源的一个视图，数据源可以是一个数组，Java容器或I/O channel等在Stream中的操作每一次都会产生新的流，
 * 内部不会像普通集合操作一样立刻获取值，而是惰性取值，只有等到用户真正需要结果的时候才会执行。
 *
 *  Stream流操作分为两大类:中间操作和终端操作
 *    中间操作：会返回一个流，通过这种方式可以将多个中间操作连接起来，形成一个调用链，从而转换为另外一个流。除非调用链最后存在一个终端操作，否则中间操作对
 *            流不会进行任何结果处理.
 *    终端操作：会返回一个具体的结果，如boolean、list、integer等。
 *
 *  流操作详解：
 *    筛选:Stream中对于符合条件的数据筛选数的两个常见API：filter(过 滤)、distinct(去重)
 *    切片:基于limit()实现数据截取、基于skip()实现数据跳过
 *    映射:从某些对象中选择性的提取某些元素的值，就像编写sql一样，指定获取表中特定的数据列.Stream API中对应的方法是map()，它接收一个
 *      函数作为方法参数，这个函数会被应用到集合中每一个元素上，并最终将其映射为一个新的元素。
 *    匹配：判断集合中某些元素是否匹配对应的条件，如果有的话，再进行后续的操作。
 *       基于anyMatch()判断条件至少匹配一个元素、基于allMatch()判断条件是否匹配所有元素
 *    查找:从集合中查找中符合条件的元素，Stream中也提供了相关的API，findAny()和findFirst()，他俩可以与其他流操作组合使用。
 *       findAny用于获取流中随机的某一个元素，findFirst用于获取流中的第一个元素。
 *    归约：对元素进行统计计算，如求和、求最大值、最小值等
 */
public class MyStreamDemo1 {
    public static void main(String[] args) {
        // java8 查询年龄小于20岁的学生,并且根据 年龄进行排序，得到学生姓名，生成新集合
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"张 三","M",19));
        studentList.add(new Student(1,"李 四","M",18));
        studentList.add(new Student(1,"王 五","F",21));
        studentList.add(new Student(1,"赵 六","F",20));

        // 过滤出年龄小于20岁的学生
        List<String> result = studentList.stream().filter(s -> s.getAge() < 20)  // 过滤出年龄小于20岁的学生
                .sorted(Comparator.comparing(Student::getAge ))  //对结果进行排序
                .map(Student::getName) // 提取出 结合中的name属性
                .collect(Collectors.toList()); // 转换成一个新的集合

        System.out.println(result);
    }
}
