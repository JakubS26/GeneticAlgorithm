package base;

public class MatrixEuclid extends Matrix {
    public double[] x_coordinates;
    public double[] y_coordinates;
    public MatrixEuclid(int size) {
        super(size);
        this.x_coordinates = new double[size];
        this.y_coordinates = new double[size];
    }
    public void printPoints() {
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.x_coordinates[i] + " " + this.y_coordinates[i]);
        }
    }

}
