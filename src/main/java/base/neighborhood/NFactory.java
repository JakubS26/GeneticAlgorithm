package base.neighborhood;

import base.Matrix;
import base.Path;

public class NFactory {
    public static Neighbourhood getNeighbourhood(NType type, Matrix matrix, Path path) {
        switch (type) {
            case INVERT -> {
                return new TwoOptNeighbourhood(matrix, path);
            }
            case SWAP -> {
                return new SwapHood(matrix, path);
            }
            case INSERT -> {
                return new InsertHood(matrix, path);
            }
            default -> {
                return new TwoOptNeighbourhood(matrix, path);
            }
        }
    }
}
