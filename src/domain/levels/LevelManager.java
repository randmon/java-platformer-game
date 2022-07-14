package domain.levels;

import domain.save.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.*;

public class LevelManager {
    private ArrayList<BufferedImage> levelSprites;
    private final Level levelOne;

    public LevelManager() {
        importSprites();
        levelOne = new Level(LoadSave.getLevel());
    }

    private void importSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprites = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 8; j++)  {
                levelSprites.add(img.getSubimage(j * TILE_DEFAULT_SIZE, i * TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE));
            }
        }
        //Last row only has 5 tiles
        for (int i = 0; i < 5; i++) {
            levelSprites.add(img.getSubimage(i * TILE_DEFAULT_SIZE, 11 * TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE));
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < TILES_PER_COLUMN; i++) {
            for (int j = 0; j < TILES_PER_ROW; j++) {
                int tile = levelOne.getTile(j, i);
                if (tile >= 93) {
                    // draw image flipped horizontally
                    g.drawImage(levelSprites.get(tile - 93), j * TILE_SIZE + TILE_SIZE, i * TILE_SIZE, -TILE_SIZE, TILE_SIZE, null);
                } else {
                    g.drawImage(levelSprites.get(tile), j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }

    public void update() {

    }
}
