package path.route;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Node;
import util.Point;

/**
 * Visualises a graph by drawing both the nodes and the connections between them in a frame representing the graph space.
 */
public class Visualiser extends Frame {
	/** The functional width of this frame in pixels. */
	public static final int PIXEL_WIDTH = 500;
	/** The functional height of this frame in pixels. */
	public static final int PIXEL_HEIGHT = 500;
	/** The radius in pixels of a point. */
	public static final int POINT_RADIUS = 5;
	/** The extra width, in pixels, added by the left side of a Windows frame. */
	public static final int WIDTH_OFFSET = 8;
	/** The extra height, in pixels, added by the top of a Windows frame. */
	public static final int HEIGHT_OFFSET = 30;
	
	/** The width of the graph space, in the same units as used in the elements of {@link #locations}. */
	private int width;
	/** The height of the graph space, in the same units as used in the elements of {@link #locations}. */
	private int height;
	/** A list of nodes in the graph, containing references to the connected nodes. */
	private List<? extends Location> locations;
	
	/**
	 * Initialises the visualiser.
	 * @param width The width of the graph space, in the same units as used in the elements of {@link #locations}.
	 * @param height The height of the graph space, in the same units as used in the elements of {@link #locations}.
	 * @param locations A list of nodes in the graph, containing references to the connected nodes.
	 */
	public Visualiser(int width, int height, List<Location> locations) {
		super("Graph");
		this.width = width;
		this.height = height;
		this.locations = locations;
		// Set the window size to the functional size plus the offset created by Windows frames.
		setSize(PIXEL_WIDTH + WIDTH_OFFSET + WIDTH_OFFSET, PIXEL_HEIGHT + HEIGHT_OFFSET + 8);
		// Close the application when closing the window.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		// Draw lines
		g.setColor(Color.BLACK);
		Set<Location> alreadyVisited = new HashSet<>();
		for (Location location : locations) {
			alreadyVisited.add(location);
			for (Node child : location.getChildren()) {
				if (alreadyVisited.contains(child)) {
					continue;
				}
				
				Location childLocation = (Location) child;
				drawLine(g, location.getPoint(), childLocation.getPoint());
			}
		}
		
		// Draw points
		g.setColor(Color.GRAY);
		for (Location location : locations) {
			drawPoint(g, location.getPoint());
		}
	}
	
	/**
	 * Draws a node from the graph in the window.
	 * @param graphics The {@link Graphics} object used to draw shapes.
	 * @param point The point representing the location of the node in the graph space.
	 */
	protected void drawPoint(Graphics graphics, Point point) {
		graphics.fillOval(
				(int) ((point.determineCoordinate(0).doubleValue() / width) * PIXEL_WIDTH) - POINT_RADIUS + WIDTH_OFFSET,
				(int) ((point.determineCoordinate(1).doubleValue() / height) * PIXEL_HEIGHT) - POINT_RADIUS + HEIGHT_OFFSET,
				POINT_RADIUS * 2, POINT_RADIUS * 2
		);
	}
	
	/**
	 * Draws a line between two nodes from the graph in the window.
	 * @param graphics The {@link Graphics} object used to draw shapes.
	 * @param fromPoint The point representing the location of the first node in the graph space.
	 * @param toPoint The point representing the location of the second node in the graph space.
	 */
	protected void drawLine(Graphics graphics, Point fromPoint, Point toPoint) {
		graphics.drawLine(
				(int) ((fromPoint.determineCoordinate(0).doubleValue() / width) * PIXEL_WIDTH) + WIDTH_OFFSET,
				(int) ((fromPoint.determineCoordinate(1).doubleValue() / height) * PIXEL_HEIGHT) + HEIGHT_OFFSET,
				(int) ((toPoint.determineCoordinate(0).doubleValue() / width) * PIXEL_WIDTH) + WIDTH_OFFSET,
				(int) ((toPoint.determineCoordinate(1).doubleValue() / height) * PIXEL_HEIGHT) + HEIGHT_OFFSET
		);
	}
}
