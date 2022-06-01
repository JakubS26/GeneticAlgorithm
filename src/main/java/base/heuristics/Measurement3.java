package base.heuristics;

import java.io.FileWriter;
import java.io.IOException;

import base.heuristics.*;
import base.*;
import base.neighborhood.NType;
import base.tabu.*;

public class Measurement3 {
	
	
	public static void main(String args[]) throws IOException {
		
		FileWriter fw = new FileWriter("/home/jakub_s/AlgMeta/TSP/Etap2/measure3.csv");
		fw.write("0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2, 3.4, 3.6, 3.8, 4.0\n");
		
		//double PT[] = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
		double PT[] = {0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2, 3.4, 3.6, 3.8, 4.0};
		Path solution = null;
		int objFunction;
		
		Matrix matrices[] = new Matrix[10];
		
		for(int i=0; i<=9; i++) {
			matrices[i] = Matrices.randomSymmetricTSP(50);
		}
		
		for(int i=0; i<=9; i++) {
			for(int j=0; j<=17; j++) {
				
				Path path = Paths.ascPath(matrices[i]);
				NearestNH nh = new NearestNH(matrices[i]);
				path = nh.solve(path);
				
				TabuSearch ts = new TabuSearch(matrices[i], 100, 10000, PT[j], 1000, 100, 10000, NType.INVERT, NType.INSERT);
				solution = ts.solve(path);
				objFunction = matrices[i].objectiveFunction(solution);
				
				if(j == 17) {
					fw.write(objFunction + "\n");
				} else {
					fw.write(objFunction + ", ");
				}
				
				
			}
		}
		
		fw.close();

	}
	
}
