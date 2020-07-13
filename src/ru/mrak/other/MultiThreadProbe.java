package ru.mrak.other;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class MultiThreadProbe {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        //runnable();
        //executorRunnable();
        //executorCallable();
        //invokeAll();
        //invokeAny();
        //scheduled();
        //synchronizedTest();
        //reentrantLockTest();
        //readWriteLockTest();
        //semaphore();
        //atomicInteger();
        //longAdder();
        concurrentMap();
        
        
        long end = System.currentTimeMillis();
        System.out.println("time " + ((end - start) / 1000.0));
    }
    
    private static void runnable() {
        Runnable task = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Thread start " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Thread end " + name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    
        for (int i = 0; i < 10; i++) {
            (new Thread(task)).start();
        }
    
        System.out.println("Done");
    }
    
    private static void executorRunnable() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                //TimeUnit.SECONDS.sleep(2);
                while(true) {
                    if (false) break;
                }
                String name = Thread.currentThread().getName();
                System.out.println("Thread start " + name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("shutdown start");
        executor.shutdown();
        System.out.println("shutdown end");
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("await Termination");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executor.isTerminated()) {
                System.out.println("work");
            }
            System.out.println("shutdownNow");
            executor.shutdownNow();
        }
    }
    
    private static void executorCallable() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
    
        Callable<Integer> task = () -> {
            System.out.println("Callable task");
            TimeUnit.SECONDS.sleep(5);
            return 1;
        };
    
        Future<Integer> submit = executor.submit(task);
        try {
            System.out.println(submit.isDone());
            System.out.println(submit.get(1, TimeUnit.SECONDS));
            System.out.println(submit.isDone());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    
    private static void invokeAll() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
    
        List<Callable<String>> callableList = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3",
                () -> "task4");
    
        try {
            executor.invokeAll(callableList).stream()
                    .map(stringFuture -> {
                        try {
                            return stringFuture.get();
                        } catch (InterruptedException |ExecutionException e) {
                            e.printStackTrace();
                        }
                        return null;})
                    .forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    
    private static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
    
    private static void invokeAny() {
        ExecutorService executor = Executors.newWorkStealingPool();
        
        List<Callable<String>> callableList = Arrays.asList(
                callable("task1", 1),
                callable("task2", 2),
                callable("task3", 3));
    
        try {
            String result = executor.invokeAny(callableList);
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    
    private static void scheduled() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        Runnable task = () -> System.out.println("Scheduled " + System.nanoTime());
        ScheduledFuture<?> schedule = executor.schedule(task, 3, TimeUnit.SECONDS);
    
        TimeUnit.MICROSECONDS.sleep(1337);
    
        System.out.println(schedule.getDelay(TimeUnit.MILLISECONDS));
    }
    
    private static int count = 0;
    
    private static synchronized void increment() {
        count = count + 1;
    }
    
    private static void synchronizedTest() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        Runnable task = MultiThreadProbe::increment;
        IntStream.range(0, 100000).forEach(i -> executor.submit(task));
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println(count);
    }
    
    private static ReentrantLock reentrantLock = new ReentrantLock();
    
    private static void reentrantIncrement() {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " " + reentrantLock.getQueueLength());
            count++;
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
    
    private static void reentrantLockTest() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        Runnable task = MultiThreadProbe::reentrantIncrement;
        IntStream.range(0, 100).forEach(i -> executor.submit(task));
        
        executor.shutdown();
        executor.awaitTermination(500, TimeUnit.SECONDS);
        
        System.out.println(count);
    }
    
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    
    private static void readWriteLockTest() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        
        Runnable writeTask = () -> {
            System.out.println("writeTask");
            readWriteLock.writeLock().lock();
            try {
                count = 100;
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
        };
        
        Runnable readTask = () -> {
            System.out.println("readTask");
            readWriteLock.readLock().lock();
            try {
                System.out.println(count);
            } finally {
                readWriteLock.readLock().unlock();
            }
        };
    
        System.out.println("start");
        executor.submit(writeTask);
        executor.submit(readTask);
        executor.submit(readTask);
    
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
    
    private static void semaphore() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
    
        Semaphore semaphore = new Semaphore(5);
        
        Runnable task = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("done");
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    System.out.println("not");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };
        
        IntStream.range(0, 10).forEach(i -> executor.submit(task));
    
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
    
    private static void atomicInteger() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executor = Executors.newWorkStealingPool();
        
        IntStream.range(0, 10000).forEach(i -> executor.submit(() -> atomicInteger.accumulateAndGet(i, Integer::sum)));
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    
        System.out.println(atomicInteger);
    }
    
    private static void longAdder() throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        ExecutorService executor = Executors.newWorkStealingPool();
        
        IntStream.range(0, 10000).forEach(i -> longAdder.add(5));
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    
        System.out.println(longAdder.intValue());
    }
    
    private static void concurrentMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newWorkStealingPool();
        
        map.putIfAbsent("1", "1");
        map.putIfAbsent("1", "2");
        map.putIfAbsent("2", "2");
        map.putIfAbsent("3", "3");
        map.putIfAbsent("4", "4");
        
        map.compute("2", (key, value) -> key + value);
        
        map.merge("4", "5", (ov, nv) -> ov + nv);
        
        map.forEachValue(1, System.out::println);
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }
}
