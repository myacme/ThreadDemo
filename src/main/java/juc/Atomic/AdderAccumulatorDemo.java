package juc.Atomic;


import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/4/9 上午10:51
 */
public class AdderAccumulatorDemo {
    /*
    LongAdder只能用来计算加法，且从零开始计算
    LongAccumulator提供了自定义的函数操作
    如果是JDK8，推荐使用 LongAdder 对象,比 AtomicLong 性能更好(减少乐观锁的重试次数）
    内部有一个base变量，一个Cell数组。
            base变量:低并发，直接累加到该变量上
            Cell数组:高并发，累加进各个线程自己的槽Cell中
     */
    public static void main(String[] args) {
        longAccumulator();
    }

    public static void longAdder() {
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 100; i++) {
            longAdder.increment();
        }
        System.out.println(longAdder.sum());
    }

    public static void longAccumulator() {
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.get());
    }
}
