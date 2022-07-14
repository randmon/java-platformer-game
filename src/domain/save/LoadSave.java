package domain.save;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.Game.TILES_PER_COLUMN;
import static main.Game.TILES_PER_ROW;

public class LoadSave {
    public static final String PLAYER_ATLAS = "/images/graverobber_spritesheet.png";
    public static final String LEVEL_ATLAS = "/images/cavesofgallet_tiles.png";
    public static final String LEVEL_ONE_DATA = "/maps/level_one_data.png";

    public static BufferedImage getSpriteAtlas(String file) {
        InputStream iStream = LoadSave.class.getResourceAsStream(file);
        try {
            return ImageIO.read(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int[][] getLevel() {
        int[][] level = new int[TILES_PER_COLUMN][TILES_PER_ROW];
        BufferedImage img = LoadSave.getSpriteAtlas(LEVEL_ONE_DATA);

        for (int i = 0; i < TILES_PER_COLUMN; i++) {
            for (int j = 0; j < TILES_PER_ROW; j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                int direction = color.getBlue();
                if (direction == 255) {
                    value = 93 + value;
                }
                level[i][j] = value;
            }
        }
          return level;
    }
}
