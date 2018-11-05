package Graph;

import java.awt.Color;

/**
 *
 * @author Muaaz
 */
public class Edge {
	private Node start;
	private Node end;
//	private String id;
	private double weight;
	private Color edgeColor;

	public Edge(Node start, Node end) {
		this.start = start;
		this.end = end;
//		this.id = start.getId() + "*" + end.getId(); // example : 1*2
		this.weight = Math.sqrt(Math.pow(start.getPos().getX() - end.getPos().getX(), 2)
				+ Math.pow(start.getPos().getY() - end.getPos().getY(), 2));
		this.edgeColor = new Color(0, 153, 153);
	}

	public Node getStart() {
		return this.start;
	}

	public Node getEnd() {
		return this.end;
	}

	public double getWeight() {
		return this.weight;
	}

	public Color getEdgeColor() {
		return this.edgeColor;
	}

	public void setEdgeColor(Color color) {
		this.edgeColor = color;
	}

//	public String getId() {
//		return this.id;
//	}
}
