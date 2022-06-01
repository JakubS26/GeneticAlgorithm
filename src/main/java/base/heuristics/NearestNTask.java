package base.heuristics;

import base.Path;

import java.util.LinkedList;
import java.util.List;

public class NearestNTask implements Runnable {
    private final NearestNH parent;
    private List<Integer> notVisited;
    private final int start;
    public NearestNTask(NearestNH parent, int start) {
        this.parent = parent;
        this.start = start;
    }

    private int findNearest(int vertex) {
        int result = notVisited.get(0);
        int min = parent.matrix.distances[vertex][result];
        int pozz = 0;

        for (int i = 1; i < notVisited.size(); i++) {
            if (parent.matrix.distances[vertex][notVisited.get(i)] < min) {
                result = notVisited.get(i);
                min = parent.matrix.distances[vertex][result];
                pozz = i;
            }
        }
        notVisited.remove(pozz);
        return result;
    }
    @Override
    public void run() {
        this.notVisited = new LinkedList<>();
        for (int i = 0; i < parent.matrix.size; i++) {
            if (i != this.start - 1) {
                this.notVisited.add(i);
            }
        }
        int[] tab = new int[parent.matrix.size];
        tab[0] = this.start;
        int current = this.start - 1;
        for (int i = 1; i < parent.matrix.size && !Thread.interrupted(); i++) {
            current = findNearest(current);
            tab[i] = current + 1;
        }
        int value = this.parent.matrix.objectiveFunction(tab);
        this.parent.update(tab, value);
    }
}
