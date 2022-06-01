package base.heuristics;

import base.IndexedPath;
import base.Matrix;
import base.Path;
import base.neighborhood.TwoOptNeighbourhood;

public class TwoOptAccH implements Heuristic {
    private Matrix matrix;
    public TwoOptAccH(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public Path solve(Path startPath) {
        TwoOptNeighbourhood hood = new TwoOptNeighbourhood(this.matrix, startPath);
        Path bestPath = startPath;
        int bestLength = hood.getBaseLength();
        int loopBestLength = bestLength;
        Path loopBestPath = startPath;
        while (true) {
            while (hood.hasNext()) {
                IndexedPath tempPath = hood.getNext();
                if (tempPath.getLength() < loopBestLength) {
                    loopBestPath = tempPath.getPath();
                    loopBestLength = tempPath.getLength();
                }
            }
            if (loopBestLength < bestLength) {
                hood = new TwoOptNeighbourhood(this.matrix, loopBestPath);
                bestPath = loopBestPath;
                bestLength = loopBestLength;
            } else {
                return bestPath;
            }
        }
    }
}
