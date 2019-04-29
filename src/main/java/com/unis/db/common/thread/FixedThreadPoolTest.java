package com.unis.db.common.thread;

import java.util.concurrent.*;

public class FixedThreadPoolTest {
    private ExecutorService executorService = null;
    private LinkedBlockingDeque<Future> linkedBlockingDeque = null;

    /**
     * 初始化线程池，50个线程，用来并行请求
     */
    public void init() {
        executorService = Executors.newFixedThreadPool(50);
        linkedBlockingDeque = new LinkedBlockingDeque<>();
    }

    /**
     * 关闭线程池
     */
    public void destory() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void parallelRequest(FixedThreadPool fixThread, int number) throws InterruptedException {
        for (int i = 0; i < number; i++) {
            ThreadRequest threadRequest = new ThreadRequest(fixThread);
            Future future = executorService.submit(threadRequest);
            linkedBlockingDeque.offer(future);
        }
        Long beginTime = System.currentTimeMillis();
        int successTotal = 0;
        int total=0;
        for (int i = 0; i < number; i++) {
            //没有就阻塞
            Future future = linkedBlockingDeque.take();
            try {
                Boolean flag = (Boolean) future.get();
                if (flag) {
                    successTotal++;
                }
                total++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //结束时间
        Long endTime = System.currentTimeMillis();
        System.out.println("总共用时: " + (endTime - beginTime) / 1000.0 + "s");
        System.out.println("总共执行请求的次数: " + total);
        System.out.println("总共执行成功请求的次数: " + successTotal);
    }

//    public static void main(String[] args) throws InterruptedException {
//        FixedThreadPoolTest thread = new FixedThreadPoolTest();
//        FixedThreadPool fixThread = new FixedThreadPool();
//        thread.init();
//        fixThread.init();
//        thread.parallelRequest(fixThread, 1000000);
//        fixThread.destory();
//        thread.destory();
//    }
}
