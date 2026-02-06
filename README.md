# JUC多线程及高并发项目

## 项目概述

本项目是一个专注于Java并发编程的学习项目，涵盖了Java.util.concurrent包下的各种多线程和高并发相关技术。项目包含了大量实用的并发编程示例，帮助理解JUC框架的核心概念和最佳实践。

## 技术栈

- Java 8+
- Maven构建工具
- Lombok库（简化代码）

## 项目结构

```
src/main/java/juc/
├── Atomic/                 # 原子类相关示例
│   ├── ABADemo.java
│   ├── AdderAccumulatorDemo.java
│   ├── AtomicRefrenceDemo.java
│   ├── CASDemo.java
│   └── SpinLock.java
├── aqs/                    # AQS框架示例
│   └── AQSdemo.java
├── collection/             # 并发集合示例
│   ├── ContainerNotSafeDemo.java
│   ├── HashSetTest.java
│   └── MapSafe.java
├── conditionThread/        # 线程协作工具类示例
│   ├── ConditionDemo.java
│   ├── CountDownLatchDemo.java
│   ├── CountryEnum.java
│   ├── CyclicBarrierDemo.java
│   └── SemaphoreDemo.java
├── future/                 # 异步计算示例
│   ├── CompletableFutureDemo.java
│   └── FutureDemo.java
├── lock/                   # 锁机制示例
│   ├── ReadWriteLockDemo.java
│   ├── ReentrantLockDemo.java
│   ├── SpinLockDemo.java
│   ├── SyncAndReentrantLockDemo.java
│   └── SynchronizedDemo.java
├── lockSupport/            # 线程阻塞唤醒示例
│   └── WaitAndNotify3.java
├── queue/                  # 阻塞队列示例
│   ├── ProdConsumer_BlockQueueDemo.java
│   └── ProdConsumer_TraditionDemo.java
├── stampedlock/            # 乐观锁示例
│   └── StampedLockDemo.java
├── thread/                 # 线程基础示例
│   ├── pool/               # 线程池示例
│   │   ├── MyThreadPoolDemo.java
│   │   └── StaticThreadPool.java
│   ├── CallableDemo.java
│   ├── DeadLockDemo.java
│   └── StopThread.java
├── volatileTest/           # volatile关键字示例
│   ├── SingletonDemo.java
│   └── VolatileDemo.java
└── test/                   # 测试示例
    └── RuntimeTest.java
```

## 核心特性

### 1. 原子类 (Atomic)
- CAS (Compare And Swap) 操作
- AtomicInteger, AtomicReference 等原子类使用
- ABA问题及其解决方案
- 原子更新引用和时间戳原子引用

### 2. 锁机制 (Lock)
- ReentrantLock 可重入锁
- ReadWriteLock 读写锁
- 公平锁与非公平锁
- 自旋锁实现
- Synchronized 关键字

### 3. 线程协作工具类
- CountDownLatch 倒计时门闩
- CyclicBarrier 循环栅栏
- Semaphore 信号量控制
- Condition 条件变量

### 4. 并发集合
- CopyOnWriteArrayList 写时复制列表
- ConcurrentHashMap 并发哈希表
- ConcurrentLinkedQueue 并发链表队列
- 线程安全集合的选择与应用

### 5. 线程池 (ThreadPool)
- ThreadPoolExecutor 使用
- 线程池参数配置
- 线程池最佳实践

### 6. 异步编程 (Future/CompletableFuture)
- Future异步计算结果
- CompletableFuture异步编排
- 异步编程最佳实践

### 7. volatile关键字
- 可见性保证
- 禁止指令重排
- 单例模式双重检查锁定与volatile

### 8. AQS (AbstractQueuedSynchronizer)
- AQS框架原理
- 自定义同步器实现

## 主要知识点

1. **volatile的理解**：保证可见性、不保证原子性、禁止指令重排
2. **CAS机制**：比较并交换，底层Unsafe类实现
3. **ABA问题**：原子引用与时间戳原子引用解决方案
4. **线程安全集合**：ArrayList、HashMap等非线程安全集合的替代方案
5. **锁的分类**：公平锁/非公平锁、可重入锁、独占锁/共享锁
6. **线程协作**：CountDownLatch、CyclicBarrier、Semaphore的应用场景
7. **线程池**：线程池的工作原理和使用方法

## 使用方法

1. 克隆项目到本地
2. 使用IDE导入Maven项目
3. 直接运行各个包下的示例类
4. 参考代码理解JUC相关知识

## 学习价值

本项目旨在深入理解Java并发编程的核心概念，掌握JUC包下各类工具的使用方法，提高多线程编程能力，为开发高并发应用打下坚实基础。