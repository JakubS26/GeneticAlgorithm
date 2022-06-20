package base.selection;

import base.Matrix;
import base.Path;

import java.util.List;

public class SelectionMethods {
    public enum Method {
        ROULETTE,
        TOURNAMENT
    }
    public static SelectionMethod getMethod(Matrix matrix, List<Path> population, Method type) {
        switch (type) {
            case ROULETTE -> {
                return new RouletteSelect(population, matrix);
            }
            case TOURNAMENT -> {
                return new TournamentSelect(matrix, population);
            }
        }
        return null;
    }
}
