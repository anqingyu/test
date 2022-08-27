package com.xf.jdk18.stream;

import com.xf.jdk18.lambda.Student;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义收集器，返回所有合格的学员
 *
 *   实现Collector接口自定义收集器：Collector接口需要三个参数。T：流中要收集的元素类型、A：累加器的类型、R：收集的结果类型
 *     需要实现Collector接口中的五个方法：supplier、accumulator、finisher、combiner、characteristics
 *
 *     supplier：用于创建一个容器，在调用它时，需要创建一个空的累加器实例，供后续方法使用。
 *     accumulator：基于supplier中创建的累加容器，进行累加操作。
 *     finisher：当遍历完流后，在其内部完成最终转换，返回一个最终结果。
 *     combiner：用于在并发情况下，将每个线程的容器进行合并。
 *     characteristics：用于定义收集器行为，如是否可以并行或使用哪些优化。其本身是一个枚举，内部有三个值，分别为：
 *        CONCURRENT：表明收集器是并行的且只会存在一个中间容器。
 *        UNORDERED：表明结果不受流中顺序影响，收集是无序的。
 *        IDENTITY_FINISH：表明累积器的结果将会直接作为归约的最终结果，跳过finisher()。
 */
public class MyCollector implements Collector<Student, List<Student>,List<Student>> {
    @Override
    public Supplier<List<Student>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Student>, Student> accumulator() {
        return (studentList, student) -> {
            if (student.isPass()) {
                studentList.add(student);
            }
        };
    }

    @Override
    public BinaryOperator<List<Student>> combiner() {
        return null;
    }

    @Override
    public Function<List<Student>, List<Student>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH,Characteristics.UNORDERED);
    }
}
