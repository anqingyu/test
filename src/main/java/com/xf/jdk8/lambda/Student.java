package com.xf.jdk8.lambda;

import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    private String sex;
    private int age;
    private boolean isPass;
    private int score;

    public Student(int id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Student(int id, String name, String sex, int age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Student(int id, String name, String sex, int age, boolean isPass) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.isPass = isPass;
    }

    public Student(int id, String name, String sex, int age, boolean isPass, int score) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.isPass = isPass;
        this.score = score;
    }
}

