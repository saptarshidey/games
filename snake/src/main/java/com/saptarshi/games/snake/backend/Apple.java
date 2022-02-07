package com.saptarshi.games.snake.backend;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

import com.saptarshi.games.snake.frontend.GameBoard;

public final class Apple extends Sprite {

    private final Color color;
    private final Random rnd;

    public Apple(GameBoard game, Color color, int margin) {
        super(game, margin, 1);
        this.color = color;
        this.rnd = new Random();
    }

    @Override
    public void initLocation() {
        int gridSize = getGameBoard().getGridSize();
        int boardWidth = getGameBoard().getScreenDimension().width;
        int boardHeight = getGameBoard().getScreenDimension().height;

        setX(rnd.nextInt(boardWidth / gridSize) * gridSize);
        setY(rnd.nextInt(boardHeight / gridSize) * gridSize);
    }

    @Override
    public void paint(Graphics g) {
        int diameter = getGameBoard().getGridSize() - getMargin() * 2;

        g.setColor(color);
        g.fillOval(getX() + getMargin(), getY() + getMargin(), diameter, diameter);
    }

}
