package base;

public class Matrix {
    public int size;
    public int[][] distances;
    public boolean isSymmetric;

    public Matrix(int size) {
        this.size = size;
        this.distances = new int[size][size];
    }
    public int objectiveFunction(Path path) {
        return objectiveFunction(path.vertices);
    }
    public int objectiveFunction(int[] tab) {

        int suma = 0;

        /*for(int i=0; i<=rozmiar-1; i++) {
			suma = suma + odleglosci[r.wierzcholki[i]-1][(r.wierzcholki[(i+1)%rozmiar])-1];
		}*/

        for(int i = 0; i <= size -2; i++) {
            suma = suma + distances[tab[i]-1][tab[i+1]-1];
        }
        suma = suma + distances[tab[tab.length-1]-1][tab[0]-1];

        return suma;
    }
    public double PRD(Path path, int opt) {

        return 100*(double)(objectiveFunction(path)-opt)/(double)opt;
    }
    /*public int objectiveFunctionWithInvert(Path path, int beg, int end) {
        return objectiveFunctionWithInvert(path.vertices, beg, end);
    }
    public int objectiveFunctionWithInvert(int[] tab, int beg, int end) {
        int sum = 0;
        for (int i = 0; i < beg - 1; i++) {
            sum += distances[tab[i] - 1][tab[i + 1] - 1];
        }
        if (beg > 0) {
            sum += distances[tab[beg - 1] - 1][tab[end] - 1];
        }
        for (int i = end; i > beg; i--) {
            sum += distances[tab[i] - 1][tab[i - 1] - 1];
        }
        if (end < tab.length - 1) {
            sum += distances[tab[beg] - 1][tab[end + 1] - 1];
        }
        for (int i = end + 1; i < tab.length - 1; i++) {
            sum += distances[tab[i] - 1][tab[i + 1] - 1];
        }
        if (beg > 0 && end < tab.length - 1) {
            sum += distances[tab[tab.length - 1] - 1][tab[0] - 1];
        } else if (beg == 0 && end < tab.length - 1) {
            sum += distances[tab[tab.length - 1] - 1][tab[end] - 1];
        } else if (beg > 0 && end == tab.length - 1){
            sum += distances[tab[beg] - 1][tab[0] - 1];
        } else {
            sum += distances[tab[0] - 1][tab[tab.length - 1] - 1];
        }
        return sum;
    }*/
    public void print() {

        for(int i = 0; i<= size -1; i++) {
            for(int j = 0; j<= size -1; j++) {
                System.out.print(String.format("%5d ", distances[i][j]));
            }
            System.out.println("");
        }

    }
}
