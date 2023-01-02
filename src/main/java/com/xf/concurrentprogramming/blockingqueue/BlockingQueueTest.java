package com.xf.concurrentprogramming.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  Java.util.concurrent.BlockingQueue是一个队列，在进行检索或移除一个元素的时候，它会等待队列变为非空；当在添加一个元素时，它会等待队列中的可用空间。
 *
 *      Java提供了 BlockingQueue的实现，比如ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue,、SynchronousQueue等。
 *  在 Queue 中 poll()和 remove()有什么区别？
 *      相同点：都是返回第一个元素，并在队列中删除返回的对象。
 *      不同点：如果没有元素 poll()会返回 null，而 remove()会直接抛出 NoSuchElementException 异常。
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        Queue<String> queue= new LinkedList<String>();
        queue.offer("string");//add

        // 如果没有元素 poll()会返回 null，而 remove()会直接抛出 NoSuchElementException 异常
        System.out.println(queue.poll());
        System.out.println(queue.remove());
        System.out.println(queue.size());
    }
}
