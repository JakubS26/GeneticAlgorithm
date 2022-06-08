package base.selection;

import base.Matrix;
import base.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelect implements SelectionMethod {
    private final int tSize;
    private final List<Path> population;
    private final Matrix matrix;

    public TournamentSelect(Matrix matrix, List<Path> population) {
        this.matrix = matrix;
        this.population = population;
        this.tSize = Math.min(population.size(), 10);
    }

    public TournamentSelect(Matrix matrix, List<Path> population, int tSize) {
        this.matrix = matrix;
        this.population = population;
        this.tSize = tSize;
    }

    @Override
    public Path select() {
        Random random = new Random();
        Path bestPath = population.get(random.nextInt(population.size()));
        if (bestPath.getPathLen() == Path.lengthNotSet) {
            bestPath.setPathLen(this.matrix.objectiveFunction(bestPath));
        }
        int bestLength = bestPath.getPathLen();
        for (int i = 1; i < this.tSize; i++) {
            Path path = population.get(random.nextInt(population.size()));
            if (path.getPathLen() == Path.lengthNotSet) {
                path.setPathLen(this.matrix.objectiveFunction(path));
            }
            if (path.getPathLen() < bestLength) {
                bestLength = path.getPathLen();
                bestPath = path;
            }
        }
        return bestPath;
    }
}
