package wildfires.model;

public class TerrainRules {

    public static boolean isBurnable(TerrainType type) {
        if (type == null) return false;
        switch (type) {
            case GRASS:
            case TREE:
            case SOIL:
                return true;
            case WATER:
            case ROCK:
            case ROAD:
            case UNKNOWN:
            default:
                return false;
        }
    }

    public static int getBurnDuration(TerrainType type) {
        if (type == null) return 0;
        switch (type) {
            case GRASS: return 2;
            case TREE:  return 5;
            case SOIL:  return 3;
            default:    return 0;
        }
    }

    public static double getSpreadMultiplier(TerrainType type) {
        if (type == null) return 0.0;
        switch (type) {
            case GRASS: return 1.2;
            case TREE:  return 1.5;
            case SOIL:  return 0.8;
            default:    return 0.0;
        }
    }
}
