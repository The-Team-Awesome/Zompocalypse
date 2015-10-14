package zompocalypse.gameworld.characters;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

import zompocalypse.gameworld.world.Floor;

/**
 * A class for the future implementation of A* Search algorithm
 *
 * @author Kieran Mckay, 300276166
 */
public class PathFinder {
	private Set<Node> visited = new HashSet<Node>();
	private Stack<Floor> path = new Stack<Floor>();

	private PriorityBlockingQueue<AStarNode> fringe;

	private Node end;
	private double estimate;
	private AStarNode data;

	public PathFinder(Node start, Node end) {
		this.end = end;

		estimate = estimate(start);
		fringe = new PriorityBlockingQueue<AStarNode>();

		data = new AStarNode(start, null, 0, estimate);
		fringe.add(data);
		findPath();
	}

	public void findPath() {
		while (!fringe.isEmpty()) {
			data = fringe.poll();
			Node current = data.getNode();

			if (!current.isVisited()) {
				current.setVisited(true);
				current.setPathFrom(data.getFrom());
				current.setCost(data.getCostToHere());
				visited.add(current);

				if (current.equals(end)) {
					Node temp = current;

					while (temp.getPathFrom() != null) {
						path.push(getConnectingFloor(temp, temp.getPathFrom()));
						temp = temp.getPathFrom();
					}
					break;
				}
			/*
				for (Floor exit : current.floors) {
					Node neigh = exit.end;
					if (!neigh.isVisited()) {
						double costToNeigh = current.getCost() + exit.length;
						double estTotal = costToNeigh + estimate(neigh);
						data = new AStarNode(neigh, current, costToNeigh,
								estTotal);
						fringe.add(data);
					}
				}
				*/
			}
		}
	}

	public Floor getConnectingFloor(Node start, Node end) {
		for (Floor exit : start.floors) {
		//	if (exit.end.equals(end)) {
				return exit;
		//	}
		}
		return null;
	}

	public double estimate(Node current) {
		//int xdif = end.
		//return end.location.distance(current.location);
				return 0;
	}

	public Set<Node> getVisited() {
		return visited;
	}

	public Stack<Floor> getPath() {
		return path;
	}

	private class AStarNode implements Comparable<AStarNode> {
		private Node node;
		private Node from;
		private double costToHere;
		private double totEstCost;

		public AStarNode(Node node, Node from, double costToHere,
				double totEstCost) {
			this.node = node;
			this.from = from;
			this.costToHere = costToHere;
			this.totEstCost = totEstCost;
		}

		public Node getNode() {
			return node;
		}

		public Node getFrom() {
			return from;
		}

		public double getCostToHere() {
			return costToHere;
		}

		public double getTotEstCost() {
			return totEstCost;
		}

		@Override
		public int compareTo(AStarNode o) {
			if (this.getTotEstCost() < o.getTotEstCost()) {
				return -1;
			}
			if (this.getTotEstCost() > o.getTotEstCost()) {
				return 1;
			}
			return 0;
		}
	}
}