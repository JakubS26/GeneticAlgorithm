package base.measurement;

import java.io.FileWriter;
import java.io.IOException;

import base.heuristics.*;
import base.*;
import base.neighborhood.NType;
import base.tabu.*;

public class Measurement1 {
	
	public static void main(String args[]) throws IOException {
		
		FileWriter fw = new FileWriter("/home/jakub_s/AlgMeta/TSP/Etap2/measure1.csv");
		
		fw.write("INVERT/INSERT, INVERT/SWAP, INSERT/INVERT, INSERT/SWAP, SWAP/INVERT, SWAP/INSERT\n");
		
		Matrix matrices[] = new Matrix[5]; 
		
		Path solution;
		int objFunction;
		
		matrices[0] = Matrices.randomSeedSymmetricTSP(30, 453139);
		matrices[1] = Matrices.randomSeedSymmetricTSP(30, 3813055);
		matrices[2] = Matrices.randomSeedSymmetricTSP(30, 732958);
		matrices[3] = Matrices.randomSeedSymmetricTSP(30, 3951911);
		matrices[4] = Matrices.randomSeedSymmetricTSP(30, 915387);
		
		for(int i=0; i<=4; i++) {
			
			Path path = Paths.ascPath(matrices[i]);
			NearestNH nh = new NearestNH(matrices[i]);
			path = nh.solve(path);
				
			TabuSearch ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.INSERT);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + ", ");
			
			ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.INVERT, NType.SWAP);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + ", ");
			
			ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.INSERT, NType.INVERT);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + ", ");
			
			ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.INSERT, NType.SWAP);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + ", ");
			
			ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.SWAP, NType.INVERT);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + ", ");

			ts = new TabuSearch(matrices[i], 10, 10000, 1, 1000, 100, 10000, NType.SWAP, NType.INSERT);
			
			solution = ts.solve(path);
			objFunction = matrices[i].objectiveFunction(solution);
			fw.write(objFunction + "\n");
				
			
		}
		
		fw.close();

		
		
	}
}
