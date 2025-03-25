package juc.future;


import java.util.concurrent.FutureTask;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/3/3 下午2:45
 */
public class FutureDemo {

    /**
     * get()方法在Future 计算完成之前会一直处在阻塞状态下,
     * isDone()方法容易耗费CPU资源，对于真正的异步处理我们希望是可以通过传入回调函数，
     * 在Future结束时自动调用该回调函数，这样，我们就不用等待结果。
     * <p>
     * 阻塞的方式和异步编程的设计理念相违背，而轮询的方式会耗费无谓的CPU资源。因此，JDK8设计出CompletableFuture.
     * CompletableFuture提供了一种观察者模式类似的机制，可以让任务执行完成后通知监听的一方。
     *
     * @param args
     */

    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask(() -> {
            String result = "hello";
            System.out.println(result);
            return result;
        });
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
            System.out.println(futureTask.isDone());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
