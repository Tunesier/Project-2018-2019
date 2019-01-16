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

import Graph.Client;
import Graph.Edge;
import Model.Driver;
import Model.Solver;

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
	public static final Color[] colors = { Color.GREEN, Color.PINK, Color.ORANGE, Color.LIGHT_GRAY };
	public static int day = 1;

	public static int MinOverAllWeight = 0;

	public static Client[] clients;
	public static Edge[] edges;
	public static Driver[] drivers;
	public static Solver solver = new Solver();

	Panel panel;
	// JLabel l1;

	public GUI(Client[] clients, Edge[] edges, Driver[] drivers) {

		this.clients = clients;
		this.edges = edges;
		this.drivers = drivers;
		initGui();
		initPanel();
		initButtons();
		this.setContentPane(panel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void initGui() {
		setTitle("TSP-Routing-Solver");
		setSize(GUI_WIDTH, GUI_HEIGHT);
		setBackground(new Color(51, 0, 102));
	}

	private void initPanel() {
		panel = new Panel();
		// this let us freely add JButtons in the area
		panel.setLayout(null);
	}

	private void initButtons() {
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
				solver.solveClicked();
				repaint();

			}
		});
		// Next Day Button
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
				solver.nextDayClicked();
				repaint();
			}
		});
		// arrange Drivers to distination button
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton arrangeDrivers = new JButton("Arrange Drivers");
		arrangeDrivers.setBounds(0, 80, 100, 40);
		arrangeDrivers.setFocusPainted(false);
		arrangeDrivers.setBackground(new Color(59, 89, 182));
		arrangeDrivers.setForeground(new Color(255, 255, 255));
		arrangeDrivers.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(arrangeDrivers);

		arrangeDrivers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solver.arrangeDriversToDistinationClicked();
				repaint();
			}
		});
	}

	private class thread extends Thread {
		@Override
		public void run() {

		}
	}

	private static class Panel extends JPanel implements ActionListener {
		// Timer loopTimer;

		static final int STROKE_SMALL_SIZE = 4;
		static final int STROKE_BIG_SIZE = 16;

		public Panel() {
			// loopTimer = new Timer(10, this);
			// loopTimer.start();
		}

		@Override
		public void paint(Graphics g) {
			// Setting drawing parameters
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			g2d.drawString("Day: " + day, 10, 140);
			for (int i = 0; i < edges.length; i++) {
				drawEdge(g2d, i);
				drawVertices(g2d, i);
			}

		}

		private void drawEdge(Graphics2D g2d, int index) {
			g2d.setStroke(new BasicStroke(STROKE_SMALL_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			g2d.setColor(edges[index].getEdgeColor());
			g2d.drawLine(edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY(),
					edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY());
			g2d.drawString(" " + edges[index].getId(),
					(Math.abs(edges[index].getStart().getPos().getX() - edges[index].getEnd().getPos().getX()) / 2
							+ Math.min(edges[index].getStart().getPos().getX(), edges[index].getEnd().getPos().getX())),
					(Math.abs(edges[index].getStart().getPos().getY() - edges[index].getEnd().getPos().getY()) / 2
							+ Math.min(edges[index].getStart().getPos().getY(),
									edges[index].getEnd().getPos().getY())));

		}

		private void drawVertices(Graphics2D g2d, int index) {
			g2d.setStroke(new BasicStroke(STROKE_BIG_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			g2d.setColor(edges[index].getStart().getColor());
			g2d.drawLine(edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY(),
					edges[index].getStart().getPos().getX(), edges[index].getStart().getPos().getY());
			g2d.drawString(
					"(" + edges[index].getStart().getPos().getX() + ", " + edges[index].getStart().getPos().getY()
							+ ")",
					edges[index].getStart().getPos().getX() + 5, edges[index].getStart().getPos().getY() - 5);

			g2d.setColor(edges[index].getEnd().getColor());
			g2d.drawLine(edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY(),
					edges[index].getEnd().getPos().getX(), edges[index].getEnd().getPos().getY());
			g2d.drawString(
					"(" + edges[index].getEnd().getPos().getX() + ", " + edges[index].getEnd().getPos().getY() + ")",
					edges[index].getEnd().getPos().getX() + 5, edges[index].getEnd().getPos().getY() - 5);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}

	}
}
