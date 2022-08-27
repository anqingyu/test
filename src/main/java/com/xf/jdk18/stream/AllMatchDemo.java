package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * 匹配：基于allMatch()判断条件是否匹配所有元素
 *      allMatch()的工作原理与anyMatch()类似，但是anyMatch执行时，只要流中有一个元素符合条件就会返回true，而allMatch会判断流中是否所有条件都符合条件，全部符合才会返回true。
 */
public class AllMatchDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"张 三","M",19));
        studentList.add(new Student(1,"李 四","M",18));
        studentList.add(new Student(1,"王 五","F",21));
        studentList.add(new Student(1,"赵 六","F",20));

        // 当流中只要有一个符合条件的元素，则会立刻中止后续的操作，立即返回一个布尔值，无需遍历整个流。
        if(studentList.stream().allMatch(s -> s.getAge() < 20)){
            System.out.println("集合中有符合条件的学生");
        }

    }
}
