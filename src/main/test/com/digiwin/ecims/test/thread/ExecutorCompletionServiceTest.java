package com.digiwin.ecims.test.thread;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by zaregoto on 2017/1/17.
 */
public class ExecutorCompletionServiceTest {

    private static int MAX_THREAD_CNT = 2;
    public static void main(String[] args) {
        System.out.println("Main Thread Started...");
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD_CNT);
        CompletionService<String> completionService =
            new ExecutorCompletionService<String>(executor);
        for (int i = 0; i < MAX_THREAD_CNT; i++) {
            completionService.submit(new Callable<String>() {
                @Override public String call() throws Exception {
                    System.out.println("Worker Thread Started@" + new Date());
                    Thread.sleep((long)(Math.random() * 2000 + 1000));
                    return "Thread Finished@" + new Date();
                }
            });
            System.out.println("Worker Thread " + i + " Submitted...");
        }
        System.out.println("Main Thread Ready to Get Worker Thread result@" + new Date());
        try {
            for (int i = 0; i < MAX_THREAD_CNT; i++) {
                Future<String> futureStr = completionService.take();
                String resultStr = futureStr.get();
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
