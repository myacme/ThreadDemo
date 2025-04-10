package juc.Atomic;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author MyAcme
 */
public class AtomicRefrenceDemo {
    public static void main(String[] args) {
        User z3 = new User("张三", 22);
        User l4 = new User("李四", 23);
        AtomicReference<User> atomicReference = new AtomicReference<>(z3);
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
        //属性原子操作
        z3.addAge();
        z3.updateName();
        System.out.println(z3);
    }
}

@Getter
@ToString
class User {
    public volatile String userName;
    public volatile int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }


    // 属性原子操作  属性加 volatile
    static final AtomicIntegerFieldUpdater<User> UPDATER = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
    static final AtomicReferenceFieldUpdater<User, String> UPDATER2 = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "userName");

    public void addAge() {
        UPDATER.getAndIncrement(this);
        UPDATER.compareAndSet(this, 23, 99);
    }
    public void updateName() {
        UPDATER2.compareAndSet(this, "张三", "张三他爹");
    }
}