package base.operators;

import base.Path;

import java.util.Arrays;

public class HalfCrossover extends AbstractOperator {
	
	//Metoda służy do sortowania elementów tablicy array od indeksów p do q zgodnie z kolejnością podaną w tablicy order
	
	private void CustomSort(int[] array, int p, int q, int[] order) {	
		
		int[] sortPart = Arrays.copyOfRange(array, p, q+1);
		
		Arrays.sort(sortPart);
		for(int i=0; i<=order.length-1; i++) {
			int ind = Arrays.binarySearch(sortPart, order[i]);
			if(ind >= 0) {
				array[p] = order[i];
				p++;
			}
		}
	}

	@Override
	public Path[] reproduce(Path parent1, Path parent2) {
		
		Path offspring[] = new Path[2];
		
		Path offspring1 = new Path(parent1.size);
		Path offspring2 = new Path(parent1.size);
		
		int m = parent1.size/2;
		
		for(int i=0; i<=parent1.size-1; i++) {
			offspring1.vertices[i] = parent1.vertices[i];
			offspring2.vertices[i] = parent2.vertices[i];
		}
		
		CustomSort(offspring1.vertices, m, parent1.size-1, parent2.vertices);
		CustomSort(offspring2.vertices, m, parent1.size-1, parent1.vertices);

		offspring[0] = offspring1;
		offspring[1] = offspring2;
		
		return offspring;
	}

}
