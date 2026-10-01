package wildfires.graph;

import wildfires.dataStructures.MyArrayList;
import wildfires.model.BlockNode;
import wildfires.model.Edge;
import wildfires.model.TerrainType;

public class GridGraph {
    private final int rows;
    private final int cols;
    private final BlockNode[][] nodes;
    private final MyArrayList<Edge> edges;

    public GridGraph(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and cols must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.nodes = new BlockNode[rows][cols];
        this.edges = new MyArrayList<>();
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void setNode(int row, int col, BlockNode node) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Invalid position: " + row + "," + col);
        }
        nodes[row][col] = node;
    }

    public BlockNode getNode(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return nodes[row][col];
    }

    public void addEdge(BlockNode from, BlockNode to, double weight) {
        if (from == null || to == null) return;
        Edge e = new Edge(from, to, weight);
        edges.add(e);
        from.addEdge(e);
    }

    public MyArrayList<Edge> getEdges() {
        return edges;
    }

    public MyArrayList<BlockNode> getNodes() {
        MyArrayList<BlockNode> list = new MyArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (nodes[r][c] != null) {
                    list.add(nodes[r][c]);
                }
            }
        }
        return list;
    }

    public void buildNeighborEdges() {
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                BlockNode current = nodes[r][c];
                if (current == null) continue;
                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    if (isValidPosition(nr, nc) && nodes[nr][nc] != null) {
                        double weight = computeWeight(current, nodes[nr][nc]);
                        addEdge(current, nodes[nr][nc], weight);
                    }
                }
            }
        }
    }

    private double computeWeight(BlockNode a, BlockNode b) {
        // Higher risk terrain = higher weight (harder / more dangerous to traverse)
        double base = 1.0;
        TerrainType t = b.getTerrainType();
        switch (t) {
            case WATER: return 1000.0; // almost impassable
            case ROCK:  return 5.0;
            case ROAD:  return 0.5;
            case SOIL:  return 1.0;
            case GRASS: return 1.5;
            case TREE:  return 2.5;
            default:    return base;
        }
    }
}
