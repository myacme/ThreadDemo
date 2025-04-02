package juc.thread;


/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/2 上午10:12
 */
public class StopThread {

    /**
     * 线程会继续执行直到任务完成
     * 在运行中的线程如果调用了sleep、wait等方法，会抛出InterruptedException
     * 当线程处于阻塞状态时（如sleep），调用interrupt()会抛出异常，此时线程的中断状态会被清除，
     * 所以需要在catch块中重新中断，或者妥善处理异常，避免忽略中断请求。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程被中断了");
                    break;
                }
                System.out.println("线程正在运行");
            }
        });
        thread.start();
        Thread.sleep(300);
        // 中断线程标志，不会立即停止线程，由处理器决定
        thread.interrupt();
    }
}
