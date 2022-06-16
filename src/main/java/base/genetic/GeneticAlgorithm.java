package base.genetic;

import base.*;
import base.selection.*;
import base.heuristics.Paths;
import base.operators.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
	
	private Matrix matrix;
	private int popSize;
	private int generations;
	private int opt;
	private int parents;
	private double repProb;
	private double mutProb;
	private double memProb;
	private CrossType crossover;
	private int selectionType;
	private Random rand;
	
	public GeneticAlgorithm(Matrix matrix, int popSize, int generations, int opt, int parents,
							double repProb, double mutProb, double memProb, CrossType crossover, int selectionType) {
		this.matrix = matrix;
		this.popSize = popSize;
		this.generations = generations;
		this.opt = opt;
		this.parents = parents;
		if(this.parents % 2 != 0)
				this.parents++;
		this.repProb = repProb;
		this.mutProb = mutProb;
		this.memProb = memProb;
		this.crossover = crossover;
		this.selectionType = selectionType;
		this.rand = new Random();
	}
	
	public Path solve() {
		
		AbstractOperator crossOperator = CrossFactory.getCrossover(crossover);
		
		Memetic memetic = new Memetic(matrix, 5);
		
		//Najlepsze rozwiązanie znalezione w trakcie działania algorytmu
		
		Path bestPath = null;
		int bestObjFunction;
		Path newPath;
		
		//Generujemy początkową populację rozwiązań (losowanie)
		
		ArrayList<Path> population = new ArrayList<Path>();
		
		newPath = Paths.randomPath(matrix);
		population.add(newPath);
		bestPath = newPath;
		bestObjFunction = matrix.objectiveFunction(bestPath);
		
		for(int i=1; i<=popSize-1; i++) {
			newPath = Paths.randomPath(matrix);
			population.add(newPath);
			//newPath.print();
			if(matrix.objectiveFunction(newPath) < bestObjFunction) {
				bestPath = newPath;
				bestObjFunction = matrix.objectiveFunction(newPath);
			}
		}
		 
		int elite = 3500;
		
		for(int i=1; i<=elite; i++) {
			Path improved = memetic.solve(population.get(i));
			population.set(i, improved);
		}
		
		//Ustawiamy metodę selekcji rodziców
		
		SelectionMethod selection;
		
		if(this.selectionType == 0) {
			selection = new RouletteSelect(population, this.matrix);
		} else {
			selection = new TournamentSelect(this.matrix, population);
		}
		
		ArrayList<Path> parentsPop = new ArrayList<Path>();
		Path offspring[] = null;
		
		//Główna pętla programu (liczba pokoleń)
		
		for(int i=1; i<=generations; i++) {
			
			//Wybieramy populację rodziców
			
			parentsPop.clear();
			
			for(int j=1; j<=parents; j++) {
				parentsPop.add(selection.select());
			}
						
			//Krzyżujemy rodziców (z pewnym ppb) i dodajemy dzieci do populacji
			
			for(int j=0; j<=parentsPop.size()-2; j+=2) {
				
				double p = rand.nextDouble();
				
				if(p <= repProb) {
					offspring = crossOperator.reproduce(parentsPop.get(j), parentsPop.get(j+1));
					
					if(this.matrix.objectiveFunction(offspring[0].vertices) < bestObjFunction) {
						//System.out.println("cross");
						bestPath = offspring[0];
						bestObjFunction = this.matrix.objectiveFunction(offspring[0].vertices);
						System.out.println(i + ". " + bestObjFunction + " "  + 100.0*(double)(bestObjFunction-opt)/(double)opt + " %");
					}
					if(this.matrix.objectiveFunction(offspring[1].vertices) < bestObjFunction) {
						//System.out.println("cross");
						bestPath = offspring[1];
						bestObjFunction = this.matrix.objectiveFunction(offspring[1].vertices);
						System.out.println(i + ". " + bestObjFunction + " "  + 100.0*(double)(bestObjFunction-opt)/(double)opt + " %");
					}
						
					population.add(offspring[0]);
					population.add(offspring[1]);
				}
			}
			
			//Przeprowadzamy mutację (z pewnym ppb) wszystkich osobników
			
			for(int j=0; j<=population.size()-1; j++) {
				Mutation.mutate(population.get(j), mutProb, this.matrix);
				if(population.get(j).getPathLen() < bestObjFunction) {
					//System.out.println("mut");
					bestPath = population.get(j);
					bestObjFunction = population.get(j).getPathLen();
					System.out.println(i + ". " + bestObjFunction + " "  + 100.0*(double)(bestObjFunction-opt)/(double)opt + " %");
				}
			}
			
			//Przeprowadzamy "uczenie się" osobników
			
//			for(int j=0; j<=population.size()-1; j++) {
//				
//				double x = rand.nextDouble();
//				if(x <= memProb) {
//					Path improved = memetic.solve(population.get(j));
//					population.remove(j);
//					population.add(j, improved);
//				}
//			}
			
			//Przeprowadzamy powtórną selekcję (część osobników ginie), rozmiar populacji wraca do początkowego
			//Póki co powtórna selekcja jest przeprowadzana losowo
			
			while(population.size() > popSize) {
				int index = rand.nextInt(population.size());
				population.remove(index);
			}
			
		}
		
		return bestPath;
	}
	
}
