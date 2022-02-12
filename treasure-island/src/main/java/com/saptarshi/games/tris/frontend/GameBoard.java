package com.saptarshi.games.tris.frontend;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;

import com.saptarshi.games.tris.backend.KeyHandler;
import com.saptarshi.games.tris.entity.Player;
import com.saptarshi.games.tris.tile.TileManager;
import com.saptarshi.games.tris.backend.GameConstants;

public final class GameBoard extends JPanel implements Runnable {

    private final Thread gameThread;

    private final KeyHandler keyHandler;
    private final TileManager tileManager;
    private final Player player;

    public GameBoard() {
        keyHandler = new KeyHandler();
        tileManager = new TileManager(this);
        player = new Player(this, keyHandler);

        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        this.gameThread = new Thread(this);
        gameThread.start();
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public Player getPlayer() {
        return player;
    }

    private void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileManager.draw(g2);
        player.draw(g2);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / GameConstants.FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1.0) {
                update();
                repaint();
                delta--;
            }
        }
    }

}
