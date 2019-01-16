package Model;

import java.awt.Color;

import Graph.Client;
import Graph.Edge;
import Gui.GUI;

public class Solver {

	Client nextClient = null;
	boolean startToTake = true;
	// TabuSearch[] tabuSearches = new TabuSearch[GUI.drivers.length];

	public void solveClicked() {
		for (int driverNum = 0; driverNum < GUI.drivers.length; driverNum++) {
			// tabuSearches[driverNum] = new TabuSearch(driverNum);
			int index = 0;
			boolean wentOutFromDepot = false;
			while (!CheckAllClientsServed(driverNum)) {

				// for (Client client : GUI.drivers[driverNum].clientsToServeToday) {
				if (wentOutFromDepot) {
					double tempMin = Integer.MAX_VALUE;
					int id = -1;
					// if (client == tempClient) {
					for (Edge edge : nextClient.edges) {
						if (edge.isAssignedToDriver(driverNum)) {
							// System.out.println("EdgeID: " + edge.getId() + " EdgeStart: " +
							// edge.getStart().getID()
							// + " EdgeEnd: " + edge.getEnd().getID() + " EdgeEndIsDepot: "
							// + edge.getEnd().isDepot());
							if (edge.getStart().getID() == nextClient.getID()) {
								if ((!edge.getEnd().isDepot()) && (!edge.getEnd().isServed()) && (!edge.isUsed())) {
									if (tempMin > edge.getWeight()) {
										tempMin = edge.getWeight();
										id = edge.getId();
										startToTake = true;
									}
								}
							} else {
								if ((!edge.getStart().isDepot()) && (!edge.getStart().isServed()) && (!edge.isUsed())) {
									if (tempMin > edge.getWeight()) {
										tempMin = edge.getWeight();
										id = edge.getId();
										startToTake = false;
									}
								}
							}

						}
					}
					// System.out.println("id " + id + " tempMin = " + tempMin);
					GUI.drivers[driverNum].setSkill(1);
					// tabuSearches[driverNum].currentSolution[index] =
					// GUI.edges[id].getStart().getID();

					index++;
					makeClientAndEdgeUsed(id, driverNum);
				}
				// }
				else {
					double tempMin = Integer.MAX_VALUE;
					int id = -1;
					for (Edge edge : GUI.clients[0].edges) {
						if (edge.isAssignedToDriver(driverNum)) {
							if (edge.getStart().isDepot()) {
								if (tempMin > edge.getWeight()) {
									tempMin = edge.getWeight();
									id = edge.getId();
									nextClient = edge.getEnd();
								}
							}
						}
					}
					// tabuSearches[driverNum].currentSolution[index] = GUI.clients[0].getID();
					index++;
					wentOutFromDepot = true;
					System.out.println(" EdgeID: " + GUI.edges[id].getId() + "  --> is used now!");
					GUI.edges[id].setEdgeColor(Color.white);
					GUI.edges[id].setUsed(true);
					if (GUI.edges[id].getEnd().isFamiliarWith(driverNum) != 1) {
						GUI.edges[id].getEnd().setFamiliarWith(driverNum);
					}
					GUI.drivers[driverNum].MinOverAllWeight += GUI.edges[id]
							.getWeight(GUI.edges[id].getEnd().isFamiliarWith(driverNum));
					GUI.drivers[driverNum].MinOverAllWeight += GUI.edges[id].getEnd()
							.getServiceTime(GUI.edges[id].getEnd().isFamiliarWith(driverNum));

					GUI.drivers[driverNum].setSkill(GUI.edges[id].getEnd().familiarityMatrix[driverNum]);
					GUI.drivers[driverNum].timeNeededForTour += (GUI.edges[id]
							.getWeight(GUI.edges[id].getEnd().isFamiliarWith(driverNum)));

				}
			}
			goBackToDepot(driverNum, index);
			TravelTimeXLS.REST_OF_DAY_TIME = 100;
		}
		for (Edge edge : GUI.edges) {
			if (!edge.isUsed()) {

				edge.setEdgeColor(Color.DARK_GRAY);

			}
		}

	}

	void goBackToDepot(int driverNum, int index) {
		for (Client client : GUI.drivers[driverNum].clientsToServeToday) {
			if (!client.isServed()) {
				int id = -1;
				for (Edge edge : client.edges) {
					if (edge.isAssignedToDriver(driverNum) && (edge.getEnd().isDepot() || edge.getStart().isDepot())) {
						if (edge.getEnd().isDepot()) {
							startToTake = true;
						} else {
							startToTake = false;
						}

						id = edge.getId();
					}
				}
				// System.out.println("id " + id + " tempMin = " + tempMin);
				if (startToTake) {
					// tabuSearches[driverNum].currentSolution[index] =
					// GUI.edges[id].getStart().getID();
				} else {
					// tabuSearches[driverNum].currentSolution[index] =
					// GUI.edges[id].getEnd().getID();
				}
				index++;
				makeClientAndEdgeUsed(id, driverNum);
				// System.out.printf("Actual MinimalTour: %.3f",
				// GUI.drivers[driverNum].MinOverAllWeight);
			}
		}

	}

	void makeClientAndEdgeUsed(int id, int driverNum) {
		if (id != -1) {
			GUI.drivers[driverNum].MinOverAllWeight += GUI.edges[id].getWeight();
			if (startToTake) {
				System.out.println(" EdgeID: " + GUI.edges[id].getId() + "  --> is used now!");
				System.out.println(" StartID: " + GUI.edges[id].getStart().getID() + "  --> is used now!");
				GUI.edges[id].setEdgeColor(Color.white);
				GUI.edges[id].getStart().setColor(Color.yellow);
				GUI.edges[id].setUsed(true);
				GUI.edges[id].getStart().setServed(true);
				if (GUI.edges[id].getStart().isFamiliarWith(driverNum) != 1) {
					GUI.edges[id].getStart().setFamiliarWith(driverNum);
				}
				GUI.drivers[driverNum].timeNeededForTour += (GUI.edges[id]
						.getWeight(GUI.edges[id].getStart().isFamiliarWith(driverNum)));

				nextClient = GUI.edges[id].getEnd();
			} else {
				System.out.println(" EdgeID: " + GUI.edges[id].getId() + "  --> is used now!");
				System.out.println(" EndID: " + GUI.edges[id].getEnd().getID() + "  --> is used now!");
				GUI.edges[id].setEdgeColor(Color.white);
				GUI.edges[id].getEnd().setColor(Color.yellow);
				GUI.edges[id].setUsed(true);
				GUI.edges[id].getEnd().setServed(true);
				if (GUI.edges[id].getEnd().isFamiliarWith(driverNum) != 1) {
					GUI.edges[id].getEnd().setFamiliarWith(driverNum);
				}
				GUI.drivers[driverNum].timeNeededForTour += (GUI.edges[id]
						.getWeight(GUI.edges[id].getEnd().isFamiliarWith(driverNum)));
				nextClient = GUI.edges[id].getStart();
			}
		}
	}

	void showWhichClientsToWhichDriver(int driverNum) {
		System.out.print("Driver " + driverNum + " has : \n");
		for (int i = 0; i < GUI.drivers[driverNum].clientsToServeToday.size(); i++) {
			System.out.println(GUI.drivers[driverNum].clientsToServeToday.get(i) + "  ");
		}
		System.out.println("#####################################################");
	}

	void showWhichEdgesToWhichDriver(int driverNum) {
		System.out.print("Driver " + driverNum + " has : \n");
		for (int i = 0; i < GUI.drivers[driverNum].edgesICanUseToday.size(); i++) {
			int id = GUI.drivers[driverNum].edgesICanUseToday.get(i).getId();
			System.out.println("*\\|| " + GUI.edges[id] + " ||/* ");
		}
		System.out.println("#####################################################");

	}

	// not used but maybe useful
	public Edge tryRandomEdge(int driverNum) {
		int random;
		Edge edge;
		Client c;
		if (driverNum == 0) {
			for (Client temp : GUI.drivers[driverNum].clientsToServeToday) {
				System.out.println(temp);
			}
		}
		do {
			random = (int) (Math.random() * GUI.drivers[driverNum].edgesICanUseToday.size());
			edge = GUI.drivers[driverNum].edgesICanUseToday.get(random);
			if (!edge.getEnd().isDepot()) {
				// if (!(edge.getStart() instanceof Depot)) {
				if (!edge.getEnd().isServed()) {
					return edge;
				}
				// }
			}

		} while (!CheckAllClientsServed(driverNum));
		return null;
	}

	public boolean CheckAllClientsServed(int driverNum) {
		int i = 0;
		for (Client temp : GUI.drivers[driverNum].clientsToServeToday) {
			if (!temp.isServed()) {
				i++;
			}
		}
		return i == 1; // the last client to be served must not be served yet!
	}

	// TODO not finished yet
	public void nextDayClicked() {
		GUI.day += 1;
		// for (int i = 0; i < GUI.drivers.length; i++) {
		// GUI.drivers[i].setSkill(GUI.drivers[i].getSkill() + 1);
		// }
		reset();
	}

	public void reset() {
		for (int i = 0; i < GUI.edges.length; i++) {
			GUI.edges[i].setEdgeColor(new Color(0, 153, 153));
		}
		for (int i = 0; i < GUI.clients.length; i++) {
			GUI.clients[i].setColor(new Color(255, 255, 255));
			GUI.clients[i].setServed(false);
		}
		for (int i = 0; i < GUI.drivers.length; i++) {
			GUI.drivers[i].clientsToServeToday.clear();
			GUI.drivers[i].edgesICanUseToday.clear();
		}
	}

	public void arrangeDriversToDistinationClicked() {
		int degreesProRadius = calcRadiusForEveryDriver();
		setTheRightDriverForEachClient(degreesProRadius);
		setAvailableEdgesForEachDriver(degreesProRadius);
	}

	public int calcRadiusForEveryDriver() {
		return 360 / GUI.drivers.length;
	}

	// to calculate angle between two points in degrees ..
	// "now it works after testing it and multiplying it with -1 "
	public double calcAngleBetweenTwoPoints(Position p1, Position p2) {
		double temp = -1 * (Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()) * 180 / Math.PI);
		if (temp < 0.0) {
			temp *= -1;
			if (temp <= 90.0) {
				temp += 270;
			} else {
				// this can be done in one step but
				// i left it so to make it clearer for the others
				temp -= 90;
				temp += 180;
			}
		}
		return temp;
	}

	public void setTheRightDriverForEachClient(int degreesProRadius) {
		for (int i = 1; i < GUI.clients.length; i++) {
			double temp = calcAngleBetweenTwoPoints(GUI.clients[0].getPos(), GUI.clients[i].getPos());
			GUI.clients[i].setColor(GUI.colors[(int) temp / degreesProRadius]);
			GUI.drivers[(int) temp / degreesProRadius].clientsToServeToday.add(GUI.clients[i]);
		}
	}

	public void setAvailableEdgesForEachDriver(int degreesProRadius) {
		for (int driverNum = 0; driverNum < GUI.drivers.length; driverNum++) {// for each driver

			for (int i = 0; i < GUI.drivers[driverNum].clientsToServeToday.size(); i++) {// for each client belongs to
																							// this driver
				// take all the edges from depot to the right distinations
				for (Edge edgeTemp : GUI.edges) {
					if (edgeTemp.findEdge(GUI.clients[0], GUI.drivers[driverNum].clientsToServeToday.get(i)) != null) {
						edgeTemp.setEdgeColor(GUI.colors[driverNum]);
						edgeTemp.setAssignedToDriver(driverNum);
						GUI.drivers[driverNum].edgesICanUseToday.add(edgeTemp);
					}
				}
				for (int j = i + 1; j < GUI.drivers[driverNum].clientsToServeToday.size(); j++) {// take all the edges
																									// between the right
																									// distinations
					for (Edge edgeTemp : GUI.edges) {
						if (edgeTemp.findEdge(GUI.drivers[driverNum].clientsToServeToday.get(i),
								GUI.drivers[driverNum].clientsToServeToday.get(j)) != null) {
							edgeTemp.setEdgeColor(GUI.colors[driverNum]);
							edgeTemp.setAssignedToDriver(driverNum);
							GUI.drivers[driverNum].edgesICanUseToday.add(edgeTemp);
						}
					}
				}

			}
			showWhichClientsToWhichDriver(driverNum);
			showWhichEdgesToWhichDriver(driverNum);
		}

	}

}
