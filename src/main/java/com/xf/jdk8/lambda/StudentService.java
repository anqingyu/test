package com.xf.jdk8.lambda;

import java.util.List;

public interface StudentService {
    public abstract Student getStudentInfo(List<Student> studentList, Student student);
}
