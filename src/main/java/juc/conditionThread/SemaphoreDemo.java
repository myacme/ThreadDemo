package juc.conditionThread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        executeSequentially();
        alternateExecution();
    }

    /**
     * 交替执行
     *
     * @throws InterruptedException
     */
    public static void alternateExecution() throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        Semaphore s3 = new Semaphore(1);
        s2.acquire();
        s3.acquire();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s1.acquire();
                    System.out.println("A");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    s2.release();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s2.acquire();
                    System.out.println("B");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    s3.release();
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s3.acquire();
                    System.out.println("C");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    s1.release();
                }
            }
        }, "C").start();
    }

    /**
     * 依次执行
     */
    public static void executeSequentially() throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        Semaphore s3 = new Semaphore(1);
        s2.acquire();
        s3.acquire();
        new Thread(() -> {
                try {
                    s1.acquire();
                    for (int i = 0; i < 10; i++) {
                        System.out.println("A");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    s2.release();
                }
        }, "A").start();
        new Thread(() -> {
                try {
                    s2.acquire();
                    for (int i = 0; i < 10; i++) {
                        System.out.println("B");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    s3.release();
                }
        }, "B").start();
        new Thread(() -> {
            try {
                s3.acquire();
                for (int i = 0; i < 10; i++) {
                    System.out.println("C");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s1.release();
            }
        }, "C").start();
    }

    public static void test() {
        Semaphore semaphore = new Semaphore(3);//模拟三个停车位
        for (int i = 1; i <= 6; i++) {//模拟6部汽车
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t抢到车位");
                    try {
                        TimeUnit.SECONDS.sleep(3);//停车3s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t停车3s后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, "Car " + i).start();
        }
    }
}
