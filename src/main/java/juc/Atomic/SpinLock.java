package juc.Atomic;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写自旋锁实现
 *
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/3 下午2:59
 */
public class SpinLock {

    AtomicReference<Thread> spinLock = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in");
        while (!spinLock.compareAndSet(null, thread)) {
        }
        System.out.println(thread.getName() + " 获取锁");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        while (!spinLock.compareAndSet(thread, null)) {
        }
        System.out.println(thread.getName() + " 释放锁");
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        new Thread(() -> {
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + "：处理业务！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unlock();
        }, "A").start();
        new Thread(() -> {
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + "：处理业务！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unlock();
        }, "B").start();
    }
}
