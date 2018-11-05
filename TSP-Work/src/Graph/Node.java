package Graph;

import java.awt.Color;
import java.util.ArrayList;

import Model.Position;

/**
 *
 * @author Muaaz
 */
public abstract class Node {

	private Position pos;
	private final int ID;
	public ArrayList<Integer> edges = new ArrayList<>();// edges connected with this client
	private Color color;

	public Node(Position pos, int ID) {
		this.pos = pos;
		this.ID = ID;
		this.color = new Color(255, 255, 255);
	}

	public Position getPos() {
		return this.pos;
	}

	public int getId() {
		return this.ID;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}
}
