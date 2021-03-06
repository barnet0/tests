package com.digiwin.ecims.test.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zaregoto on 2017/1/17.
 */
public class ExecutorServiceTest {

    private static int MAX_THREAD_CNT = 2;
    public static void main(String[] args) {
        System.out.println("Main Thread Started...");
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD_CNT);
        Callable<String> task1 = new Callable<String>() {
            @Override public String call() throws Exception {
                System.out.println("Worker Thread Started@" + new Date());
                Thread.sleep((long) (Math.random() * 2000 + 1000));
                return "Thread Finished@" + new Date();
            }
        };
        Callable<String> task2 = new Callable<String>() {
            @Override public String call() throws Exception {
                System.out.println("Worker Thread Started@" + new Date());
                Thread.sleep((long) (Math.random() * 4000 + 1000));
                return "Thread Finished@" + new Date();
            }
        };
        List<Future<String>> futures = new ArrayList<>();
        futures.add(executor.submit(task1));
        futures.add(executor.submit(task2));
        System.out.println("Worker Thread Submitted...");
        System.out.println("Main Thread Ready to Get Worker Thread result@" + new Date());
        try {
            for (int i = 0; i < futures.size(); i++) {
                String resultStr = futures.get(i).get();
                System.out.println(resultStr);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        finally {
            if (executor != null && !executor.isShutdown()) {
                executor.shutdown();
            }
        }
        System.out.println("Main Thread Finished...");
    }
}
