package main;

import domain.entities.player.Player;
import domain.levels.LevelManager;
import ui.GamePanel;
import ui.GameWindow;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 60;
    private final int UPS = 200;

    private Player player;
    private LevelManager levelManager;

    public final static int TILE_DEFAULT_SIZE = 8;
    public final static float SCALE = 8f;
    public final static int TILES_PER_ROW = 26;
    public final static int TILES_PER_COLUMN = 14;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_PER_ROW * TILE_SIZE;
    public final static int GAME_HEIGHT = TILES_PER_COLUMN * TILE_SIZE;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        player = new Player(TILE_SIZE, 0);
        levelManager = new LevelManager();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaF = 0;
        double deltaU = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;

            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "\t|\tUPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    public Player getPlayer() {
        return player;
    }

    public void windowLostFocus() {
        player.resetDirBooleans();
    }
}
