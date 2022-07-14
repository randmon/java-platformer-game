package domain.entities.player;

import domain.entities.Entity;
import domain.entities.EntityStatus;
import domain.save.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static main.Game.TILE_SIZE;

public class Player extends Entity {
    private EntityStatus status;
    private boolean left, right, up, down, attacking;
    private BufferedImage[][] animations;
    private int animationTick, animationFrame, animationDelay = 25;
    private float speed = 0.1f * SCALE;

    public Player(float x, float y) {
        super(x, y);
        setStatus(EntityStatus.IDLE);

        loadAnimation();
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public void update() {
        updatePosition();
        updateAnimation();
        setAnimation();
    }

    public void render(Graphics g) {
        BufferedImage image = animations[status.ordinal()][animationFrame];
        g.drawImage(image, (int) x, (int) y, (int) (TILE_SIZE * 2.5), (int) (TILE_SIZE * 2.5), null);
    }

    private void loadAnimation() {
        BufferedImage image = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                animations[i][j] = image.getSubimage(j * 48, i * 48, 48, 48);
            }
        }
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationDelay) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= status.frames) {
                animationFrame = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        EntityStatus prevStatus = status;

        if (left || right || up || down) {
            status = EntityStatus.WALKING;
        } else {
            status = EntityStatus.IDLE;
        }

        if (attacking) {
            status = EntityStatus.ATTACK1;
        }

        if (status != prevStatus) {
            animationFrame = 0;
        }
    }

    private void updatePosition() {
        if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }
        if (up) {
            y -= speed;
        }
        if (down) {
            y += speed;
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void resetDirBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
