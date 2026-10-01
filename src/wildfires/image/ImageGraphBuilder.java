package wildfires.image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import wildfires.graph.GridGraph;
import wildfires.model.BlockNode;
import wildfires.model.TerrainClassifier;
import wildfires.model.TerrainType;

public class ImageGraphBuilder {

    public static GridGraph buildFromImage(File imageFile, int targetRows, int targetCols) throws Exception {
        BufferedImage img = ImageIO.read(imageFile);
        if (img == null) {
            throw new IllegalArgumentException("Could not read image: " + imageFile.getName());
        }

        int imgW = img.getWidth();
        int imgH = img.getHeight();

        // Sample the image into a grid
        int rows = targetRows > 0 ? targetRows : Math.min(100, imgH);
        int cols = targetCols > 0 ? targetCols : Math.min(100, imgW);

        GridGraph graph = new GridGraph(rows, cols);

        double cellW = (double) imgW / cols;
        double cellH = (double) imgH / rows;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = (int) (c * cellW + cellW / 2);
                int y = (int) (r * cellH + cellH / 2);
                x = Math.min(x, imgW - 1);
                y = Math.min(y, imgH - 1);

                int rgb = img.getRGB(x, y);
                TerrainType type = TerrainClassifier.classify(rgb);
                BlockNode node = new BlockNode(r, c, type);
                graph.setNode(r, c, node);
            }
        }

        graph.buildNeighborEdges();
        return graph;
    }

    public static GridGraph buildDemoGrid(int rows, int cols) {
        GridGraph graph = new GridGraph(rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                TerrainType type;
                // Simple procedural demo terrain
                if (r < 2 || c < 2 || r > rows - 3 || c > cols - 3) {
                    type = TerrainType.ROCK;
                } else if ((r + c) % 7 == 0) {
                    type = TerrainType.WATER;
                } else if ((r * c) % 5 == 0) {
                    type = TerrainType.TREE;
                } else if ((r + c) % 3 == 0) {
                    type = TerrainType.ROAD;
                } else {
                    type = TerrainType.GRASS;
                }
                graph.setNode(r, c, new BlockNode(r, c, type));
            }
        }
        graph.buildNeighborEdges();
        return graph;
    }
}
