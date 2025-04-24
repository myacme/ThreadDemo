package juc.stampedlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock不支持重入，没有Re开头
 * StampedLock的悲观读锁和写锁都不支持条件变量(Condition)，这个也需要注意使用
 * StampedLock一定不要调用中断操作，即不要调用interrupt()方法
 *
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/24 上午10:54
 */
public class StampedLockDemo {


    private static int num = 0;

    private static final StampedLock stampedLock = new StampedLock();


    /**
     * 写锁 与
     * ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
     * readWriteLock.writeLock().lock();
     * 一样
     *
     * @throws InterruptedException
     */
    public void write() throws InterruptedException {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入：" + num);
            TimeUnit.MILLISECONDS.sleep(300);
            num += 10;
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    /**
     * 读锁 与
     * ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
     * readWriteLock.readLock().lock();
     * 一样
     *
     * @throws InterruptedException
     */
    public void read() throws InterruptedException {
        long stamp = stampedLock.readLock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取：" + num);
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + "\t读取完成");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }


    public void tryOptimisticRead() throws InterruptedException {
        // 乐观读
        long stamp = stampedLock.tryOptimisticRead();
        System.out.println(Thread.currentThread().getName() + "\t optimisticRead 是否读到值：" + num);
        TimeUnit.MILLISECONDS.sleep(500);
        // 如果读锁没有被写锁修改过，则返回true
        if (!stampedLock.validate(stamp)) {
            System.out.println(Thread.currentThread().getName() + "\t optimisticRead 失败，重读");
            try {
                stamp = stampedLock.readLock();
                System.out.println(Thread.currentThread().getName() + "\t optimisticRead 重读成功：" + num);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        new Thread(() -> {
            try {
                stampedLockDemo.write();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                stampedLockDemo.read();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();
        new Thread(() -> {
            try {
                stampedLockDemo.tryOptimisticRead();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t3").start();
    }
}
