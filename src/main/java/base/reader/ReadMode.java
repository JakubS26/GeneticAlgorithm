package base.reader;

import base.Matrix;
import base.MatrixEuclid;

import java.io.FileNotFoundException;

public interface ReadMode {
    public Matrix read(ProblemInstance dane) throws FileNotFoundException;
}
