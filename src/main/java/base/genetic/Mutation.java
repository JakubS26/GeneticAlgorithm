package base.genetic;

import base.*;
import java.util.Random;

public class Mutation {
	public static void mutate(Path path, double prob, Matrix m) {
		
		Random rand = new Random();
		
		double r;
		double geneProb = prob/(double)path.size;
		int secondGene;
		
		//System.out.println(geneProb);
		
		for(int i=0; i<=path.size-1; i++) {
			 
			r = rand.nextDouble();
			
			if(r <= geneProb) {
				secondGene = rand.nextInt(path.size);
				int temp = path.vertices[i];
				path.vertices[i] = path.vertices[secondGene];
				path.vertices[secondGene] = temp;
			}
			
		}
		
		path.setPathLen(m.objectiveFunction(path));
	}
}
