package Graph;

import java.awt.Color;
import java.util.ArrayList;

import Model.Position;
import Model.TravelTimeXLS;

/**
 *
 * @author Muaaz
 */
public class Client {

	private final Position pos;
	private final int ID;
	private Color color;
	public ArrayList<Edge> edges = new ArrayList<>();// edges connected with this client
	public int familiarityMatrix[];

	private boolean served = false;
	private boolean isDepot = false;

	public Client(Position pos, int ID, int familiarityMatrixLength) {
		this.pos = pos;
		this.ID = ID;
		this.color = new Color(255, 255, 255);
		this.familiarityMatrix = new int[familiarityMatrixLength];
		for(int i : familiarityMatrix) {
			familiarityMatrix[i] = 0;
		}
	}

	public boolean isDepot() {
		return isDepot;
	}

	public void setDepot(boolean isDepot) {
		this.isDepot = isDepot;
	}

	public boolean isServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	public int getID() {
		return this.ID;
	}

	public Position getPos() {
		return this.pos;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}
	
	public int isFamiliarWith(int driverNum) {
		if (familiarityMatrix[driverNum] == 1) {
			return 1;
		}
		return 0;
	}
	
	public void setFamiliarWith(int driverNum) {
		this.familiarityMatrix[driverNum] = 1;
	}
	
	public void fillFamiliarityMatrix(int i, int familiarityMatrixNum) {
		this.familiarityMatrix[i] = familiarityMatrixNum;
	}
	
	public void showFamiliarityMatrix() {
		System.out.println("Familiarity Matrix:  ");
		for(int i : familiarityMatrix) {
			System.out.print(i + "  ,");
		}
		System.out.println("#################################");

	}
	
	public double getServiceTime(int driverNum) {
		if(this.familiarityMatrix[driverNum] == 1) {
			return TravelTimeXLS.FAST_SERVICE_TIME;
		}
		return TravelTimeXLS.NORMAL_SERVICE_TIME;
	}

	@Override
	public String toString() {
		if (!isDepot) {
			return "ClientID: " + this.getID() + " ### Pos: " + this.getPos();
		} else {
			return "DepotID: " + this.getID() + " ### DepotPos: " + this.getPos();
		}
	}
}
