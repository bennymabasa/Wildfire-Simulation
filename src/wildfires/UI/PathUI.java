package wildfires.UI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import wildfires.dataStructures.MyArrayList;
import wildfires.model.BlockNode;

public class PathUI {
    private final Pane layer;
    private final double cellWidth;
    private final double cellHeight;

    public PathUI(Pane layer, double cellWidth, double cellHeight) {
        this.layer = layer;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void clear() {
        layer.getChildren().clear();
    }

    public void renderPath(MyArrayList<BlockNode> path) {
        clear();
        if (path == null || path.size() < 2) return;

        for (int i = 0; i < path.size() - 1; i++) {
            BlockNode a = path.get(i);
            BlockNode b = path.get(i + 1);

            double x1 = a.getCol() * cellWidth + cellWidth / 2;
            double y1 = a.getRow() * cellHeight + cellHeight / 2;
            double x2 = b.getCol() * cellWidth + cellWidth / 2;
            double y2 = b.getRow() * cellHeight + cellHeight / 2;

            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(Color.CYAN);
            line.setStrokeWidth(3);
            layer.getChildren().add(line);
        }

        // Start and end markers
        BlockNode start = path.get(0);
        BlockNode end = path.get(path.size() - 1);

        Circle startCircle = new Circle(
            start.getCol() * cellWidth + cellWidth / 2,
            start.getRow() * cellHeight + cellHeight / 2,
            Math.min(cellWidth, cellHeight) / 3
        );
        startCircle.setFill(Color.LIMEGREEN);

        Circle endCircle = new Circle(
            end.getCol() * cellWidth + cellWidth / 2,
            end.getRow() * cellHeight + cellHeight / 2,
            Math.min(cellWidth, cellHeight) / 3
        );
        endCircle.setFill(Color.DODGERBLUE);

        layer.getChildren().addAll(startCircle, endCircle);
    }
}
