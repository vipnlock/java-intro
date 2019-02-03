package ch.study.threads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * https://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
 *
 * Working with the Thread class can be very tedious and error-prone.
 * Due to that reason the Concurrency API has been introduced back in 2004 with the release of Java 5.
 * The API is located in package java.util.concurrent
 */
public class PlainThreadsTest {

    private void printThreadName() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread \"" + threadName + "\"");
    }

    private String getThreadName() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
        String threadName = Thread.currentThread().getName();
        return "Thread \"" + threadName + "\"";
    }

    private String throwException() {
        throw new UnsupportedOperationException();
    }

    /* ********************* */
    /* Threads and Runnables */
    /* ********************* */

    @Test
    @DisplayName("Plain Runnable")
    void testRunnable() {
        Runnable task = this::printThreadName;

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }

    /* ********* */
    /* Executors */
    /* ********* */

    @Test
    @DisplayName("SingleThread Executor + shutdown")
    void firstExecutorService() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(this::printThreadName);

        shutdownExecutor(executor);
        assertThrows(RejectedExecutionException.class, () -> executor.submit(this::printThreadName));
    }

    /* ********************* */
    /* Callables and Futures */
    /* ********************* */

    @Test
    @DisplayName("Future with result")
    void testFutureWithResult() throws ExecutionException, InterruptedException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String> future = executor.submit(this::getThreadName);

        // check if the future has already been finished. this isn't the case since the above callable sleeps for one second before returning the integer
        assertFalse(future.isDone());
        // calling the method get() blocks the current thread and waits until the callable completes before returning the actual result
        final String result = future.get();
        // now the future is finally done
        assertTrue(future.isDone());

        assertEquals("Thread \"pool-1-thread-1\"", result);
    }

    @Test
    @DisplayName("Future with exception")
    void testFutureWithException() {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String> future = executor.submit(this::throwException);

        // callable threw an exception
        assertThrows(ExecutionException.class, future::get);
    }

    @Test
    @DisplayName("Future was interrupted")
    void testFutureWasInterrupted() {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String> future = executor.submit(this::getThreadName);

        executor.shutdownNow();
        assertThrows(ExecutionException.class, future::get);
    }

    /* ******** *
     * Timeouts *
     * ******** */

    @Test
    @DisplayName("Task runs too long")
    void testTaskRunsTooLong() {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new ExecutionException(e);
            }
        });

        assertThrows(TimeoutException.class, () -> future.get(1, TimeUnit.SECONDS));
    }

    /* ********* *
     * InvokeAll *
     * ********* */

    @Test
    @DisplayName("Invoke All")
    void testInvokeAll() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> tasks = Stream.of("task1", "task2", "task3")
                                             .map(input -> (Callable<String>) () -> input)
                                             .collect(Collectors.toList());

        executor.invokeAll(tasks)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }

    /* ********* *
     * InvokeAny *
     * ********* */
    /*
     * works slightly different to invokeAll().
     * Instead of returning future objects this method blocks until the first callable terminates
     * and returns the result of that callable.
     */
    @Test
    @DisplayName("Invoke Any")
    void invokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        String result = executor.invokeAny(callables);
        assertEquals("task2", result);
    }

    private Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    /* ******************* */
    /* Scheduled Executors */
    /* ******************* */
    @Test
    @DisplayName("Scheduled Future")
    void testScheduledFuture() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> future = executor.schedule(this::getThreadName, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1337);
        long remainingTime = future.getDelay(TimeUnit.MILLISECONDS);

        assertThat(remainingTime).isBetween(1650L, 1670L);
    }

    @Test
    @DisplayName("Schedule at fixed rate")
    void testScheduleAtFixedRate() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

        long startTime = System.nanoTime();
        Runnable task = () -> {
            int index = new Random().nextInt();
            try {
                System.out.println("[" + index + ". " + Thread.currentThread().getName() + "] Scheduling: " + (System.nanoTime() - startTime) / 1000000 + " ms");
                TimeUnit.MILLISECONDS.sleep(1500);
                System.out.println("[" + index + ". " + Thread.currentThread().getName() + "] Finished; queue size = " + executor.getQueue().size());
            }
            catch (InterruptedException e) {
                System.err.println("[" + index + ". " + Thread.currentThread().getName() + "] Interrupted");
            }
        };

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    @DisplayName("Schedule with fixed delay")
    void testScheduleWithFixedDelay() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

        long startTime = System.nanoTime();
        Runnable task = () -> {
            int index = new Random().nextInt();
            try {
                System.out.println("[" + index + ". " + Thread.currentThread().getName() + "] Scheduling: " + (System.nanoTime() - startTime) / 1000000 + " ms");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("[" + index + ". " + Thread.currentThread().getName() + "] Finished; queue size = " + executor.getQueue().size());
            }
            catch (InterruptedException e) {
                System.err.println("[" + index + ". " + Thread.currentThread().getName() + "] Interrupted");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
    }


    /**
     * Executors have to be stopped explicitly - otherwise they keep listening for new tasks.
     */
    private void shutdownExecutor(ExecutorService executor) {
        try {
            System.out.println("attempt to shutdown executor");
            // The executor shuts down softly ...
            executor.shutdown();
            // ... by waiting a certain amount of time for termination of currently running tasks.
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        // After a maximum of five seconds ...
        finally {
            // ... the executor finally shuts down by interrupting all running tasks.
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

}
