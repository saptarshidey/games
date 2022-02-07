package com.saptarshi.games.snake.frontend;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.saptarshi.games.snake.backend.Direction;
import com.saptarshi.games.snake.backend.Snake;
import com.saptarshi.games.snake.backend.Apple;

public class GameBoard extends JPanel implements ActionListener, KeyListener {

    private final static int MARGIN = 4;

    private final Dimension screenDimension;
    private final int gridSize;
    private final int maxDelay;
    private final Snake snake;
    private final Apple[] apples;

    private int applesEaten;
    private int hiScore;
    private GameStatus status;
    private Timer timer;

    public enum GameStatus { RUNNING, PAUSED, OVER }

    public GameBoard(Dimension dim, int gridSize, int maxApples, int maxDelay) {
        this.screenDimension = dim;
        this.gridSize = gridSize;
        this.maxDelay = maxDelay;

        snake = new Snake(this, Color.yellow, Color.orange, MARGIN);
        apples = new Apple[maxApples];
        for (int i = 0; i < apples.length; i++) {
            apples[i] = new Apple(this, Color.red, MARGIN);
        }

        this.setPreferredSize(dim);
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    public Dimension getScreenDimension() {
        return screenDimension;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Apple[] getApples() {
        return apples;
    }

    public void startGame() {
        applesEaten = 0;
        status = GameStatus.RUNNING;

        for (Apple apple : apples) apple.initLocation();
        snake.reborn();
        snake.initLocation();

        if (timer == null) {    // first run
            timer = new Timer(maxDelay, this);
            timer.start();
        }
        else {  // restart game
            timer.setDelay(maxDelay);   // fall back to the initial delay
            timer.restart();
        }
    }

    public void restock(int apple) {
        Apple newApple = new Apple(this, Color.red, MARGIN);
        newApple.initLocation();
        apples[apple] = newApple;
    }

    public void accelerate(int percentage) {
        timer.setDelay((int)((100 - percentage)/100.0 * timer.getDelay()));   // increase speed by percentage
    }

    public void paintBoard(Graphics g) {
        int gridSize = getGridSize();
        int boardWidth = screenDimension.width;
        int boardHeight = screenDimension.height;

        g.setColor(Color.gray);
        for (int x = 1; x < boardWidth / gridSize; x++) {
            g.drawLine(x * gridSize, 0, x * gridSize, boardHeight);
        }
        for (int y = 1; y < boardHeight / gridSize; y++) {
            g.drawLine(0, y * gridSize, boardWidth, y * gridSize);
        }

        g.setColor(Color.pink);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics metrics = g.getFontMetrics();

        String hiScoreText = "Hi Score: " + hiScore;
        g.drawString(hiScoreText, 10, metrics.getHeight());

        String scoreText = "Score: " + applesEaten;
        g.drawString(scoreText, boardWidth - metrics.stringWidth(scoreText) - 10, metrics.getHeight());
    }

    public void paintGameStatus(GameStatus status, Graphics g) {
        int boardWidth = screenDimension.width;
        int boardHeight = screenDimension.height;

        switch (status) {
            case PAUSED -> {
                String text = "Game Paused";
                g.setColor(Color.orange);
                g.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics metrics = g.getFontMetrics();
                g.drawString(text, (boardWidth - metrics.stringWidth(text)) / 2, metrics.getHeight());
            }
            case OVER -> {
                String text = "Game Over. Press ENTER to restart";
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics metrics = g.getFontMetrics();
                g.drawString(text, (boardWidth - metrics.stringWidth(text)) / 2, (boardHeight - metrics.getHeight()) / 2);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (status != GameStatus.OVER) {    // game is running or paused
            paintBoard(g);
            for (Apple apple : apples) apple.paint(g);
            snake.paint(g);
            if (status == GameStatus.PAUSED) paintGameStatus(status, g);
        }
        else {  // game over
            paintGameStatus(status, g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status == GameStatus.RUNNING) {
            snake.move();

            int appleForLunch = snake.hadLunch();
            if (appleForLunch != -1) {   // ate an apple
                applesEaten++;
                restock(appleForLunch);
                snake.grow();
                accelerate(1);
            }

            if (snake.collision()) {
                hiScore = Math.max(applesEaten, hiScore);
                status = GameStatus.OVER;
                timer.stop();
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction snakeDir = snake.getDirection();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> { if (snakeDir != Direction.RIGHT) snake.setDirection(Direction.LEFT); }
            case KeyEvent.VK_RIGHT -> { if (snakeDir != Direction.LEFT) snake.setDirection(Direction.RIGHT); }
            case KeyEvent.VK_UP -> { if (snakeDir != Direction.DOWN) snake.setDirection(Direction.UP); }
            case KeyEvent.VK_DOWN -> { if (snakeDir != Direction.UP) snake.setDirection(Direction.DOWN); }
            case KeyEvent.VK_ENTER -> {
                if (status == GameStatus.OVER) startGame();
            }
            case KeyEvent.VK_P -> {
                if (status == GameStatus.RUNNING) {
                    status = GameStatus.PAUSED;
                    return;
                }
                if (status == GameStatus.PAUSED)
                    status = GameStatus.RUNNING;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
