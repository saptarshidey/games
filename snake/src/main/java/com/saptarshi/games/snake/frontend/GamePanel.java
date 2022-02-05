package com.saptarshi.games.snake.frontend;

import java.util.Random;

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

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int screenWidth;
    private final int screenHeight;
    private final int unitSize;
    private final int delay;
    private final int[] snakeX;
    private final int[] snakeY;
    private int snakeSize;
    private final int[] appleX;
    private final int[] appleY;
    private int applesEaten;
    private int hiScore;
    private char direction;
    private Timer timer;
    private final Random rnd;
    private boolean isRunning;

    public GamePanel(int screenWidth, int screenHeight, int unitSize, int delay) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
        this.delay = delay;

        int gameUnits = (screenWidth * screenHeight) / unitSize;
        snakeX = new int[gameUnits];
        snakeY = new int[gameUnits];

        appleX = new int[4];
        appleY = new int[4];

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(this);

        rnd = new Random();

        startGame();
    }

    public void startGame() {
        snakeSize = 2;
        snakeX[0] = unitSize * (snakeSize - 1);
        snakeX[1] = 0;
        snakeY[0] = snakeY[1] = 0;
        applesEaten = 0;
        direction = 'R';
        isRunning = true;

        if (timer == null) {
            timer = new Timer(delay, this);
            timer.start();
        }
        else {
            timer.setDelay(delay);
            timer.restart();
        }

        randomizeApples();
    }

    public void randomizeApples() {
        for (int i = 0; i < appleX.length; i++) {
            appleX[i] = rnd.nextInt(screenWidth / unitSize) * unitSize;
            appleY[i] = rnd.nextInt(screenHeight / unitSize) * unitSize;
        }
    }

    public void randomizeApple(int index) {
        appleX[index] = rnd.nextInt(screenWidth / unitSize) * unitSize;
        appleY[index] = rnd.nextInt(screenHeight / unitSize) * unitSize;
    }

    public void move() {
        for (int i = snakeSize; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'R' -> snakeX[0] = snakeX[0] + unitSize;
            case 'L' -> snakeX[0] = snakeX[0] - unitSize;
            case 'U' -> snakeY[0] = snakeY[0] - unitSize;
            case 'D' -> snakeY[0] = snakeY[0] + unitSize;
        }
    }

    public int eatenApple() {
        int appleNumber = -1;
        for (int i = 0; i < appleX.length; i++) {
            if (snakeX[0] == appleX[i] && snakeY[0] == appleY[i]) {
                appleNumber = i;
                break;
            }
        }
        return appleNumber;
    }

    public boolean collided() {
        // check if head collided with body
        for (int i = snakeSize - 1; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                return true;
            }
        }
        // check if head collided with the walls
        return snakeX[0] < 0 || snakeX[0] > screenWidth || snakeY[0] < 0 || snakeY[0] > screenHeight;
    }

    public void drawGrid(Graphics g) {
        g.setColor(Color.gray);
        for (int x = 1; x < screenWidth / unitSize; x++) {
            g.drawLine(x * unitSize, 0, x * unitSize, screenHeight);
        }

        for (int y = 1; y < screenHeight / unitSize; y++) {
            g.drawLine(0, y * unitSize, screenWidth, y * unitSize);
        }
    }

    public void drawApples(Graphics g) {
        int margin = 6;
        int diameter = unitSize - margin * 2;

        g.setColor(Color.red);
        for (int i = 0; i < appleX.length; i++) {
            g.fillOval(appleX[i] + margin, appleY[i] + margin, diameter, diameter);
        }
    }

    public void drawSnake(Graphics g) {
        for (int i = 0; i < snakeSize; i++) {
            if (i == 0) {   // head
                g.setColor(Color.yellow);
                g.fillOval(snakeX[i], snakeY[i], unitSize, unitSize);

                // eyes
                g.setColor(Color.black);
                switch(direction) {
                    case 'R' -> {
                        g.fillOval(snakeX[i] + (int)(2 * unitSize / 3.0f), snakeY[i] + (int)(unitSize / 3.0f) - 1, 4, 4);
                        g.fillOval(snakeX[i] + (int)(2 * unitSize / 3.0f), snakeY[i] + (int)(2 * unitSize / 3.0f) - 1, 4, 4);
                    }
                    case 'L' -> {
                        g.fillOval(snakeX[i] + (int)(unitSize / 3.0f), snakeY[i] + (int)(unitSize / 3.0f) - 1, 4, 4);
                        g.fillOval(snakeX[i] + (int)(unitSize / 3.0f), snakeY[i] + (int)(2 * unitSize / 3.0f) - 1, 4, 4);
                    }
                    case 'U' -> {
                        g.fillOval(snakeX[i] + (int)(unitSize / 3.0f) - 1, snakeY[i] + (int)(unitSize / 3.0f), 4, 4);
                        g.fillOval(snakeX[i] + (int)(2 * unitSize / 3.0f) - 1, snakeY[i] + (int)(unitSize / 3.0f), 4, 4);
                    }
                    case 'D' -> {
                        g.fillOval(snakeX[i] + (int)(unitSize / 3.0f) - 1, snakeY[i] + (int)(2 * unitSize / 3.0f), 4, 4);
                        g.fillOval(snakeX[i] + (int)(2 * unitSize / 3.0f) - 1, snakeY[i] + (int)(2 * unitSize / 3.0f), 4, 4);
                    }
                }

                // tongue
                g.setColor(Color.red);
                switch(direction) {
                    case 'R' -> g.drawLine(snakeX[i] + unitSize, snakeY[i] + (int)(unitSize / 2.0f), snakeX[i] + unitSize + 8, snakeY[i] + (int)(unitSize / 2.0f));
                    case 'L' -> g.drawLine(snakeX[i], snakeY[i] + (int)(unitSize / 2.0f), snakeX[i] - 8, snakeY[i] + (int)(unitSize / 2.0f));
                    case 'U' -> g.drawLine(snakeX[i] + (int)(unitSize / 2.0f), snakeY[i], snakeX[i] + (int)(unitSize / 2.0f), snakeY[i] - 8);
                    case 'D' -> g.drawLine(snakeX[i] + (int)(unitSize / 2.0f), snakeY[i] + unitSize, snakeX[i] + (int)(unitSize / 2.0f), snakeY[i] + unitSize + 8);
                }
            }
            else {
                g.setColor(Color.orange);
                if (i == snakeSize - 1) {   // tail
                    g.fillOval(snakeX[i] + 8, snakeY[i] + 8, unitSize - 16, unitSize - 16);
                }
                else if (i == snakeSize - 2) {  // 1 position before tail
                    g.fillOval(snakeX[i] + 6, snakeY[i] + 6, unitSize - 12, unitSize - 12);
                }
                else {  // rest of the body
                    g.fillOval(snakeX[i] + 4, snakeY[i] + 4, unitSize - 8, unitSize - 8);
                }
            }
        }
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.pink);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics metrics = g.getFontMetrics();

        String hiScoreText = "Hi Score: " + hiScore;
        g.drawString(hiScoreText, 10, metrics.getHeight());

        String scoreText = "Score: " + applesEaten;
        g.drawString(scoreText, screenWidth - metrics.stringWidth(scoreText) - 10, metrics.getHeight());
    }

    public void gameOver(Graphics g) {
        drawScore(g);
        String text = "Game Over. Press ENTER to restart";
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, (screenWidth - metrics.stringWidth(text)) / 2, (screenHeight - metrics.getHeight()) / 2);
    }

    public void gamePaused(Graphics g) {
        String text = "Game Paused";
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, (screenWidth - metrics.stringWidth(text)) / 2, metrics.getHeight());
    }

    public void draw(Graphics g) {
        if (timer.isRunning()) {
            drawGrid(g);
            drawApples(g);
            drawSnake(g);
            drawScore(g);
            if (!isRunning) gamePaused(g);
        }
        else {
            gameOver(g);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            int appleNumber = eatenApple();
            if (appleNumber >= 0) {
                applesEaten++;
                snakeSize++;
                randomizeApple(appleNumber);
                timer.setDelay(timer.getDelay() - 2);
            }
            if (collided()) {
                hiScore = Math.max(applesEaten, hiScore);
                isRunning = false;
                timer.stop();
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> { if (direction != 'R') direction = 'L'; }
            case KeyEvent.VK_RIGHT -> { if (direction != 'L') direction = 'R'; }
            case KeyEvent.VK_UP -> { if (direction != 'D') direction = 'U'; }
            case KeyEvent.VK_DOWN -> { if (direction != 'U') direction = 'D'; }
            case KeyEvent.VK_ENTER -> { if (!timer.isRunning() && !isRunning) startGame(); }
            case KeyEvent.VK_P -> isRunning = !isRunning;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
