package com.xf.fundation.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * CompletableFuture小结:
 * 1）当一个操作需要依赖与一个或多个比较耗时的操作时，可以通过异步任务改善程序性能，加快响应速度。
 * 2）在功能实现时，根据当前需求，应该尽量的使用异步API。
 * 3）将同步API封装到CompletableFuture，以异步形式执行。
 * 4）结合自身业务确定异步任务何时结束，是全部执行完毕还是只要有一个首先完成就结束。
 * 5）CompletableFuture提供了回调操作，当任务执行完毕可以通过回调触发后续特定任务处理。
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CompletableFuture.runAsync()测试，使用默认线程池
        runAsyncTest1();
        System.out.println("我是分割线1--------------------------CompletableFuture.runAsync()测试");
        // CompletableFuture.runAsync()测试，使用自定义线程池，当任务非常耗时，使用自定义线程池
        runAsyncTest2();
        System.out.println("我是分割线2--------------------------supplyAsync测试，有返回值");
        // supplyAsync测试，有返回值
        supplyAsyncTest();

        System.out.println("我是分割线3--------------------------whenComplete/whenCompleteAsync测试");
        // 2.1 异步计算结果触发回调:whenComplete/whenCompleteAsync测试
        whenCompleteTest();
        System.out.println("我是分割线4--------------------------exceptionallyTest测试");
        // 2.2 异步计算结果触发回调:exceptionallyTest测试
        exceptionallyTest();

        System.out.println("我是分割线5--------------------------thenApply()测试");
        // 3.1 多任务依赖执行:thenApply()使用
        thenApplyTest();
        System.out.println("我是分割线5--------------------------handle()测试");
        // 3.2多任务依赖执行:handle()使用
        handleTest();
        System.out.println("我是分割线6--------------------------thenAccept()测试");
        // 3.3多任务依赖执行:thenAccept()使用
        thenAcceptTest();
        System.out.println("我是分割线7--------------------------thenRun()测试");
        // 3.3多任务依赖执行:thenRun()使用
        thenRunTest();

        System.out.println("我是分割线8--------------------------thenCombine()测试");
        // 4.1 两任务合并执行:thenCombine()使用
        thenCombineTest();
        System.out.println("我是分割线9--------------------------thenAcceptBoth()测试");
        // 4.2 两任务合并执行:thenAcceptBoth()使用
        thenAcceptBothTest();
        System.out.println("我是分割线10--------------------------runAfterBoth()测试");
        // 4.3 两任务合并执行:runAfterBoth()使用
        runAfterBothTest();

        System.out.println("我是分割线11--------------------------applyToEither()测试");
        // 5.1 两个任务任意一个完成触发:applyToEither()使用
        applyToEitherTest();
        System.out.println("我是分割线12--------------------------acceptEither()测试");
        // 5.2 两个任务任意一个完成触发:acceptEither()使用
        acceptEitherTest();
        System.out.println("我是分割线13--------------------------runAfterEither()测试");
        // 5.3 两个任务任意一个完成触发:runAfterEither()使用
        runAfterEitherTest();

        System.out.println("我是分割线13--------------------------allOf()测试");
        // 6.1 多任务组合执行:allOf()使用
        allOfTest();
        System.out.println("我是分割线13--------------------------anyOf()测试");
        // 6.2 多任务组合执行:anyOf()使用
        anyOfTest();
    }

    /**
     * runAsync()测试，未设置Executor
     */
    private static void runAsyncTest1() {
        // runAsync()是没有返回值的。查看源码，当传入Executor会使用指定线程池执行，如果没有传入则使用默认ForkJoinPool.commonPool()执行，值得注意的是，commonPool中都是守护线程，主线程执行完，子线程也就over了。因此建议当任务非常耗时，使用自定义线程池。
        // 未设置Executor
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                // 未设置Executor，根据结运行果可以看到，子线程没有来得及打印，主线程就结束了。
                System.out.println("runAsyncTest1：child run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("main end");
    }

    /**
     * runAsync()测试，置自定义线程池Executor
     */
    private static void runAsyncTest2() {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // runAsync()是没有返回值的。查看源码，当传入Executor会使用指定线程池执行，如果没有传入则使用默认ForkJoinPool.commonPool()执行，值得注意的是，commonPool中都是守护线程，主线程执行完，子线程也就over了。因此建议当任务非常耗时，使用自定义线程池。
        // 自定义线程池Executor
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("runAsyncTest2：child run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executor);

        System.out.println("main end");

        executor.shutdown();
    }

    /**
     * supplyAsync测试，有返回值
     */
    public static void supplyAsyncTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("supplyAsyncTest：child run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executor);

        System.out.println("main end");

        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    /**
     * 异步计算结果触发回调:whenComplete与whenCompleteAsync方法测试
     */
    public static void whenCompleteTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        System.out.println(Thread.currentThread().getName());

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("异步任务线程："+Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
                System.out.println("whenCompleteTest：child run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executor);

        System.out.println("main end");

        try {
            // whenComplete测试：后续的特定处理任务使用的线程与异步任务线程相同
//            future.whenComplete(new BiConsumer<Integer, Throwable>() {
            // whenCompleteAsync测试：后续的处理任务在线程池中会开启了一个新的线程进行使用
            future.whenCompleteAsync(new BiConsumer<Integer, Throwable>() {
                @Override
                public void accept(Integer integer, Throwable throwable) {
                    System.out.println("结果触发任务线程："+Thread.currentThread().getName());

                    System.out.println("特定任务执行");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    /**
     * 异步计算结果触发回调:exceptionally测试
     *      whenComplete与whenCompleteAsync方法可以处理正常的返回结果也可以处理异常，而exceptionally()只对异常进行处理，且其使用的是主线程
     */
    public static void exceptionallyTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        System.out.println(Thread.currentThread().getName());

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("异步任务线程："+Thread.currentThread().getName());
                int i = 1/0;
                TimeUnit.SECONDS.sleep(3);
                System.out.println("exceptionallyTest：child run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executor);

        System.out.println("main end");

        future.exceptionally(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) {
                System.out.println("异常结果 触发任务线程：" + Thread.currentThread().getName());
                System.out.println("异步任务 执行失败：" + throwable.getMessage());
                return null;
            }
        });

        executor.shutdown();
    }

    /**
     * 多任务依赖执行:thenApply()使用
     *      第二个任务的执行需要依赖与第一个任务的执行结果
     */
    public static void thenApplyTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        System.out.println(Thread.currentThread().getName());

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int value = new Random().nextInt(100);
            System.out.println(value);
            return value;
        }, executor).thenApply(value -> {
            int result = value * 10;
            System.out.println(result);
            return result;
        });

        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * 多任务依赖执行:handle()使用
     *    handle()的使用效果与thenApply()类似，但不同的是thenApply()只能处理任务的正常返回结果，一旦出现异常则无法进行后续处理。而handle()即可以处理正常结果，也可以处理异常结果。
     */
    public static void handleTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        System.out.println(Thread.currentThread().getName());

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int value = new Random().nextInt(100);
            int i=1/0;
            System.out.println(value);
            return value;
        }, executor).handle(new BiFunction<Integer, Throwable, Integer>() {
            @Override
            public Integer apply(Integer integer, Throwable throwable) {
                int result=1;
                if (throwable == null){
                    result = integer * 10;
                    System.out.println(result);
                } else {
                    System.out.println(throwable.getMessage());
                }
                return result;
            }
        });

        try {
            Integer result = future.get();
            System.out.println(result);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * 多任务依赖执行:thenAccept()使用
     *    当将多个任务连接起来执行时，有时最终是不需要返回结果。thenAccept()使用与thenApply()、handle()类似，接收任务执行结果，并使用，但其没有结果返回。
     */
    public static void thenAcceptTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);

        System.out.println(Thread.currentThread().getName());

        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int value = new Random().nextInt(100);
            System.out.println(value);
            return value;
        }).thenAcceptAsync(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("接收上一个任务结果为：" + integer);
            }
        },executor);

        executor.shutdown();
    }

    /**
     * 多任务依赖执行:thenRun()使用
     *    thenRun()与thenAccept()使用基本相同，都是不会进行结果返回，但不同的是，thenRun()不关心方法是否有结果，只要它完成，就会触发其执行
     */
    public static void thenRunTest(){
        ExecutorService executor = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int i = new Random().nextInt(100);
            return i;
        }, executor).thenRun(()-> System.out.println("thenRunTest:run方法执行"));

        executor.shutdown();
    }

    /**
     * 两任务合并执行:thenCombine()使用
     *    当两个异步任务都执行完毕后，它可以将两个任务进行合并，获取到两个任务的执行结果，进行合并处理，最后会有返回值。
     */
    public static void thenCombineTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1：" + Thread.currentThread().getName());
            return "hello";
        }, executorService);

        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2：" + Thread.currentThread().getName());
            return "word";
        }, executorService);

        CompletableFuture result = future1.thenCombineAsync(future2, (f1, f2) -> {
            System.out.println("result：" + Thread.currentThread().getName());
            return f1 + " " + f2;
        }, executorService);

        System.out.println(result.get());

        executorService.shutdown();
    }

    /**
     * 两任务合并执行:thenAcceptBoth()使用
     *    thenAcceptBoth()使用与thenCombine()类似，当两个任务执行完，获取两个任务的结果进行特定处理，但thenAcceptBoth()没有返回值
     */
    public static void thenAcceptBothTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int f1 = new Random().nextInt(100);
            System.out.println("f1 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int f2 = new Random().nextInt(100);
            System.out.println("f2 value：" + f2);
            return f2;
        }, executorService);

        future1.thenAcceptBoth(future2, (f1,f2)-> System.out.println(f1+f2));

        executorService.shutdown();

    }

    /**
     * 两任务合并执行:runAfterBoth()使用
     *    当两个任务执行完毕，触发特定任务处理，但不要两个异步任务结果，且不会进行值返回。
     */
    public static void runAfterBothTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int f1 = new Random().nextInt(100);
            System.out.println("f1 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int f2 = new Random().nextInt(100);
            System.out.println("f2 value：" + f2);
            return f2;
        }, executorService);

        future1.runAfterBothAsync(future2, () -> {
            System.out.println("两个任务都已执 行完");
            executorService.shutdown();
        });
    }

    /**
     * 两个任务任意一个完成触发:applyToEither()使用
     *    当两个任务异步任务执行，谁先执行完，就以谁的结果为准，完成后续的业务处理，并且会进行结果值返回。
     */
    public static void applyToEitherTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("future1："+Thread.currentThread().getName());
            return "hello";
        }, executorService);

        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2："+Thread.currentThread().getName());
            return "world";
        }, executorService);

        CompletableFuture result = future1.applyToEitherAsync(future2, value -> {
            System.out.println("result：" + Thread.currentThread().getName());
            return value;
        });

        System.out.println(result.get());
        executorService.shutdown();
    }

    /**
     * 两个任务任意一个完成触发:acceptEither()使用
     *    acceptEither()的使用效果与applyToEither()类似，但acceptEither()没有返回值
     */
    public static void acceptEitherTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("future1："+Thread.currentThread().getName());
            return "hello";
        }, executorService);

        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2："+Thread.currentThread().getName());
            return "world";
        }, executorService);

        CompletableFuture result = future1.acceptEitherAsync(future2, value -> {
            System.out.println("result："+Thread.currentThread().getName());
            System.out.println(value);
            executorService.shutdown();
        });
    }

    /**
     * 两个任务任意一个完成触发:runAfterEither()使用
     *    当两个任务执行，只要有一个任务执行完，则触发特定处理执行，无需使用异步任务的执行结果，且特定处理不会进行值的返回。
     */
    public static void runAfterEitherTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("future1："+Thread.currentThread().getName());
            return "hello";
        }, executorService);

        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2："+Thread.currentThread().getName());
            return "world";
        }, executorService);

        future1.runAfterEitherAsync(future2, () -> System.out.println("其中一个任务处理完成 了"), executorService);
    }

    /**
     * 多任务组合执行:支持任意多个异步任务进行组合操作
     *    allOf()使用：当一个特定业务处理任务的执行需要一组异步任务完成后才能执行的话，就可以通过allOf()实现。
     *       适用场景：假设现在有一个Z任务，它的执行需要[A,B,C,D,E,F]这一组异步任务全部执行完才能触发。
     */
    public static void allOfTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int f1 = new Random().nextInt(100);
            System.out.println("f1 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int f1 = new Random().nextInt(100);
            System.out.println("f2 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int f1 = new Random().nextInt(100);
            System.out.println("f3 value：" + f1);
            return f1;
        }, executorService);

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        list.add(future1);
        list.add(future2);
        list.add(future3);

        CompletableFuture<Void> all = CompletableFuture.allOf(list.toArray(new CompletableFuture[]{}));

        all.thenRunAsync(() -> {
            AtomicReference<Integer> result = new AtomicReference<>(0);
            list.parallelStream().forEach(future -> {
                try {
                    Integer value = future.get();
                    result.updateAndGet(v -> v + value);
                    System.out.println(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    /**
     * 多任务组合执行:支持任意多个异步任务进行组合操作
     *    anyOf()使用：anyOf()与allOf()类似，但不同的是，使用anyOf()时，当一组异步任务中，只要有一个执行完毕，则会被触发，利用该特性可以用来获取最快的那个线程结果。
     *        其内部实现原理与allOf()都是一样的，内部也会基于二分查找构建一个二叉树
     */
    public static void anyOfTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        // 调用supplyAsync方法，传入自定义线程池Executor
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int f1 = new Random().nextInt(100);
            System.out.println("f1 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int f1 = new Random().nextInt(100);
            System.out.println("f2 value：" + f1);
            return f1;
        }, executorService);

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int f1 = new Random().nextInt(100);
            System.out.println("f3 value：" + f1);
            return f1;
        }, executorService);

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        list.add(future1);
        list.add(future2);
        list.add(future3);

        CompletableFuture<Object> future = CompletableFuture.anyOf(list.toArray(new CompletableFuture[]{}));

        future.thenRunAsync(() -> {
            try {
                System.out.println("有一个任 务执行完了，其值为:"+future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
