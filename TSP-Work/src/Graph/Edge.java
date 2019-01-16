package Graph;

import java.awt.Color;

import Model.TravelTimeXLS;

/**
 *
 * @author Muaaz
 */
public class Edge {
	private final Client start;
	private final Client end;
	private final int ID;
	private double weight;
	private Color edgeColor;

	private int isAssignedToDriver = -1;
	private boolean isUsed = false;

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Edge(Client start, Client end, int ID) {

		this.start = start;
		this.end = end;
		this.ID = ID;
		this.weight = Math.sqrt(Math.pow(start.getPos().getX() - end.getPos().getX(), 2)
				+ Math.pow(start.getPos().getY() - end.getPos().getY(), 2));
		this.edgeColor = new Color(0, 153, 153);
	}

	public Client getStart() {
		return this.start;
	}

	public Client getEnd() {
		return this.end;
	}
	public double getWeight() {
		return this.weight;
	}

	public double getWeight(int i) {
		if(i == 1) {
			return TravelTimeXLS.TWENTY_MINUTES;
		}
		return TravelTimeXLS.THIRTY_MINUTES;
	}

	public Color getEdgeColor() {
		return this.edgeColor;
	}

	public void setEdgeColor(Color color) {
		this.edgeColor = color;
	}

	public int getId() {
		return this.ID;
	}

	public Edge findEdge(Client start, Client end) {
		if (this.start.equals(start) && this.end.equals(end)) {
			return this;
		}
		return null;
	}

	public boolean isAssignedToDriver(int driverNumber) {
		return isAssignedToDriver == driverNumber;
	}

	public void setAssignedToDriver(int driverNumber) {
		this.isAssignedToDriver = driverNumber;
	}

	@Override
	public String toString() {
		return "EdgeID: " + this.getId() + " ### Weight: " + this.getWeight(0) + " ### StartNode: " + this.getStart()
				+ " ### EndNode: " + this.getEnd();
	}
}
