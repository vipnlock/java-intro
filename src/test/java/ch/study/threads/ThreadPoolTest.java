package ch.study.threads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * https://www.baeldung.com/thread-pool-java-and-guava
 * https://www.baeldung.com/java-executor-service-tutorial
 *
 * Thread pool pattern.
 */
public class ThreadPoolTest {

    private final static String HELLO_WORLD = "Hello World";

    private void doAction() {
        System.out.println(HELLO_WORLD);
    }

    /*
     * Use it to create a Callable task that imitate heavy work by sleeping for 1000 milliseconds.
     */
    private String doWait() throws InterruptedException {
        Thread.sleep(1000);
        return null;
    }

    @Test
    @DisplayName("SingleThreadExecutor + Runnable")
    void testRunnable() {
        /*
         * acquire an Executor instance backed by a single thread pool and an unbounded queue for executing tasks sequentially.
         */
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(this::doAction);
    }

    @Test
    @DisplayName("FixedThreadPool + Callable")
    void testCallable() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> HELLO_WORLD);

        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            fail("InterruptedException", e);
        } catch (ExecutionException e) {
            fail("ExecutionException", e);
        }

        // assertj assertion
        assertThat(result).isEqualTo(HELLO_WORLD);
        // jupiter assertion
        assertEquals(HELLO_WORLD, result);
    }

    /* ****************** */
    /* ThreadPoolExecutor */
    /* ****************** */

    @Test
    @DisplayName("FixedThreadPool")
    void testFixedThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(this::doWait);
        executor.submit(this::doWait);
        executor.submit(this::doWait);

        ThreadPoolExecutor tpExecutor = (ThreadPoolExecutor) executor;
        // fixed thread count of 2. This means that if the amount of simultaneously running tasks is less or equal to two at all times, then they get executed right away.
        assertEquals(2, tpExecutor.getCorePoolSize());
        // Otherwise some of these tasks may be put into a queue to wait for their turn.
        assertEquals(1, tpExecutor.getQueue().size());
    }

    @Test
    @DisplayName("CachedThreadPool: a lot of short-living tasks")
    void testCachedThreadPool() {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(this::doWait);
        executor.submit(this::doWait);
        executor.submit(this::doWait);

        ThreadPoolExecutor tpExecutor = (ThreadPoolExecutor) executor;
        // the cached thread pool may grow without bounds to accommodate any amount of submitted tasks
        assertEquals(3, tpExecutor.getCorePoolSize());
        // The queue size in the example above will always be zero because internally a SynchronousQueue instance is used.
        // In a SynchronousQueue, pairs of insert and remove operations always occur simultaneously, so the queue never actually contains anything.
        assertEquals(0, tpExecutor.getQueue().size());
    }

    @Test
    @DisplayName("SingleThreadExecutor: an event loop")
    void testSingleThreadExecutor() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // will be executed sequentially
        executor.submit(() -> { counter.set(1); });
        executor.submit(() -> { counter.compareAndSet(1, 2); });

        executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        assertEquals(2, counter.get());
    }

    /* *************************** */
    /* ScheduledThreadPoolExecutor */
    /* *************************** */

    @Test
    @DisplayName("ScheduledThreadPoolExecutor")
    /*
     * The ScheduledThreadPoolExecutor
     * - extends the ThreadPoolExecutor class and
     * - also implements the ScheduledExecutorService interface with several additional methods:
     * * schedule               method allows to execute a task once after a specified delay;
     * * scheduleAtFixedRate    method allows to execute a task after a specified initial delay and
     *                          then execute it repeatedly with a certain period;
     *                          the period argument is the time measured between the starting times of the tasks,
     *                          so the execution rate is fixed;
     * * scheduleWithFixedDelay method is similar to scheduleAtFixedRate in that it repeatedly executes the given task,
     *                          but the specified delay is measured between the end of the previous task and
     *                          the start of the next;
     *                          the execution rate may vary depending on the time it takes to execute any given task.
     */
    void testScheduledThreadPool() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(3);

        /*
         * The Executors.newScheduledThreadPool() method is typically used to create a ScheduledThreadPoolExecutor
         * with a given corePoolSize, unbounded maximumPoolSize and zero keepAliveTime.
         */
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        /*
         * The following code shows how to execute a task after 500 milliseconds delay and
         * then repeat it every 100 milliseconds.
         */
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            doAction();
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        /*
         * After scheduling the task, we wait until it fires three times using the CountDownLatch lock, ...
         */
        lock.await(1000, TimeUnit.MILLISECONDS);

        /*
         * ... then cancel it using the Future.cancel() method.
         */
        future.cancel(true);
    }

}
