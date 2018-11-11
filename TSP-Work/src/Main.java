import java.awt.Color;
import java.util.ArrayList;

import Extern.ReadWriteExcel;
import Graph.Client;
import Graph.Depot;
import Graph.Edge;
import Gui.GUI;
import Model.Driver;
import Model.Position;

/**
 * @author Muaaz
 */
public class Main {

	public static void main(String args[]) {
		String[][] matrix;
		Client[] clients;
		Edge[] edges;
		Depot depot;
		Driver[] driver;

		try {
			matrix = ReadWriteExcel.readXLSFileToArray("C:/Users/moaaz/eclipse-workspace/TSP-Work/clientInfo.xls");// read
																													// xls
			System.out.println("matrix length: " + matrix.length);
			// Create Clients and depot
			// ///////////////////////////////////////////////////////////////////////////////

			depot = new Depot(new Position(Integer.parseInt(matrix[0][1]), Integer.parseInt(matrix[0][2])), 0);
			depot.setColor(Color.RED);
			clients = new Client[matrix.length - 1]; // -1 for the depot
			for (int i = 0; i < clients.length; i++) { // Clients have ID [1, infinity]
				clients[i] = new Client(
						new Position(Integer.parseInt(matrix[i + 1][1]), Integer.parseInt(matrix[i + 1][2])), i + 1);
			}
			// Create Edge
			// ///////////////////////////////////////////////////////////////////////////////////////////
			edges = new Edge[clients.length * (clients.length + 1) / 2]; // Gauss-formel

			int index = 0; // edges index
			for (int i = 0; i < clients.length; i++) {
				edges[index] = new Edge(depot, clients[i]);
				clients[i].edges.add(index);
				depot.edges.add(index);
				index++;
			}

			for (int i = 0; i < clients.length; i++) {
				for (int j = i + 1; j < clients.length; j++) {
					edges[index] = new Edge(clients[i], clients[j]);
					clients[i].edges.add(index);
					clients[j].edges.add(index);
					index++;
				}
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			driver = new Driver[2];
			for (int i = 0; i < driver.length; i++) {
				driver[i] = new Driver(i, clients.length);
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////

			GUI gui = new GUI(depot, clients, edges, driver);

		} catch (Exception ex) {
			System.err.println(ex);
		}

	}
}
