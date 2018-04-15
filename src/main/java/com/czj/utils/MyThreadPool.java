package com.czj.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    /**
     * 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
     * 若大于corePoolSize，则会将任务加入队列，
     * 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
     * 若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式。
     *
     * API:
     * ThreadPoolExecutor(int corePoolSize,
     *                  int maximumPoolSize,
     *                  long keepAliveTime,
     *                  TimeUnit unit,
     *                  BlockingQueue<Runnable> workQueue,
     *                  RejectedExecutionHandler handler)
     用给定的初始参数和默认的线程工厂创建新的 ThreadPoolExecutor。
     *
     */
    private static ThreadPoolExecutor mailPool = null;


    static {
        initMailPool();
    }

    private static void initMailPool(){
        mailPool = new ThreadPoolExecutor(
                6, 				//coreSize
                20, 				//MaxSize
                60, 			//60
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),			//指定一种队列 （有界队列）
                //new LinkedBlockingQueue<Runnable>()
                new ThreadPoolExecutor.CallerRunsPolicy()  //它直接在 execute 方法的调用线程中运行被拒绝的任务；

        );
    }

    public static void exec(Runnable task){
        mailPool.execute(task);
    }


}
