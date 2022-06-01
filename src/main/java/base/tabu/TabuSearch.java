package base.tabu;

import base.*;
import base.neighborhood.*;

import java.util.LinkedList;

public class TabuSearch {
	
	private Matrix matrix;
	private int tabuLength;
	private int iterations;
	private double promisingThreshold;
	private int maxNoImprovement;
	private int maxNoImprovementJump;
	private int maxMemorySize;
	private NType primaryN ;
	private NType secondaryN;
	private int optimum;
	
	public TabuSearch(Matrix m, int len, int iter, double promisingThreshold,
					  int maxNoImprovement, int maxNoImprovementJump, int maxMemorySize, NType primaryN, NType secondaryN) {
		this.promisingThreshold = promisingThreshold;
		this.matrix = m;
		this.tabuLength = len;
		this.iterations = iter;
		this.maxNoImprovement = maxNoImprovement;
		this.maxNoImprovementJump = maxNoImprovementJump;
		this.maxMemorySize = maxMemorySize;
		this.primaryN = primaryN;
		this.secondaryN = secondaryN;
	}
	
	public TabuSearch(Matrix m, int len, int iter, double promisingThreshold,
			  int maxNoImprovement, int maxNoImprovementJump, int maxMemorySize, NType primaryN, NType secondaryN, int optimum) {
		this.promisingThreshold = promisingThreshold;
		this.matrix = m;
		this.tabuLength = len;
		this.iterations = iter;
		this.maxNoImprovement = maxNoImprovement;
		this.maxNoImprovementJump = maxNoImprovementJump;
		this.maxMemorySize = maxMemorySize;
		this.primaryN = primaryN;
		this.secondaryN = secondaryN;
		this.optimum = optimum;
}
	

	
	private boolean isPromising(int objFunction, int bestObjFunction) {
		return objFunction < promisingThreshold * bestObjFunction;
	}

	public Path solve (Path solution) {
		
		//Listy nawrotów do przechowywania odpowiednio kolejnych obiecujących ścieżek i list nawrotów
		LinkedList<Path> returnListPath = new LinkedList<>();
		LinkedList<Tabu> returnListTabu = new LinkedList<>();
		
		//Liczba iteracji bez poprawy najlepszego rozwiązania
		int noImprovement = 0;
		int noImprovementJump = 0;
		boolean stagnation = true;
		Path lastPerturbationPath = null;
		boolean altMode = false;
		
		//Najlepsze rozwiązanie i najniższa funkcja celu w danej iteracji
		IndexedPath bestIterationIndPath = null;
		int bestIterationObjFunction = 0;
		Tabu tabu = new Tabu(tabuLength, this.matrix.isSymmetric);
		
		//Najlepsze rozwiązanie znalezione podczas całego dziłania algorytmu i najniższa funckja celu
		Path bestPath = solution;
		int bestObjFunction = matrix.objectiveFunction(solution);
		
		//Zmienna do generowania sąsiedztwa
		Path neighbourGen = solution;
		
		//Główna pętla algorytmu
		
		for(int i=1; i<=iterations; i++) {
			//Przeszukiwanie sąsiedztwa
			bestIterationIndPath = null;
			Neighbourhood neigh = NFactory.getNeighbourhood(this.primaryN, this.matrix, neighbourGen);
			stagnation = true;
			while(neigh.hasNext()) {
				IndexedPath currentIndPath = neigh.getNext();
				if(!tabu.contains(currentIndPath) || isPromising(currentIndPath.getLength(), bestObjFunction)) {
					if(currentIndPath.getLength() < bestObjFunction) {
						bestPath = currentIndPath.getPath();
						bestObjFunction = currentIndPath.getLength();
						stagnation = false;
						//System.out.println("Poprawa " + i + " najl. " + bestObjFunction);
						System.out.println(i + ". " + bestObjFunction + " " + 100.0*((double)bestObjFunction - (double)optimum)/(double)optimum + " %");
					}
					if(bestIterationIndPath == null) {
						bestIterationIndPath = currentIndPath;
						bestIterationObjFunction = currentIndPath.getLength();
					} else if(currentIndPath.getLength() < bestIterationObjFunction) {
						bestIterationIndPath = currentIndPath;
						bestIterationObjFunction = currentIndPath.getLength();
					}
				}
			}
			// Ocena wyniku przeszukiwania
			if (stagnation) {
				noImprovement++;
			} else {
				noImprovement = 0;
				noImprovementJump = 0;
			}
			if(bestIterationIndPath == null || noImprovement == this.maxNoImprovement) {
				// Stagnacja
				noImprovement = 0;
				if (returnListTabu.isEmpty() || returnListPath.isEmpty()) {
					/* Lista nawrotów pusta */
					if (bestPath == lastPerturbationPath) {
//						System.out.println("Koniec " + i);
						return bestPath;
					}
					/* Zmiana sąsiedztwa bez tabu */
					tabu = new Tabu(this.tabuLength, this.matrix.isSymmetric);
					NType temp = this.primaryN;
					this.primaryN = this.secondaryN;
					this.secondaryN = temp;
					altMode = !altMode;
					lastPerturbationPath = bestPath;
//					System.out.println("Zmiana " + i + " " + this.primaryN);
				} else {
					if (altMode) {
						/* Zmiana na główne sąsiedztwo bez tabu */
						noImprovementJump = 0;
						tabu = new Tabu(this.tabuLength, this.matrix.isSymmetric);
						NType temp = this.primaryN;
						this.primaryN = this.secondaryN;
						this.secondaryN = temp;
						altMode = false;
//						System.out.println("Zmiana " + i + " " + this.primaryN);
					} else {
						noImprovementJump++;
						if (noImprovementJump == this.maxNoImprovementJump) {
							/* Skok bez tabu ze zmianą sąsiedztwa */
							noImprovementJump = 0;
							tabu = new Tabu(this.tabuLength, this.matrix.isSymmetric);
							NType temp = this.primaryN;
							this.primaryN = this.secondaryN;
							this.secondaryN = temp;
							altMode = true;
							neighbourGen = returnListPath.removeFirst();
							returnListTabu.removeFirst();
//							System.out.println("Zmiana " + i + " " + this.primaryN);
						} else {
							/* Wykonaj skok do pozycji z listy nawrotów */
							tabu = returnListTabu.removeLast();
							neighbourGen = returnListPath.removeLast();
//							System.out.println("Skok " + i + " lista " + returnListPath.size());
						}
					}
				}
			} else {
				tabu.insert(new Indices(bestIterationIndPath.first, bestIterationIndPath.second));
				neighbourGen = bestIterationIndPath.getPath();
				if (!stagnation) {
					/* W tym kroku nastąpiła poprawa
					* Możliwy powrót w to miejsce
					* i obranie innego kierunku poszukiwań */
					returnListTabu.addFirst(tabu);
					returnListPath.addFirst(neigh.getPath());
					if (returnListTabu.size() > this.maxMemorySize) {
						returnListTabu.remove(this.maxMemorySize/ 2);
						returnListPath.remove(this.maxMemorySize/ 2);
					}
//					System.out.println("Dodano " + i);
				}
			}
		}
		
		if(bestPath == solution) {
//			System.out.println("\nBrak poprawy " + bestObjFunction);
		} else {
//			System.out.println("poprawa: " + matrix.objectiveFunction(solution) + " -> " + matrix.objectiveFunction(bestPath));
		}
		
		return bestPath;
	}
		
	
}
