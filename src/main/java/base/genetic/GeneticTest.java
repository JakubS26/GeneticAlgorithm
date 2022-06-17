package base.genetic;

import java.io.FileNotFoundException;

import base.*;
import base.operators.CrossType;
import base.reader.*;

public class GeneticTest {

	public static void main(String args[]) throws NumberFormatException, FileNotFoundException {
		
		//Matrix m = Matrices.randomSeedSymmetricTSP(50, 851345);

		String berlin52 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/berlin52.tsp";		//opt = 7542
		String gr120 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/gr120.tsp";			//opt = 6942
		String d198 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/d198.tsp";				//opt = 15780
		String a280 = "/home/jakub_s/AlgMeta/TSP/ALL_tsp/a280.tsp";				//opt = 2579
		String br17 = "/home/jakub_s/AlgMeta/TSP/ALL_atsp/br17.atsp";			//opt = 39
		String ft70 = "/home/jakub_s/AlgMeta/TSP/ALL_atsp/ft70.atsp";			//opt = 38673
				
		ProblemInstance pi = new ProblemInstance(ft70);
		
		Matrix m  = InstanceReader.read(pi);
		
		GeneticAlgorithm ga = new GeneticAlgorithm(m, 1000, 1000, 38673, 700, 1.0, 0.1, 1.0, CrossType.PMX, 1);
		Path solution = ga.solve();
		
		solution.print();
		
		System.out.println(m.objectiveFunction(solution));
		
	}
	
}
