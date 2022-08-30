package com.xf.fundation.completablefuture;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceDemo {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        completionService.submit(new CalculateArticleScoreA());
        completionService.submit(new CalculateArticleScoreDemoB());
        completionService.submit(new CalculateArticleScoreDemoC());

        doSomeThingElse();

        Integer result = 0;
        for (int i = 0; i < 3; i++) {
            try {
                result += completionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        executor.shutdown();
    }
    private static void doSomeThingElse() {
        System.out.println("exec other things");
    }
}

class CalculateArticleScoreDemoB implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //业务代码
        Random random = new Random();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName());
        return random.nextInt(100);
    }
}

class CalculateArticleScoreDemoC implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //业务代码
        Random random = new Random();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName());
        return random.nextInt(100);
    }
}
