package juc.conditionThread;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
//        general();
//        countDownLatchTest();
        simultaneousExecution();
    }


    /**
     * 同时执行
     */
    public static void simultaneousExecution() throws InterruptedException {
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        new Thread(() -> {
            try {
                countDownLatch1.wait();
                System.out.println(System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                countDownLatch1.wait();
                System.out.println(Instant.now().toEpochMilli());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
        Thread.sleep(1000);
        countDownLatch1.countDown();
    }

    public static void general() {
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t上完自习，离开教室");
            }, "Thread-->" + i).start();
        }
        while (Thread.activeCount() > 2) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t=====班长最后关门走人");
    }

    public static void countDownLatchTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t=====秦统一");
    }
}
