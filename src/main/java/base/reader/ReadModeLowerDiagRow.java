package base.reader;

import base.Matrix;
import base.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadModeLowerDiagRow implements ReadMode {
    @Override
    public Matrix read(ProblemInstance instance) throws FileNotFoundException {

        Scanner scan = new Scanner(instance);
        Matrix m = new Matrix(instance.getDimension());

        if (instance.getType().startsWith("TSP")) {
            m.isSymmetric = true;
        }

        String line = "";
        String[] splitLine = null;

        while(scan.hasNextLine() && !line.startsWith("EDGE_WEIGHT_SECTION")) {
            line = scan.nextLine();
        }

        int number = instance.getDimension() * (instance.getDimension() + 1)/2;

        int[] weights = new int[number];

        int c = 0;

        while(c <= number-1) {

            line = scan.nextLine();

            splitLine = Utils.splitTrim(line, " ");

            for(int k=0; k<=splitLine.length-1; k++) {
                weights[c] = Integer.parseInt(splitLine[k]);
                c++;
            }

        }

        for(int i=0; i < instance.getDimension(); i++) {
            for(int j=0; j<=i; j++) {
                m.distances[i][j] = weights[i*(i+1)/2+j];
                m.distances[j][i] = m.distances[i][j];
            }
        }

        scan.close();
        return m;
    }
}
