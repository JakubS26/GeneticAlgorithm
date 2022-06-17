package base.genetic;

import base.IndexedPath;
import base.Matrix;
import base.Path;
import base.neighborhood.TwoOptNeighbourhood;

public class Memetic {
	
    private Matrix matrix;
    private int iterations;
    
    public Memetic(Matrix matrix, int iterations) {
        this.matrix = matrix;
        this.iterations = iterations;
    }

    public Path solve(Path startPath) {
    	
        TwoOptNeighbourhood hood = new TwoOptNeighbourhood(this.matrix, startPath);
        Path bestPath = startPath;
        int bestLength = hood.getBaseLength();
        int loopBestLength = bestLength;
        Path loopBestPath = startPath;
        int i = iterations;
        
        while (i >= 1) { 
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
            }
            i--;
        }
        bestPath.setPathLen(matrix.objectiveFunction(bestPath));
        return bestPath;
    }
	
}
