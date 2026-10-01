package wildfires.UI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import wildfires.graph.GridGraph;
import wildfires.model.BlockNode;
import wildfires.model.FireState;

public class FireUI {
    private final Pane layer;
    private final GridGraph graph;
    private final double cellWidth;
    private final double cellHeight;

    public FireUI(Pane layer, GridGraph graph, double cellWidth, double cellHeight) {
        this.layer = layer;
        this.graph = graph;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void clear() {
        layer.getChildren().clear();
    }

    public void render() {
        clear();
        if (graph == null) return;

        for (int r = 0; r < graph.getRows(); r++) {
            for (int c = 0; c < graph.getCols(); c++) {
                BlockNode node = graph.getNode(r, c);
                if (node == null) continue;

                FireState state = node.getFireState();
                if (state == FireState.SAFE) continue;

                Rectangle rect = new Rectangle(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                if (state == FireState.BURNING) {
                    rect.setFill(Color.rgb(255, 69, 0, 0.7));
                } else if (state == FireState.BURNED) {
                    rect.setFill(Color.rgb(40, 40, 40, 0.6));
                }
                layer.getChildren().add(rect);
            }
        }
    }
}
