package com.unis.db.common.thread;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
/**
 * @author xuli
 * @date 2019/2/12
 */
@Component
public class FixedThreadPool {
    private ExecutorService executorService = null;
    private LinkedBlockingDeque<Future> linkedBlockingDeque = null;

    /**
     * 初始化线程池01，30个线程，用来并行执行sql
     */
    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10);
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

    /**
     * 发送一个请求，如果sql全部成功则返回true,否则返回false
     * 调用线程池来完成请求
     * @return
     * @throws InterruptedException
     */
    public Boolean request() throws InterruptedException {
        String[] strArray = QueryRecordID.randomRecordID();
        for (String str : strArray) {
            ThreadSql thread = new ThreadSql(str);
            Future future = executorService.submit(thread);
            linkedBlockingDeque.offer(future);
        }
        int total = 0;
        for (int i = 0; i < strArray.length; i++) {
            //没有就阻塞
            Future future = linkedBlockingDeque.take();
            try {
                Thread.sleep(2);
                Boolean flag = (Boolean) future.get();
                if (flag) {
                    total++;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return (total == strArray.length);
    }
}
