package juc.thread.pool;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 *
 *
 *
 * @author ljx
 * @version 1.0.0
 * @create 2025/9/30 11:23
 */
public class StaticThreadPool {

    private static final ExecutorService EXECUTOR =
            new ThreadPoolExecutor(
                    Runtime.getRuntime().availableProcessors(),
                    Runtime.getRuntime().availableProcessors() * 2,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1000),
                    new NamedThreadFactory("process-thread-pool-"),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );


    public static class NamedThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public NamedThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, namePrefix + threadNumber.getAndIncrement());
        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(i -> EXECUTOR.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
        }));
        try {
            latch.await(); // 等待所有任务完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("任务执行被中断", e);
        }
        System.out.println("所有任务完成");
        // 关闭线程池
        EXECUTOR.shutdown();
    }
}
