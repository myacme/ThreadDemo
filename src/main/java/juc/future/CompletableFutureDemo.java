package juc.future;


import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/3/4 下午2:47
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> "hello"
        ).whenComplete((v, e) -> {
            System.out.println("v=" + v);
            System.out.println("e=" + e);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
        merge();
    }

    /**
     * thenRun(Runnable runnable)   任务A执行完执行B，并且B不需要A的结果
     * thenAccept(Consumer action)  任务A执行完执行B，B需要A的结果，但是任务B无返回值
     * thenApply(Function fn)       任务A执行完执行B，B需要A的结果，同时任务B有返回值
     * handle(BiFunction fn)        任务A执行完执行B，B需要A的结果，同时任务B有返回值 ，并且可以获取到异常信息
     */
    public static void api() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "hello");
        cf.thenApply(s -> s + " world")
                .handle((s, e) -> s + "!")
                .thenRun(() -> System.out.println("Done"))
                .thenAccept(System.out::println);
        System.out.println(cf.get());
    }

    /**
     * 1没有传入自定义线程池，都用默认线程池ForkJoinPool;
     * 2 传入了一个自定义线程池，
     * 如果你执行第一个任务的时候，传入了一个自定义线程池:
     * 调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
     * 调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是Forkjoin线程池
     * 备注
     * 有可能处理太快，系统优化切换原则，直接使用main线程处理
     * 其它如:thenAccept和thenAcceptAsync，thenApply和thenApplyAsync等，它们之间的区别也是同理
     * <p>
     * 尚硅谷 26集
     */
    public static void async() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenRun(() -> System.out.println(Thread.currentThread().getName()))
                .thenRunAsync(() -> System.out.println(Thread.currentThread().getName()))
                .get();
    }

    /**
     * 并行执行每个元素的操作
     *
     * @param list
     * @return
     */
    public static List<String> getPriceBycompletableFuture(List<Map<String, String>> list) {
        return list.stream()
                .map(map ->
                        CompletableFuture.supplyAsync(() ->
                                map.get("key")))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /**
     * 哪个先执行完，就执行哪个
     */
    public static void first() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf1";
        });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf2";
        });
        cf1.acceptEither(cf2, f -> System.out.println("the first：" + f)).join();
    }

    /**
     * 合并
     */
    public static void merge() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf1";
        });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf2";
        });
        cf1.thenCombine(cf2, (f1, f2) -> f1 + f2).thenAccept(System.out::println).join();
    }
}
