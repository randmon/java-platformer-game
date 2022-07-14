package domain.levels;

public class Level {
    private final int[][] level;

    public Level(int[][] level) {
        this.level = level;
    }

    public int getTile(int x, int y) {
        return level[y][x];
    }
}
