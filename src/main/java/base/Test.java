package base;

import base.heuristics.NearestNH;
import base.heuristics.Paths;
import base.heuristics.TwoOptAccH;
import base.neighborhood.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import base.reader.InstanceReader;
import base.reader.ProblemInstance;
import base.tabu.*;

public class Test {
	
	public static void main(String args[]) {
		
		/*Path path = new Path(5);

		path.vertices = new int[5];

		path.vertices[0] = 1;
		path.vertices[1] = 2;
		path.vertices[2] = 3;
		path.vertices[3] = 4;
		path.vertices[4] = 5;

		TwoOptNeighbourhood nh = new TwoOptNeighbourhood(path);
		while (nh.hasNext()) {
			IndexedPath indexedPath = nh.getNext();
			System.out.println(indexedPath.beg + " " + indexedPath.end);
			indexedPath.path.print();
			System.out.println();
		}*/

		
		Matrix matrix = null;
		try {
			//matrix = InstanceReader.read(new ProblemInstance("/home/jakub/pwr/meta/tsplib_ex/tsp/kroB200.tsp"));
			matrix = InstanceReader.read(new ProblemInstance("/home/jakub_s/AlgMeta/TSP/ALL_tsp/gr120.tsp"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assert(matrix != null);
		TabuSearch ts = new TabuSearch(matrix, 100, 10000, 0.995,
				200, 15, 100, NType.INVERT, NType.INSERT, 6942);

		Path solution = new NearestNH(matrix).solve(Paths.ascPath(matrix));
		solution = new TwoOptAccH(matrix).solve(solution);
		
		Path solution1 = ts.solve(solution);
		System.out.println("check: " + matrix.objectiveFunction(solution1));
		
		solution1.print();
		
	}
	
}
