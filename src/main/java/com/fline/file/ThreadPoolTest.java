package com.fline.file;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("ac-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(2, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


        List<Task> tasks  = new ArrayList<>();
        tasks.add(new Task("a"));
        tasks.add(new Task("b"));
        tasks.add(new Task("c"));

        pool.invokeAll(tasks,50,TimeUnit.SECONDS);
    }
}


class Task implements Callable<String> {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws InterruptedException {
        System.out.println("name是："+name+",当前线程是："+Thread.currentThread().getName());
        Thread.sleep(5000);
        return name;
    }
}