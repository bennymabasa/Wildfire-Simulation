package wildfires.model;

import java.awt.Color;

public class TerrainClassifier {

    public static TerrainType classify(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // Simple color-based classification
        // Water - blue dominant
        if (b > r + 30 && b > g + 20) {
            return TerrainType.WATER;
        }
        // Trees / forest - dark green
        if (g > r + 20 && g > b + 20 && g < 150) {
            return TerrainType.TREE;
        }
        // Grass - bright green / yellow-green
        if (g > r && g > b && g > 100) {
            return TerrainType.GRASS;
        }
        // Road / asphalt - gray
        if (Math.abs(r - g) < 20 && Math.abs(g - b) < 20 && r > 80 && r < 180) {
            return TerrainType.ROAD;
        }
        // Rock - dark gray / brown
        if (r < 100 && g < 100 && b < 100) {
            return TerrainType.ROCK;
        }
        // Soil / dirt - brownish
        if (r > g && r > b && r > 80) {
            return TerrainType.SOIL;
        }

        return TerrainType.UNKNOWN;
    }

    public static Color getDisplayColor(TerrainType type) {
        if (type == null) return Color.MAGENTA;
        switch (type) {
            case WATER: return new Color(30, 144, 255);
            case GRASS: return new Color(124, 252, 0);
            case TREE:  return new Color(34, 139, 34);
            case SOIL:  return new Color(160, 82, 45);
            case ROAD:  return new Color(128, 128, 128);
            case ROCK:  return new Color(105, 105, 105);
            default:    return Color.MAGENTA;
        }
    }
}
