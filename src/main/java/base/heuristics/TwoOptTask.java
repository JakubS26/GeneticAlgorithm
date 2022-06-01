package base.heuristics;

import base.utils.Utils;

public class TwoOptTask implements Runnable {
    private final TwoOptH parent;
    private final int beg;
    private final int end;

    public TwoOptTask(TwoOptH parent, int beg, int end) {
        this.parent = parent;
        this.beg = beg;
        this.end = end;
    }

    @Override
    public void run() {
        int[] tab = new int[this.parent.bestAllTimePath.length];
        System.arraycopy(this.parent.bestAllTimePath, 0, tab, 0, this.parent.bestAllTimePath.length);
        Utils.invert(tab, beg, end);
        int value = this.parent.matrix.objectiveFunction(tab);
        this.parent.update(tab, value);
    }

}
