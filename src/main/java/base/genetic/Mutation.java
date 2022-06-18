package base.genetic;

import base.*;
import java.util.Random;

public class Mutation {
	public static Path mutate(Path path, double prob, Matrix m) {
		
		Random rand = new Random();
		
		double r;
		double geneProb = prob/(double)path.size;
		int secondGene;
		
		Path newPath = new Path(path.size);
		
		for(int i=0; i<=path.size-1; i++) {
			newPath.vertices[i] = path.vertices[i];
		}
		
		for(int i=0; i<=newPath.size-1; i++) {
			 
			r = rand.nextDouble();
			
			if(r <= geneProb) {
				secondGene = rand.nextInt(newPath.size);
				int temp = newPath.vertices[i];
				newPath.vertices[i] = newPath.vertices[secondGene];
				newPath.vertices[secondGene] = temp;
			}
			
		}
		
		newPath.setPathLen(m.objectiveFunction(newPath));
		return newPath;
	}
}
