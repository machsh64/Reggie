package com.ren.reggie.common;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-02 14:55
 * @description: 基于ThreadLocal封装的工具类，用于保存或获取用户的id
 **/
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = null;

    public static ReentrantLock lock = new ReentrantLock();

    public static void setCurrentId(Long id) {
        lock.lock();
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>();
        }
        threadLocal.set(id);
        lock.unlock();
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeThread() {
        if (threadLocal != null) {
            threadLocal.remove();
        }
    }
}
