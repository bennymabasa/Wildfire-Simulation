package wildfires.simulation;

import wildfires.dataStructures.Entry;
import wildfires.dataStructures.MyPriorityQueue;
import wildfires.graph.GridGraph;
import wildfires.model.BlockNode;
import wildfires.model.FireState;
import wildfires.model.TerrainRules;

import java.util.Comparator;

public class FireSimulator {

    private final GridGraph graph;
    private final MyPriorityQueue<Integer, BlockNode> eventQueue;
    private final int[][] ignitionTimes;
    private int currentTime;

    public FireSimulator(GridGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        this.graph = graph;
        this.currentTime = 0;
        this.ignitionTimes = new int[graph.getRows()][graph.getCols()];

        for (int r = 0; r < graph.getRows(); r++) {
            for (int c = 0; c < graph.getCols(); c++) {
                ignitionTimes[r][c] = Integer.MAX_VALUE;
                BlockNode node = graph.getNode(r, c);
                if (node != null) {
                    node.setFireState(FireState.SAFE);
                }
            }
        }

        this.eventQueue = new MyPriorityQueue<>(Comparator.naturalOrder());
    }

    public void ignite(BlockNode node) {
        if (node == null || !node.isBurnable()) return;

        int row = node.getRow();
        int col = node.getCol();

        if (ignitionTimes[row][col] > currentTime) {
            ignitionTimes[row][col] = currentTime;
            eventQueue.insert(currentTime, node);
        }
    }

    public void ignite(int row, int col) {
        if (!graph.isValidPosition(row, col)) return;
        ignite(graph.getNode(row, col));
    }

    public boolean step() {
        if (eventQueue.isEmpty()) {
            return false;
        }

        Entry<Integer, BlockNode> event = eventQueue.removeMin();
        if (event == null) return false;

        int eventTime = event.getKey();
        BlockNode current = event.getValue();

        if (current == null) return !eventQueue.isEmpty();

        currentTime = eventTime;

        if (current.getFireState() == FireState.BURNED) {
            return !eventQueue.isEmpty();
        }

        current.setFireState(FireState.BURNING);

        // Schedule burn-out
        int burnDuration = TerrainRules.getBurnDuration(current.getTerrainType());

        // Spread to neighbors
        var edges = current.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            BlockNode neighbor = edges.get(i).getTo();
            if (neighbor == null || !neighbor.isBurnable()) continue;
            if (neighbor.getFireState() != FireState.SAFE) continue;

            int spreadDelay = Math.max(1, (int) (edges.get(i).getWeight() * TerrainRules.getSpreadMultiplier(neighbor.getTerrainType())));
            int igniteTime = currentTime + spreadDelay;

            int nr = neighbor.getRow();
            int nc = neighbor.getCol();

            if (igniteTime < ignitionTimes[nr][nc]) {
                ignitionTimes[nr][nc] = igniteTime;
                eventQueue.insert(igniteTime, neighbor);
            }
        }

        // After burn duration the cell becomes BURNED
        // (simplified: we mark it burned on next relevant step or immediately for demo)
        current.setFireState(FireState.BURNED);

        return !eventQueue.isEmpty();
    }

    public void reset() {
        currentTime = 0;
        while (!eventQueue.isEmpty()) eventQueue.removeMin();
        for (int r = 0; r < graph.getRows(); r++) {
            for (int c = 0; c < graph.getCols(); c++) {
                ignitionTimes[r][c] = Integer.MAX_VALUE;
                BlockNode node = graph.getNode(r, c);
                if (node != null) node.setFireState(FireState.SAFE);
            }
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
