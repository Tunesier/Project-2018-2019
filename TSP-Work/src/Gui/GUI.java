package Gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Graph.Edge;
import Model.Driver;
import Model.Position;

import javax.swing.JButton;

/**
 *
 * @author Muaaz
 */
public class GUI extends JFrame {
	/**
	 * Creates new MainGUI
	 * 
	 * @param edges
	 */
	static final int GUI_WIDTH = 1920;
	static final int GUI_HEIGHT = 1080;

	static Edge[] edges;
	static Driver[] driver;

	Panel panel;

	public GUI(Edge[] edges, Driver[] driver) {
		this.edges = edges;
		this.driver = driver;
		initGui();
		panel = new Panel();
		// this let us freely add JButtons in the area
		panel.setLayout(null);
		// Test JButton added !
		addButtons();
		this.setContentPane(panel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void initGui() {
		setTitle("TSP-Routing-Solver");
		setSize(GUI_WIDTH, GUI_HEIGHT);
		setBackground(new Color(51, 0, 102));
	}

	private void addButtons() {
		// Solve Button
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton solveButton = new JButton("Solve");
		solveButton.setBounds(0, 0, 100, 40);
		solveButton.setFocusPainted(false);
		solveButton.setBackground(new Color(59, 89, 182));
		solveButton.setForeground(new Color(255, 255, 255));
		solveButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(solveButton);

		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.solveClicked();
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton nextDayButton = new JButton("Next Day");
		nextDayButton.setBounds(0, 40, 100, 40);
		nextDayButton.setFocusPainted(false);
		nextDayButton.setBackground(new Color(59, 89, 182));
		nextDayButton.setForeground(new Color(255, 255, 255));
		nextDayButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(nextDayButton);

		nextDayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.nextDayClicked();
			}
		});
	}

	private static class Panel extends JPanel {

		static final int STROKE_SMALL_SIZE = 4;
		static final int STROKE_BIG_SIZE = 16;

		@Override
		public void paint(Graphics g) {
			// Setting drawing parameters
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for (int i = 0; i < edges.length; i++) {
				drawEdge(g2d, i);
				drawVertex(g2d, i);
			}

		}

		private void drawVertex(Graphics2D g2d, int index) {
			g2d.setStroke(new BasicStroke(STROKE_BIG_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			g2d.setColor(edges[index].getStart().getColor());
			g2d.drawLine(edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY(),
					edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY());

			g2d.setColor(edges[index].getEnd().getColor());
			g2d.drawLine(edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY(),
					edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY());

		}

		private void drawEdge(Graphics2D g2d, int index) {
			g2d.setStroke(new BasicStroke(STROKE_SMALL_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			g2d.setColor(edges[index].getEdgeColor());
			g2d.drawLine(edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY(),
					edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY());
		}

		void solveClicked() {
			int degreesProRadius = calcRadius();

			int minWeightIndex = 0;
			for (int i = edges.length; i >= 0; i--) {
				minWeightIndex = 0;
				for (int j = i - 1; j >= 0; j--) {
					// if(edges[i].getStart().edges.get(j))
					if (edges[minWeightIndex].getWeight() > edges[j].getWeight()) {
						minWeightIndex = j;
					}
				}
				edges[minWeightIndex].setEdgeColor(new Color(255, 255, 102));
			}
			repaint();
		}

		private void nextDayClicked() {
			for (int i = 0; i < driver.length; i++) {
				driver[i].setSkill(driver[i].getSkill() + 1);
			}
		}

		private int calcRadius() {
			return 360 / driver.length;
		}

		// to calculate angle between two points in degrees
		private double calcAngle(Position p1, Position p2) {
			return Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()) * 180 / Math.PI;
		}

	}
}
