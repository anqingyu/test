package com.xf.jdk18.functionalinterface;

import java.util.function.Supplier;

/**
 * 在Java8的类库设计中，几个常用的函数式接口如下，它们都位于位于java.util.function包下：
 *   Predicate: 用于进行判断操作，内部定义一个抽象方法test，三个默认方法and、negate、or，两个静态方法isEqual、not
 *   Consumer: 位于java.util.function包下用于进行获取数据的操作，其内部定义了一个抽象方法accept、一个默认方法andThen。
 *      对于accept()方法来说，它接受一个泛型T对象。如果现在需要访问类型T对象，并对其进行某些操作的话，就可以使用这个接口。
 *   Function: Function主要用于进行类型转换的操作。内部提供一个抽象方法apply、两个默认方法compose，andThen、一个静态方法identity
 *      对于apply方法，它接收一个泛型T对象，并返回一个泛型R的对象。
 *   Supplier: Supplier也是用来进行值获取操作，内部只有一个抽象方法get
 */
public class MySupplierDemo {
    public static Integer getMin(Supplier<Integer> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        // 创建数组
        int[] arr = {100,20,50,30,99,101,-50};

        Integer result = getMin(() -> {
            int min = arr[0];
            for (int i : arr) {
                if (i<min){
                    min =i;
                }
            }
            return min;
        });

        System.out.println(result);
    }
}
