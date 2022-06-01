package base.reader;

import base.Matrix;
import base.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class InstanceReader {
    public static Matrix read(ProblemInstance instance) throws FileNotFoundException {
        ReadMode mode;
        if (instance.getType().startsWith("ATSP")) {
            mode = new ReadModeFull();
        } else if (instance.getType().startsWith("TSP")) {
            if (instance.getEdgeWeight().startsWith("EUC_2D")) {
                mode = new ReadModeEuclid2D();
            }  else if (instance.getEdgeWeight().startsWith("EXPLICIT")) {
                if (instance.getFormat().startsWith("FULL_MATRIX")) {
                    mode = new ReadModeFull();
                } else if (instance.getFormat().startsWith("LOWER_DIAG_ROW")) {
                    mode = new ReadModeLowerDiagRow();
                } else {
                    throw new IllegalArgumentException("nieznany format macierzy: " + instance.getFormat());
                }
            } else {
                throw new IllegalArgumentException("nieznany typ danych: " + instance.getEdgeWeight());
            }
        } else {
            throw new IllegalArgumentException("to nie jest TSP/ATSP: " + instance.getType());
        }
        return mode.read(instance);
    }
}
