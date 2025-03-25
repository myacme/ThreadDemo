package juc.lockSupport;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程的等待和唤醒的三种实现
 * 1.Object      wait()  notify()
 * 2.Condition   await() signal()
 * 3.LockSupport park()  unpark()
 *
 * @author ljx
 * @version 1.0.0
 * @create 2025/3/25 下午4:08
 */
public class WaitAndNotify3 {

    public static void main(String[] args) throws InterruptedException {
        lockSupportDemo();
    }

    /**
     * 对象锁 wait() notify()
     * ！！必须在同步代码块中执行
     * ！！将notify放在wait方法前面程序无法执行，无法唤醒
     */
    private static void objectDemo() throws InterruptedException {
        String lock = "lock";
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "---come in");
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + "---被唤醒");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "A").start();
        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "---come in");
                lock.notify();
            }
        }, "B").start();
    }

    /**
     * Condition   await() signal()
     * ！！必须在同步代码块中执行
     * ！！将signal放在await方法前面程序无法执行，无法唤醒
     */
    private static void conditionDemo() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "---被唤醒");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "A").start();
        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "---come in");
                condition.signal();
            } finally {
                lock.unlock();
            }
        }, "B").start();
    }

    /**
     * LockSupport park() unpark()
     * *
     * unpark() 发放许可证，只能最多一个
     * *
     * ！！可以在任何代码中执行
     * ！！将unpark放在park方法前面，程序正常唤醒
     */
    private static void lockSupportDemo() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "---被唤醒");
        }, "A");
        t1.start();
        Thread.sleep(1000);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---come in");
            LockSupport.unpark(t1);
        }, "B").start();
    }
}
