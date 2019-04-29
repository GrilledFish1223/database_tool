package com.unis.db.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author xuli
 * @date 2019/2/12
 */
@Component
public class ThreadUtils {
    private ExecutorService executorService = null;
    private LinkedBlockingDeque<Future> linkedBlockingDeque = null;
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * 默认线程池中线程的个数：8
     */
    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10);
        linkedBlockingDeque = new LinkedBlockingDeque<>();
    }

    /**
     * 提交task给线程池
     * @param task 任务
     */
    @SuppressWarnings(value={"unchecked"})
    public void submit(Callable task) {
        Future future = executorService.submit(task);
        linkedBlockingDeque.offer(future);
    }

    /**
     * 等待所有任务完成
     * @param threadNum 线程数
     * @return T or F
     */
    public boolean waitTask(int threadNum) {
        int total = 0;
        for (int i = 0; i < threadNum; i++) {
            try {
                //没有就阻塞
                Future future = linkedBlockingDeque.take();
                Boolean flag = (Boolean) future.get();
                if (flag) {
                    total++;
                }
            } catch (ExecutionException | InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (total==threadNum) {
            return true;
        }else{
            logger.error(" * * * * Threads are not fully executed * * * * ");
            return false;
        }
    }
}
