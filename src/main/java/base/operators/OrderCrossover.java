package base.operators;

import base.Path;
import java.util.Random;

public class OrderCrossover extends AbstractOperator {
	
	private boolean isElement(int[] arr, int e) {
		for(int i=0; i<=arr.length-1; i++) {
			if(arr[i] == e) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Path[] reproduce(Path parent1, Path parent2) {

		Random rand = new Random();
		
		Path offspring[] = new Path[2];
		
		Path offspring1 = new Path(parent1.size);
		Path offspring2 = new Path(parent1.size);
		
//		int cutPoint1 = 0;
//		int cutPoint2 = 0;
//		
//		while(Math.abs(cutPoint1-cutPoint2) == 0) {
//			cutPoint1 = rand.nextInt(parent1.size);
//			cutPoint2 = rand.nextInt(parent1.size);
//		}
		
		int cutPoint1 = rand.nextInt(parent1.size);
		int cutPoint2 = rand.nextInt(parent1.size);

		
		if(cutPoint2 < cutPoint1) {
			int temp = cutPoint2;
			cutPoint2 = cutPoint1;
			cutPoint1 = temp;
		}
		
		//System.out.println("Wybrano punkty podziału: " + cutPoint1 + ", " + cutPoint2);
		
		for(int i=cutPoint1; i<=cutPoint2; i++) {
			offspring1.vertices[i] = parent1.vertices[i];
			offspring2.vertices[i] = parent2.vertices[i];
		}
		
		int ind1 = (cutPoint2 + 1) % parent1.size;
		int ind2 = (cutPoint2 + 1) % parent1.size;
		
		int i = (cutPoint2+1) % parent1.size;
		
		do {
			if( !isElement(offspring1.vertices, parent2.vertices[i]) ) {
				offspring1.vertices[ind1] = parent2.vertices[i];
				ind1 = (ind1 + 1) % parent1.size;
			} 
			if( !isElement(offspring2.vertices, parent1.vertices[i]) ) {
				offspring2.vertices[ind2] = parent1.vertices[i];
				ind2 = (ind2 + 1) % parent1.size;
			}
			i = (i+1) % parent1.size;
		} while(i != (cutPoint2 + 1) % parent1.size);
				
		offspring[0] = offspring1;
		offspring[1] = offspring2;
		
		return offspring;
	}

}
