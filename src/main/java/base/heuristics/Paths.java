package base.heuristics;

import base.Matrix;
import base.Path;

import java.util.Random;

public class Paths {
    public static Path randomPath(Matrix matrix) {
        Path result = Paths.ascPath(matrix);
        shuffle(result);
        return result;
    }
    public static Path ascPath(Matrix matrix) {
        Path result = new Path(matrix.size);
        for (int i = 0; i < result.size; i++) {
            result.vertices[i] = i + 1;
        }
        return result;
    }
    private static void shuffle(Path path) {
        Random random = new Random();
        for (int i = 0; i < path.size; i++) {
            int n = random.nextInt(i+1);
            int temp = path.vertices[i];
            path.vertices[i] = path.vertices[n];
            path.vertices[n] = temp;
        }
    }
}
