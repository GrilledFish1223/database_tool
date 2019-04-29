package com.unis.db.common.thread;

import java.util.concurrent.Callable;
/**
 * @author xuli
 * @date 2019/3/12
 */
public class ThreadRequest implements Callable {
    private FixedThreadPool pool = null;

    public ThreadRequest(FixedThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("当前线程的名字:" + Thread.currentThread().getName());
        return pool.request();
    }
}
