package com.xf.jdk18.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *  Lambda基础格式:
 *      (参数列表) -> {
 *          方法体
 *      }
 *
 *  参数列表：即匿名方法的形参
 *  -> ：Lambda运算符
 *  方法体：用于执行业务逻辑。可以是单一语句，也可以是语句块。如果是单一语句，可以省略花括号。当需要返回值，如果方法体中只有
 *     一条语句，可以省略return，会自动根据结果进行返回。
 *
 *  用途：1.使用lambda简化匿名内部类
 */
public class LambdaDemo {
    public static void main(String[] args) {
        // 匿名内部类
        StudentService studentService = new StudentService() {
            @Override
            public Student getStudentInfo(List<Student> studentList, Student student) {
                for (Student s : studentList) {
                    if (s.getName().equals(student.getName())) {
                        return s;
                    }
                }
                return null;
            }
        };


        // 1.使用lambda简化匿名内部类
        StudentService studentService2 = (studentList2, student2) -> {
            for(Student s: studentList2){
                if (s.getName().equals(student2.getName())){
                    return s;
                }
            }
            return null;
        };

        // 构造测试数据
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"张 三","M"));
        students.add(new Student(2,"李 四","M"));
        students.add(new Student(3,"王 五","M"));
        // 函数式接口Comparator使用测试
        students.sort(Comparator.comparing(Student::getId));

        Student student = new Student(4, "王 五", "M");
        // 测试匿名内部类
        Student studentInfo = studentService.getStudentInfo(students, student);
        System.out.println(studentInfo);
        System.out.println("---------我是分割线----------");
        // 测试使用lambda简化后的匿名内部类
        Student studentInfo2 = studentService2.getStudentInfo(students, student);
        System.out.println(studentInfo2);

        // lambda简化匿名内部类
        int port = 8090;
        // 在Lambda方法体内使用外部变量时，其必须声明为final。下方代码虽然没有显示的声明，但是在Java8它自动的会对需要为final的变量进行转换。注意port遍历的颜色变成紫色了
        Thread thread = new Thread(() -> System.out.println(port));
        thread.start();

        // 2.lambda遍历集合
        String[] language = {"c", "c++", "c#", "java","python", "go","hive", "php"};
        List<String> languageList = Arrays.asList(language);
        // 旧的循环方式
        for (String s : languageList) {
            System.out.println(s+",");
        }

        // lambda循环
        languageList.forEach(s -> System.out.println(s + ","));

        // 3.lambda数组循环比较
        // 旧的循环比较方式
        Arrays.sort(language,new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return (o1.compareTo(o2));
            }
        });

        // lambda循环比较
        Arrays.sort(language, (o1, o2) -> (o1.compareTo(o2)));
        languageList.forEach(s -> System.out.println(s + ","));
    }
}
