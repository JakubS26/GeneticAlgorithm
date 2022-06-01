package base.reader;

import base.Matrix;
import base.MatrixEuclid;
import base.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadModeEuclid2D implements ReadMode {
    public MatrixEuclid read(ProblemInstance instance) throws FileNotFoundException {

        Scanner scan = new Scanner(instance);
        MatrixEuclid m = new MatrixEuclid(instance.getDimension());

        if (instance.getType().startsWith("TSP")) {
            m.isSymmetric = true;
        }

        String line = "";
        String[] splitLine = null;

        while (scan.hasNextLine() && !line.startsWith("NODE_COORD_SECTION")) {
            line = scan.nextLine();
        }

        for (int i = 0; i < instance.getDimension(); i++) {
            line = scan.nextLine();
            splitLine = Utils.splitTrim(line, " ");

            m.x_coordinates[i] = Double.parseDouble(splitLine[1]);
            m.y_coordinates[i] = Double.parseDouble(splitLine[2]);
        }

        for (int i = 0; i < instance.getDimension(); i++) {
            for (int j = 0; j < instance.getDimension(); j++) {

                double dx = m.x_coordinates[i] - m.x_coordinates[j];
                double dy = m.y_coordinates[i] - m.y_coordinates[j];

                m.distances[i][j] = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
            }
        }
        scan.close();
        return m;
    }
}
