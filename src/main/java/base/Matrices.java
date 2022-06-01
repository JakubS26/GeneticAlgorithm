package base;

import java.util.Random;

public class Matrices {
    public static Matrix randomATSP(int size) {

        Matrix m = new Matrix(size);
        Random r = new Random();

        m.isSymmetric = false;

        for(int i=0; i<=size-1; i++) {
            for(int j=0; j<=size-1; j++) {

                if(i != j) {
                    m.distances[i][j] = r.nextInt(1000)+1;
                } else {
                    m.distances[i][j] = 0;
                }
            }

        }

        return m;
    }

    public static Matrix randomSymmetricTSP(int size) {

        Matrix m = new Matrix(size);
        Random r = new Random();

        m.isSymmetric = true;

        for(int i=0; i<=size-1; i++) {
            for(int j=0; j<=i; j++) {

                if(i != j) {
                    int l = r.nextInt(1000)+1;
                    m.distances[i][j] = l;
                    m.distances[j][i] = l;
                } else {
                    m.distances[i][j] = 0;
                }
            }

        }

        return m;
    }
    
    public static Matrix randomSeedSymmetricTSP(int size, long seed) {

        Matrix m = new Matrix(size);
        Random r = new Random(seed);

        m.isSymmetric = true;

        for(int i=0; i<=size-1; i++) {
            for(int j=0; j<=i; j++) {

                if(i != j) {
                    int l = r.nextInt(1000)+1;
                    m.distances[i][j] = l;
                    m.distances[j][i] = l;
                } else {
                    m.distances[i][j] = 0;
                }
            }

        }

        return m;
    }

    public static Matrix randomEUC_2D_TSP(int size) {

        Matrix m = new Matrix(size);
        Random r = new Random();

        m.isSymmetric = true;

        int x_coordinates[] = new int[size];
        int y_coordinates[] = new int[size];

        for(int i=0; i<=size-1; i++) {
            x_coordinates[i] = r.nextInt(1000);
            y_coordinates[i] = r.nextInt(1000);
        }

        double dx, dy;

        for(int i=0; i<=size-1; i++) {
            for(int j=0; j<=i; j++) {

                dx = x_coordinates[i] - x_coordinates[j];
                dy = y_coordinates[i] - y_coordinates[j];

                int l = (int)Math.round(Math.sqrt(dx*dx+dy*dy));
                m.distances[i][j] = l;
                m.distances[j][i] = l;

            }

        }

        return m;
    }

    public static MatrixEuclid randomEUC_2D(int size) {

        MatrixEuclid e = new MatrixEuclid(size);
        e.isSymmetric = true;

        Random r = new Random();

        int[] x_coordinates = new int[size];
        int[] y_coordinates = new int[size];

        for(int i=0; i<=size-1; i++) {
            x_coordinates[i] = r.nextInt(1000);
            y_coordinates[i] = r.nextInt(1000);
        }

        for(int i=0; i<=size-1; i++) {
            for(int j=0; j<=i; j++) {

                double dx = x_coordinates[i] - x_coordinates[j];
                double dy = y_coordinates[i] - y_coordinates[j];

                int l = (int)Math.round(Math.sqrt(dx*dx + dy*dy));
                e.distances[i][j] = l;
                e.distances[j][i] = l;

            }

        }

        double[] x_coor = new double[size];
        double[] y_coor = new double[size];

        for(int i=0; i<=size-1; i++) {
            x_coor[i] = (double)x_coordinates[i];
            y_coor[i] = (double)y_coordinates[i];
        }

        e.x_coordinates = x_coor;
        e.y_coordinates = y_coor;

        return e;
    }
}
