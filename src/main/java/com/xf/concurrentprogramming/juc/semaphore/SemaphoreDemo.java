package com.xf.concurrentprogramming.juc.semaphore;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore:信号量，用来限制能同时访问共享资源的线程上限。
 *
 * 应用场景：
 * 案例一：抢车位
 *      自驾游的朋友一般都会遇到这样的烦恼：去景区游玩，停车比较麻烦。因为停车场中的车位数量是一定的。当车位满了以后，
 *   其他想要进入停车场停车的车辆只能等待。等到其他车辆出来之后，才可以进入。站在并发角度来分析的话：停车场有多个
 *   停车位(多个共享资源)，每个车辆只能停在其中一个位置上(互斥使用的)，停车场的停车位是固定的(并发线程数量的控制)。
 * 案例二：海底捞吃火锅
 *      去海底捞吃火锅的时候，海底捞场地就餐桌数量是固定的，假设有5桌。现在来了8个人，那么其他3个就需要在门口候餐区
 *   等待加号。当有其他桌吃完离开之后，进去一个。
 */
public class SemaphoreDemo {

    /**
     * 餐厅对象
     */
    static class Restaurant{
        // 默认5个餐桌
        public static int TABLE_SIZE = 5;

        /** 客人入座就餐 **/
        public void setTable(int orderNo){
            System.out.println("订单号为"+ orderNo +"的客人，开始就餐中~~~");
        }

        /** 客人就餐完毕，离开餐桌 **/
        public void releaseTable(int orderNo){
            System.out.println(orderNo +"号客人已经就餐完毕，请下一位客人入场~~~");
        }
    }

    /**
     *  junit @test的多线程问题：
     *    守护线程和非守护线程:简单理解，只有3点，①非守护线程不执行结束,程序就不会终止
     *      ②守护线程啥都不做,就是监控非守护线程
     *      ③java进程剩下非守护线程时,进程就会关闭
     *
     *    在 main 中创建的多线程是非守护线程模式,所以只要子线程未执行结束, main线程会处于等待状态 ,这是程序进程也不会结束.
     *    在junit环境中 创建的多线程都变成了守护线程模式，只要Test方法执行结束，守护线程（多线程）也会随着进程（java进程剩下非守护线程时,进程就会关闭）结束而销毁
     */
//    @Test
//    public void semaphoreDemoTest() throws InterruptedException {
//        // 获取餐桌数量
//        int tableSize = Restaurant.TABLE_SIZE;
//        Semaphore semaphore = new Semaphore(tableSize);
//
//        Restaurant restaurant = new Restaurant();
//
//        for (int i = 1; i <= 8; i++) {
//            final int orderNo = i;
//            new Thread(() -> {
//                try {
//                    semaphore.acquire();
//                    restaurant.setTable(orderNo);
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }finally {
//                    restaurant.releaseTable(orderNo);
//                    semaphore.release();
//                }
//
//            }).start();
//        }
//
//        // 让主线程休眠，目的是为了保证创建的多线程（在junit环境中 创建的多线程都变成了守护线程模式）执行完毕
//        // TimeUnit.SECONDS.sleep(4);
//    }

    public static void main(String[] args) {
        // 获取餐桌数量
        int tableSize = Restaurant.TABLE_SIZE;
        Semaphore semaphore = new Semaphore(tableSize);

        Restaurant restaurant = new Restaurant();

        for (int i = 1; i <= 8; i++) {
            final int orderNo = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    restaurant.setTable(orderNo);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    restaurant.releaseTable(orderNo);
                    semaphore.release();
                }

            }).start();
        }
    }
}
