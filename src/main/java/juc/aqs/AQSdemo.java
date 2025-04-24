package juc.aqs;


import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/23 下午5:07
 */
public class AQSdemo {

    /* AQS  ->  AbstractQueuedSynchronizer

    一、 lock()
        1、第一个线程抢到锁  ：compareAndSetState(0, 1)
        2、第二个线程抢锁失败：acquire(1)
            -> !tryAcquire(arg):再次抢锁或可重入锁
            -> addWaiter(Node.EXCLUSIVE) 入队
                >> enq(node) 队列为空，创建虚拟头结点 ，尾插
            -> acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                >>  p == head && tryAcquire(arg) 头结点为虚拟节点，继续抢锁
                >> 抢锁成功，设置当前节点为头结点
                >> 失败：shouldParkAfterFailedAcquire(p, node) 设置头结点，node.waitStatus=SIGNAL（-1）
                >> parkAndCheckInterrupt() -- LockSupport.park(this); 阻塞线程

           发生意外 ：cancelAcquire 取消排队

      二、unlock()
      1、sync.release(1)
        >> tryRelease(arg)
            >>  unparkSuccessor(h)
                >> LockSupport.unpark(s.thread)
     */


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("hello");
        } finally {
            lock.unlock();
        }
    }
}
