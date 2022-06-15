package base.genetic;

import base.Matrices;
import base.heuristics.Paths;
import base.*;

public class MutationTest {
	public static void main(String args[]) {
		
		Matrix m = Matrices.randomSeedSymmetricTSP(25, 851345);
		
		for(int i=1; i<=30; i++) {
			Path p = Paths.randomPath(m);
			p.print();
			Mutation.mutate(p, 0.5, m);
			p.print();
			System.out.println();
		}
		
	}
}
