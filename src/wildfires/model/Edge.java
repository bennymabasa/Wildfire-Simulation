package wildfires.model;

public class Edge {
    private final BlockNode from;
    private final BlockNode to;
    private final double weight;

    public Edge(BlockNode from, BlockNode to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public BlockNode getFrom() { return from; }
    public BlockNode getTo() { return to; }
    public double getWeight() { return weight; }

    @Override
    public String toString() {
        return "Edge{" + from.getRow() + "," + from.getCol() + " -> " +
               to.getRow() + "," + to.getCol() + ", w=" + weight + "}";
    }
}
