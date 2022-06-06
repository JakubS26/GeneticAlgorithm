package base.operators;

import base.Path;

public class OperatorsTest {
	
	public static void main(String args[]) {
		
		int Parent1[] = {3, 4, 8, 2, 7, 1, 6, 5};
		int Parent2[] = {4, 2, 5, 1, 6, 8, 3, 7};
		
		Path path1 = new Path(Parent1);
		Path path2 = new Path(Parent2);
		
		HalfCrossover hx = new HalfCrossover();
		
		Path offspring[] = hx.reproduce(path1, path2);
		
		System.out.println("Operator HX:");
		
		for(int i=0; i<=path1.size-1; i++) {
			System.out.println(offspring[0].vertices[i] + "   " + offspring[1].vertices[i]);
		}
		
		OrderCrossover ox = new OrderCrossover();
		
		offspring = ox.reproduce(path1, path2);
		
		System.out.println("\nOperator OX:");
		
		for(int i=0; i<=path1.size-1; i++) {
			System.out.println(offspring[0].vertices[i] + "   " + offspring[1].vertices[i]);
		}
		
		PartMapCrossover pmx = new PartMapCrossover();
		
		offspring = pmx.reproduce(path1, path2);
		
		System.out.println("\nOperator PMX:");
		
		for(int i=0; i<=path1.size-1; i++) {
			System.out.println(offspring[0].vertices[i] + "   " + offspring[1].vertices[i]);
		}
		
	}
	
}
