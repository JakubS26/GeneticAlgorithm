package base.reader;

import base.Matrix;
import base.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ReadModeFull implements ReadMode {
    @Override
    public Matrix read(ProblemInstance instance) throws FileNotFoundException {

        Scanner scan = new Scanner(instance);
        Matrix m = new Matrix(instance.getDimension());

        if (instance.getType().startsWith("TSP")) {
            m.isSymmetric = true;
        }

        String line = "";
        String[] splitLine = null;

        while (scan.hasNextLine() && !line.startsWith("EDGE_WEIGHT_SECTION")) {
            line = scan.nextLine();
        }

        int number = instance.getDimension() * instance.getDimension();

        int[] weights = new int[number];

        int c = 0;

        while (c < number) {

            line = scan.nextLine();

            splitLine = Utils.splitTrim(line, " ");

            for (int k = 0; k <= splitLine.length - 1; k++) {
                weights[c] = Integer.parseInt(splitLine[k]);
                c++;
            }

        }

        for (int i = 0; i < instance.getDimension(); i++) {
            if (instance.getDimension() >= 0)
                System.arraycopy(weights, i * instance.getDimension(), m.distances[i], 0, instance.getDimension());
            /*for (int j = 0; j < instance.getDimension(); j++) {
                m.distances[i][j] = weights[i * instance.getDimension() + j];
            }*/
        }

        scan.close();
        return m;
    }
}
