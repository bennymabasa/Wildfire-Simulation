package wildfires.pathFinding;

import java.util.Comparator;

import wildfires.dataStructures.Entry;
import wildfires.dataStructures.MyArrayList;
import wildfires.dataStructures.MyPriorityQueue;
import wildfires.graph.GridGraph;
import wildfires.model.BlockNode;
import wildfires.model.Edge;
import wildfires.model.FireState;

public class PathFinder {
	/*
	 * Implementation of Dijkstra's Algorithm using a priority queue, 
	 * with key = edge weight + distance between both nodes, low key = high priority 
	 */
	private GridGraph graph;

	public PathFinder(GridGraph graph) {
		if(graph ==null)
			throw new RuntimeException("The graph cannot be null!");
		this.graph = graph;
	}
	
	
	public MyArrayList<BlockNode> findSafestPath(BlockNode source, BlockNode target){
		MyArrayList<BlockNode> nodes = graph.getNodes();
		
		for(int i=0;i<nodes.size();i++) {
			nodes.get(i).resetPathState();
		}
		
		MyPriorityQueue<Double,BlockNode> pq = new MyPriorityQueue<Double,BlockNode>(Comparator.naturalOrder());
		
		source.setDistance(0.0);
		pq.insert(0.0, source);
		
		BlockNode current = null;
		while(!pq.isEmpty()) {
			Entry<Double, BlockNode> entry = pq.removeMin();
			current = entry.getValue();
			
			if(current == target) break;
			
			MyArrayList<Edge> edges = current.getEdges();
			for(int i=0; i<edges.size(); i++) {
				Edge e = edges.get(i);
				BlockNode neighbor = e.getTo();
				
				// Prefer safer (non-burning) paths
				double extra = 0.0;
				if(neighbor.getFireState() == FireState.BURNING) extra = 50.0;
				if(neighbor.getFireState() == FireState.BURNED) extra = 20.0;
				
				double newDist = current.getDistance() + e.getWeight() + extra;
				if(newDist < neighbor.getDistance()) {
					neighbor.setDistance(newDist);
					neighbor.setPrevious(current);
					pq.insert(newDist, neighbor);
				}
			}
		}
		
		// Reconstruct path
		MyArrayList<BlockNode> path = new MyArrayList<>();
		if(target.getPrevious() != null || source == target) {
			BlockNode n = target;
			while(n != null) {
				path.add(0, n); // insert at front
				n = n.getPrevious();
			}
		}
		return path;
	}
}
