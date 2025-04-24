package juc.thread;

import java.util.concurrent.*;

/**
 * 第四种获得java多线程的方式--线程池
 * <p>
 * <p>
 * <p>
 *
 * 合理分配核心线程数：
 *
 *  cpu密集型：CPU密集的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行。CPU密集任务只有在真正的多核CPU上才可能得到加速(通过多线程),
 *          Cpu密集型任务配置尽可能少的线程数量：-般公式：cpu核数+1个线程的线程池
 *  io密集型：
 *      1：由于IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程，如CPU核数*2
 *      2：IO密集型，即该任务需要大量的IO，即大量的阻塞。
 *          在单线程上运行IO密集型的任务会导致浪费大量的cpu运算能力浪费在等待.
 *          所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核cpu上，这种加速主要就是利用了被浪费掉的阻寨时间.
 *          IO密集型时，大部分线程都阻塞，故需要多配置线程数：
 *          参考公式：cpu核数/1-阻塞系数        阻塞系数在0.8~0.9之间
 *          比如8核cpu：8/1-0.9=80个线程数
 *
 *
 * 	new ThreadPoolExecutor.AbortPolicy();
 * 	new ThreadPoolExecutor.CallerRunsPolicy();
 * 	new ThreadPoolExecutor.DiscardOldestPolicy();
 * 	new ThreadPoolExecutor.DiscardPolicy();
 *
 * 	AbortPolicy(默认):直接抛出 RejectedExecutionException异常阻止系统正常运行。
 *  CallerRunsPolicy:"调用者运行"一秧调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量。
 *  DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
 *  DiscardPolicy:直接丢弃任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最好的一种方案。
 *
 */
public class MyThreadPoolDemo {

    public static final int CPU = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) {
		ExecutorService t = Executors.newFixedThreadPool(2);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		ExecutorService executorService1 = Executors.newCachedThreadPool();
		ExecutorService threadPool = new ThreadPoolExecutor(3, 5, 1L,
				TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(3),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.DiscardPolicy());

		try {
			for (int i = 1; i <= 10; i++) {
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "\t办理业务");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}
}
