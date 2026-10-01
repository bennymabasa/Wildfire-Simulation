package wildfires.UI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import wildfires.graph.GridGraph;
import wildfires.model.BlockNode;
import wildfires.model.TerrainType;

public class GraphUI {
    private final Pane layer;
    private GridGraph graph;
    private double cellWidth;
    private double cellHeight;

    public GraphUI(Pane layer) {
        this.layer = layer;
    }

    public void setGraph(GridGraph graph, double viewWidth, double viewHeight) {
        this.graph = graph;
        if (graph != null) {
            this.cellWidth = viewWidth / graph.getCols();
            this.cellHeight = viewHeight / graph.getRows();
        }
    }

    public double getCellWidth() { return cellWidth; }
    public double getCellHeight() { return cellHeight; }

    public void render() {
        layer.getChildren().clear();
        if (graph == null) return;

        for (int r = 0; r < graph.getRows(); r++) {
            for (int c = 0; c < graph.getCols(); c++) {
                BlockNode node = graph.getNode(r, c);
                if (node == null) continue;

                Rectangle rect = new Rectangle(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                rect.setFill(terrainColor(node.getTerrainType()));
                rect.setStroke(Color.rgb(0, 0, 0, 0.15));
                rect.setStrokeWidth(0.5);
                layer.getChildren().add(rect);
            }
        }
    }

    private Color terrainColor(TerrainType type) {
        if (type == null) return Color.MAGENTA;
        switch (type) {
            case WATER: return Color.rgb(30, 144, 255);
            case GRASS: return Color.rgb(124, 252, 0);
            case TREE:  return Color.rgb(34, 139, 34);
            case SOIL:  return Color.rgb(160, 82, 45);
            case ROAD:  return Color.rgb(128, 128, 128);
            case ROCK:  return Color.rgb(105, 105, 105);
            default:    return Color.MAGENTA;
        }
    }
}
