package zompocalypse.gameworld.characters;

import java.util.Collection;
import java.util.HashSet;

import zompocalypse.gameworld.world.Floor;

/**
 * A node for representing attributes required for the future implementation of
 * A* Search algorithm
 *
 * @author Kieran Mckay, 300276166
 */
public class Node {

	public final int nodeID;
	public final Collection<Floor> floors;

	// A* fields
	private double cost = 0;
	private Node pathFrom = null;
	private boolean visited = false;

	public Node(int nodeID) {
		this.nodeID = nodeID;
		this.floors = new HashSet<Floor>();
	}

	public void addFloor(Floor floor) {
		floors.add(floor);
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Node getPathFrom() {
		return pathFrom;
	}

	public void setPathFrom(Node pathFrom) {
		this.pathFrom = pathFrom;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}