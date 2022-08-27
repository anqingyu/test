package com.xf.jdk18.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 归约：对元素进行统计计算，如求和、求最大值、最小值等
 *      1.求和：reduce
 *      2.求最大值：max
 *      3.求最小值：min
 */
public class ReduceDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);

        // java7 进行累积求和
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        System.out.println(sum);

        // 1.1 基于reduce进行累积求和
        // reduce第一个参数表示初始值，第二个参数表示当前操作
        Integer reduce = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(reduce);
        // 简写
        Integer reduce2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(reduce2);

        // 推荐写法
        Optional<Integer> optional = numbers.stream().reduce(Integer::sum);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("数据有误");
        }


        // 2.获取流中元素的最大值
        Optional<Integer> reduce1 = numbers.stream().reduce(Integer::max);
        reduce1.ifPresent(System.out::println);

        // 推荐：使用Stream API提供的求最大值方法max()简化
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        max.ifPresent(System.out::println);

        // 3.获取流中元素的最小值
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        min.ifPresent(System.out::println);


    }
}
