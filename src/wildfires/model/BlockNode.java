package wildfires.model;

import wildfires.dataStructures.MyArrayList;

public class BlockNode {
    private final int row;
    private final int col;
    private TerrainType terrainType;
    private FireState fireState;
    private double distance; // for pathfinding
    private BlockNode previous; // for path reconstruction
    private final MyArrayList<Edge> edges;

    public BlockNode(int row, int col, TerrainType type) {
        this.row = row;
        this.col = col;
        this.terrainType = type != null ? type : TerrainType.UNKNOWN;
        this.fireState = FireState.SAFE;
        this.distance = Double.POSITIVE_INFINITY;
        this.previous = null;
        this.edges = new MyArrayList<>();
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public TerrainType getTerrainType() { return terrainType; }
    public void setTerrainType(TerrainType type) { this.terrainType = type; }

    public FireState getFireState() { return fireState; }
    public void setFireState(FireState state) { this.fireState = state; }

    public double getDistance() { return distance; }
    public void setDistance(double d) { this.distance = d; }

    public BlockNode getPrevious() { return previous; }
    public void setPrevious(BlockNode prev) { this.previous = prev; }

    public MyArrayList<Edge> getEdges() { return edges; }
    public void addEdge(Edge e) { edges.add(e); }

    public void resetPathState() {
        this.distance = Double.POSITIVE_INFINITY;
        this.previous = null;
    }

    public boolean isBurnable() {
        return TerrainRules.isBurnable(terrainType);
    }

    @Override
    public String toString() {
        return "BlockNode[" + row + "," + col + ", " + terrainType + ", " + fireState + "]";
    }
}
