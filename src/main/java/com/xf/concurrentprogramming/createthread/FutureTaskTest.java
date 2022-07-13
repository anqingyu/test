package com.xf.concurrentprogramming.createthread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建任务对象
        FutureTask<Integer> futureTask = new FutureTask(() -> {
            System.out.println("FutureTask test……");
            return 100;
        });

        // 参数1 是任务对象; 参数2 是线程名字，推荐
        new Thread(futureTask, "t3").start();

        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer result = futureTask.get();
        System.out.println("结果是:" + result);
    }

}
