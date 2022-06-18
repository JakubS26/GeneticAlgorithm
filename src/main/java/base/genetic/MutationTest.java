package base.genetic;

import base.Matrices;
import base.heuristics.Paths;
import base.*;
import java.util.ArrayList;

public class MutationTest {
	public static void main(String args[]) {
		
		Matrix m = Matrices.randomSeedSymmetricTSP(25, 851345);
		
		ArrayList<Path> list = new ArrayList<Path>();
		
		for(int i=0; i<=4; i++) {
			list.add(Paths.randomPath(m));
		}
		
		for(int i=0; i<=4; i++) {
			Path p = list.get(i);
			p.print();
			System.out.println(m.objectiveFunction(p));
			System.out.println(p.getPathLen());
			Mutation.mutate(list.get(i), 0.5, m);
			p.print();
			System.out.println(m.objectiveFunction(p));
			System.out.println(p.getPathLen());
			System.out.println();
		}
		
	}
}
