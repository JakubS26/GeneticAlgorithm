package base.operators;

import java.util.Random;

import base.Path;

public class PartMapCrossover extends AbstractOperator {
	
	private int findMapping(int[] from, int[] to, int p, int q, int elem) {
		
		for(int i=p; i<=q; i++) {
			if(from[i] == elem) {
				return to[i];
			}
		}
		
		return 0;
	}
	
	private boolean isElement(int[] arr, int p, int q, int e) {
		for(int i=p; i<=q; i++) {
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
				
		int cutPoint1 = rand.nextInt(parent1.size);
		int cutPoint2 = rand.nextInt(parent1.size);
		
		if(cutPoint2 < cutPoint1) {
			int temp = cutPoint2;
			cutPoint2 = cutPoint1;
			cutPoint1 = temp;
		}
		
		System.out.println("Wybrano punkty podziału: " + cutPoint1 + ", " + cutPoint2);
		
		for(int i=cutPoint1; i<=cutPoint2; i++) {
			offspring1.vertices[i] = parent2.vertices[i];
			offspring2.vertices[i] = parent1.vertices[i];
		}
		
		int element;
		
		for(int i=0; i<=parent1.size-1; i++) {
			if(i <= cutPoint1 - 1 || i >= cutPoint2 + 1 ) {
				
				element = parent1.vertices[i];
				while (isElement(parent2.vertices, cutPoint1, cutPoint2, element)) {
					element = findMapping(parent2.vertices, parent1.vertices, cutPoint1, cutPoint2, element);
				}
				offspring1.vertices[i] = element;
				
				element = parent2.vertices[i];
				while (isElement(parent1.vertices, cutPoint1, cutPoint2, element)) {
					element = findMapping(parent1.vertices, parent2.vertices, cutPoint1, cutPoint2, element);
				}
				offspring2.vertices[i] = element;
				
			}
		}
		
		offspring[0] = offspring1;
		offspring[1] = offspring2;
		
		return offspring;
	}
	
	
}
