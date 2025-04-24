package juc.conditionThread;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/24 下午3:03
 */
public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {
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
}
