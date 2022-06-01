package base.measurement;

import java.io.FileWriter;
import java.io.IOException;

import base.heuristics.*;
import base.*;
import base.neighborhood.NType;
import base.tabu.*;

public class Measurement2 {
	
	private static int IntLog(int n) {
		return (int)Math.round(Math.log(n));
	}
	
	private static int IntSqrt(int n) {
		return (int)Math.round(Math.sqrt(n));
	}

	public static void main(String args[]) throws IOException {
		
		FileWriter fw = new FileWriter("/home/jakub_s/AlgMeta/TSP/Etap2/measure2.csv");
		fw.write("n, 2n, 3n, sqrt(n), 2sqrt(n), 3sqrt(n), ln(n), 2ln(n), 3ln(n)\n");
		
		Path solution;
		int objFunction;
		int tabuLength;
		
		Matrix matrices[][] = new Matrix[20][5];
		
		for(int i=1; i<=10; i++) {	
			int size = 5*i;
			for(int j=1; j<=5; j++) {	
				matrices[i-1][j-1] = Matrices.randomSymmetricTSP(size);
			}
		}
	
		
		
		for(int i=0; i<=9; i++) {	
			for(int j=0; j<=4; j++) {	
				
				int size = 5*(i+1);
				
				Path path = Paths.ascPath(matrices[i][j]);
				NearestNH nh = new NearestNH(matrices[i][j]);
				path = nh.solve(path);
				
				
				tabuLength = size;
				TabuSearch ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 2*size;
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 3*size;
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				tabuLength = IntSqrt(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 2*IntSqrt(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 3*IntSqrt(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				tabuLength = IntLog(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 2*IntLog(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + ", ");
				
				
				tabuLength = 3*IntLog(size);
				ts = new TabuSearch(matrices[i][j], tabuLength, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i][j].objectiveFunction(solution);
				fw.write(objFunction + "\n");
				
				
			}
		}
		
		fw.close();
		
	}
}
