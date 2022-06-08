package base.selection;

import base.Matrix;
import base.Path;
import base.utils.Utils;

import java.util.List;
import java.util.Random;

public class RouletteSelect implements SelectionMethod {
    private static final int greatestWidth = 10;
    private final List<Path> population;
    private final Matrix matrix;
    private double[] cdfTable;

    public RouletteSelect(List<Path> population, Matrix matrix) {
        this.population = population;
        this.matrix = matrix;
        Path path = population.get(0);
        if (path.getPathLen() == Path.lengthNotSet) {
            path.setPathLen(this.matrix.objectiveFunction(path));
        }
        int bestLength = path.getPathLen();
        for (int i = 1; i < population.size(); i++) {
            path = population.get(i);
            if (path.getPathLen() == Path.lengthNotSet) {
                path.setPathLen(this.matrix.objectiveFunction(path));
            }
            if (path.getPathLen() < bestLength) {
                bestLength = path.getPathLen();
            }
        }
        this.cdfTable = new double[population.size()];
        this.cdfTable[0] = ((double) bestLength / population.get(0).getPathLen()) * greatestWidth;
        for (int i = 1; i < population.size(); i++) {
            this.cdfTable[i] = ((double) bestLength / population.get(i).getPathLen()) * greatestWidth;
        }
    }

    @Override
    public Path select() {
        Random random = new Random();
        double val = random.nextDouble(this.cdfTable[cdfTable.length - 1]);
        int index = Utils.findIndex(val, this.cdfTable);
        if (index >= this.cdfTable.length) {
            return null;
        }
        return population.get(index);
    }
}
