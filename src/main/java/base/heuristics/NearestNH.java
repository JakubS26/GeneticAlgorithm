package base.heuristics;

import base.Matrix;
import base.Path;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NearestNH implements Heuristic {
    public final Matrix matrix;
    public volatile PathIntPair bestPath;
    private CountDownLatch latch;
    public NearestNH(Matrix matrix) {
        this.matrix = matrix;
        this.bestPath = new PathIntPair();
    }
    public Path solve(Path startPath) {
        this.bestPath.setFields(startPath.vertices, this.matrix.objectiveFunction(startPath));
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(coreCount);
        this.latch = new CountDownLatch(this.matrix.size);
        for (int i = 0; i < this.matrix.size; i++) {
            executor.execute(new NearestNTask(this, i + 1));
        }
        try {
            this.latch.await();
            executor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Path(this.bestPath.tab);
    }
    public synchronized void update(int[] tab, int newValue) {
        if (newValue < this.bestPath.value) {
            this.bestPath.setFields(tab, newValue);
        }
        this.latch.countDown();
    }
}
