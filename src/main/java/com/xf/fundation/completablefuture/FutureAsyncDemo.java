package com.xf.fundation.completablefuture;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Future的使用:通常需要配合ExecutorService和Callable一起使用
 */
public class FutureAsyncDemo {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.out.println(getArticleScore("demo"));
    }

    // 接收文章名称，模拟并发调用其他服务（如关注服务、点赞服务等）计算文章分数
    public static int getArticleScore(String name){
        Future<Integer> futureA = executor.submit(new CalculateArticleScoreA());
        Future<Integer> futureB = executor.submit(new CalculateArticleScoreA());
        Future<Integer> futureC = executor.submit(new CalculateArticleScoreA());

        doSomeThingElse();

        Integer a = null;
        try {
            a = futureA.get();
        } catch (InterruptedException e) {
            futureA.cancel(true);
            e.printStackTrace();
        } catch (ExecutionException e) {
            futureA.cancel(true);
            e.printStackTrace();
        }

        Integer b = null;
        try {
            b = futureB.get();
        } catch (InterruptedException e) {
            futureB.cancel(true);
            e.printStackTrace();
        } catch (ExecutionException e) {
            futureB.cancel(true);
            e.printStackTrace();
        }

        Integer c = null;
        try {
            c = futureC.get();
        } catch (InterruptedException e) {
            futureC.cancel(true);
            e.printStackTrace();
        } catch (ExecutionException e) {
            futureC.cancel(true);
            e.printStackTrace();
        }

        executor.shutdown();
        return a + b + c;
    }

    private static void doSomeThingElse() {
        System.out.println("exec other things");
    }
}

class CalculateArticleScoreA implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        //业务代码
        Random random = new Random();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName());
        return random.nextInt(100);
    }
}
