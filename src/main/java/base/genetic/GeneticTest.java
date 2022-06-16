package base.genetic;

import java.io.FileNotFoundException;

import base.*;
import base.operators.CrossType;
import base.reader.*;

public class GeneticTest {

	public static void main(String args[]) throws NumberFormatException, FileNotFoundException {
		
		//Matrix m = Matrices.randomSeedSymmetricTSP(10, 851345);
		
		String berlin52 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/berlin52.tsp";		//opt = 7542
		String gr120 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/gr120.tsp";			//opt = 6942
		String d198 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/d198.tsp";				//opt = 15780
		String a280 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/a280.tsp";				//opt = 2579
				
		ProblemInstance pi = new ProblemInstance(d198);
		
		Matrix m  = InstanceReader.read(pi);
		
		GeneticAlgorithm ga = new GeneticAlgorithm(m, 7000, 1000, 15780, 3000, 1.0, 0.1, 0.01, CrossType.OX, 1);
		Path solution = ga.solve();
		
		solution.print();
		
	}
	
}
