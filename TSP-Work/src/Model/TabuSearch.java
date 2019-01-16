package Model;

import Graph.Edge;
import Gui.GUI;

public class TabuSearch {

	int[] currentSolution;
	int[][] tabuMatrix;
	int TABU_NUM = 5;
	final int MAX_ITERATION = 50;

	public TabuSearch(int driverNum) {
		currentSolution = new int[GUI.drivers[driverNum].clientsToServeToday.size() + 1];// +1 for the depot
		tabuMatrix = new int[GUI.drivers[driverNum].clientsToServeToday.size()
				+ 1][GUI.drivers[driverNum].clientsToServeToday.size() + 1];
		initTabuMatrix();
		solve(driverNum);
	}

	/*
	 * enum tabuMatrixValue { NONE; }
	 */

	void initTabuMatrix() {
		for (int i = 0; i < tabuMatrix.length; i++) {
			for (int j = 0; j < tabuMatrix[0].length; j++) {
				tabuMatrix[i][j] = -1;
			}
		}
	}

	/*						(n)
	 * binomialKoeffezient	| |
	 * 						(2)
	 */						
	void solve(int driverNum) {
		int n = (GUI.drivers[driverNum].clientsToServeToday.size()+1);
		int binomialKoeffezient = ((n)*(n-1))/2; 
		for (int i = 0; i < MAX_ITERATION; i++) {
			for (int j = 0; j < binomialKoeffezient; j++) {
				/*
				 * TODO swap every kombination and take the best candidate .. make it then tabu in the tabuMaterix
				 */
			}
		}
	}

	public void swap(Edge edge1, Edge edge2) {
		Edge temp = edge1;
		edge1 = edge2;
		edge2 = temp;
	}

}
