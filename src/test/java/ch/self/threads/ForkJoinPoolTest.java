package ch.self.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/*
 * https://www.baeldung.com/thread-pool-java-and-guava
 * https://www.baeldung.com/java-fork-join
 *
 * ForkJoinPool is the central part of the fork/join framework introduced in Java 7.
 * It solves a common problem of spawning multiple tasks in recursive algorithms.
 * Using a simple ThreadPoolExecutor, you will run out of threads quickly,
 * as every task or subtask requires its own thread to run.
 *
 * In a fork/join framework, any task can spawn (fork) a number of subtasks and wait
 * for their completion using the join method. The benefit of the fork/join framework
 * is that it does not create a new thread for each task or subtask,
 * implementing the Work Stealing algorithm instead.
 */
public class ForkJoinPoolTest {

    private final static long startingTime = System.nanoTime();

    @Test
    void testForkJoinPool() {
        TreeNode tree = new TreeNode(1,
                new TreeNode(2), new TreeNode(3,
                        new TreeNode(4), new TreeNode(5)));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int sum = forkJoinPool.invoke(new CountingTask(tree));

        assertEquals(15, sum);
    }

    private static class CountingTask extends RecursiveTask<Integer> {
        private final TreeNode node;

        private CountingTask(TreeNode node) {
            this.node = node;
        }

        @Override
        protected Integer compute() {
            return node.getValue() + node.getChildren()
                                         .stream()
                                         .map(childNode -> new CountingTask(childNode).fork())
                                         .parallel()
                                         .mapToInt(ForkJoinTask::join)
                                         .sum();
        }
    }

    private static class TreeNode {
        private final int value;
        private final Set<TreeNode> children;

        TreeNode(int value, TreeNode... children) {
            this.value = value;
            this.children = Arrays.stream(children).collect(Collectors.toSet());
        }

        int getValue() {
            System.out.println(((System.nanoTime() - startingTime) / 1000000) + ". getValue: " + value);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return value;
        }
        Set<TreeNode> getChildren() {
            return children;
        }
    }
}
