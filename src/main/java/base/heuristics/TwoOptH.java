package base.heuristics;

import base.Matrix;
import base.Path;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwoOptH implements Heuristic {
    public Matrix matrix;
    public volatile PathIntPair bestPath;
    public int[] bestAllTimePath;
    public int bestAllTime;
    public CountDownLatch latch;
    public TwoOptH(Matrix matrix) {
        this.matrix = matrix;
        this.bestPath = new PathIntPair();
    }

    @Override
    public Path solve(Path startPath) {
        this.bestPath.setFields(startPath.vertices, this.matrix.objectiveFunction(startPath));
        this.bestAllTimePath = this.bestPath.tab;
        this.bestAllTime = this.bestPath.value;
        while (true) {
            int coreCount = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(coreCount);
            this.latch = new CountDownLatch((this.matrix.size * (this.matrix.size - 1)) / 2);
            for (int i = 0; i < this.matrix.size - 1; i++) {
                for (int j = i + 1; j < this.matrix.size; j++) {
                    executor.execute(new TwoOptTask(this, i, j));
                }
            }
            try {
                this.latch.await();
                executor.shutdownNow();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.bestPath.value < this.bestAllTime) {
                this.bestAllTime = this.bestPath.value;
                this.bestAllTimePath = this.bestPath.tab;
            } else {
                return new Path(this.bestAllTimePath);
            }
        }
    }

    public synchronized void update(int[] tab, int newValue) {
        if (newValue < this.bestPath.value) {
            this.bestPath.setFields(tab, newValue);
        }
        this.latch.countDown();
    }
}
