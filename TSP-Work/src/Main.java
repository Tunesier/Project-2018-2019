import java.awt.Color;
import Extern.ReadWriteExcel;
import Graph.Client;
import Graph.Edge;
import Gui.GUI;
import Model.Driver;
import Model.Position;

/**
 * @author Muaaz
 */
public class Main {

	static String[][] XLSmatrix;
	// static String[][] familiarityMatrix;
	// public static StringBuilder XLS_Output;
	public static Client[] clients;
	public static Edge[] edges;
	public static Driver[] drivers;

	public static void main(String args[]) {

		try {
			XLSmatrix = ReadWriteExcel
					.readXLSFileToArray("C:/Users/moaaz/eclipse-workspace/TSP-Work-Newer/clientInfo.xls");
			/*
			 * familiarityMatrix = ReadWriteExcel .readXLSFileToArray(
			 * "C:/Users/moaaz/eclipse-workspace/TSP-Work-Newer/familiarityInfo.xls");
			 */
		} catch (Exception ex) {
			System.err.println(ex);
		}
		System.out.println("XLSmatrix length (number of rows): " + XLSmatrix.length);
		System.out.println("XLSmatrix length (number of column in each row): " + XLSmatrix[0].length);
		System.out.println("#####################################################################");

		/*
		 * XLS_Output.append("XLSmatrix length (number of rows): " + XLSmatrix.length +
		 * "\n"); XLS_Output.append("XLSmatrix length (number of column in each row): "
		 * + XLSmatrix[0].length+ "\n"); XLS_Output.append(
		 * "#####################################################################"+
		 * "\n");
		 */

		// Create Clients and depot
		// ///////////////////////////////////////////////////////////////////////////////
		initializeDepotAndClients();
		// ///////////////////////////////////////////////////////////////////////////////////////////
		// Create Edge
		initializeEdges();
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Create Drivers
		initializeDrivers();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		GUI gui = new GUI(clients, edges, drivers);
		System.out.println("Min OverAll Weight : " + GUI.MinOverAllWeight);
		System.out.println("#####################################################################");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0; i < drivers.length; i++) {
			drivers[i].MinOverAllWeight = GUI.MinOverAllWeight;
		}

	}

	static void initializeDepotAndClients() {
		clients = new Client[XLSmatrix.length]; // depot in clients[0]

		clients[0] = new Client(new Position(Integer.parseInt(XLSmatrix[0][1]), Integer.parseInt(XLSmatrix[0][2])), 0,
				0);// 0 for familiarity due to depot
		clients[0].setDepot(true);
		System.out.println(clients[0]);
		clients[0].setColor(Color.RED);

		for (int i = 1; i < clients.length; i++) { // Clients have ID [1, infinity]
			clients[i] = new Client(new Position(Integer.parseInt(XLSmatrix[i][1]), Integer.parseInt(XLSmatrix[i][2])),
					i, 2);// 2 as the number of drivers available
			/*
			 * for (int j = 0; j < familiarityMatrix[i - 1].length; j++) {
			 * clients[i].fillFamiliarityMatrix(j, (Integer.parseInt(familiarityMatrix[i -
			 * 1][j]))); }
			 */
			System.out.println(clients[i]);
			clients[i].showFamiliarityMatrix();
			// XLS_Output.append(clients[i]+ "\n");
		}
		System.out.println("#####################################################################");
		// XLS_Output.append("#####################################################################"+
		// "\n");

	}

	static void initializeEdges() {
		edges = new Edge[(clients.length * (clients.length - 1)) / 2]; // Gauss-formel
		int index = 0; // edges index

		for (int i = 0; i < clients.length; i++) {
			for (int j = i + 1; j < clients.length; j++) {
				edges[index] = new Edge(clients[i], clients[j], index);
				clients[i].edges.add(edges[index]);
				clients[j].edges.add(edges[index]);
				GUI.MinOverAllWeight += edges[index].getWeight(0);
				System.out.println(edges[index]);
				index++;
			}
		}
		System.out.println("#####################################################################");
	}

	static void initializeDrivers() {
		drivers = new Driver[2];
		for (int i = 0; i < drivers.length; i++) {
			drivers[i] = new Driver(i);
		}
	}
}
