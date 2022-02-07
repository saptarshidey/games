package com.saptarshi.games.snake.backend;

import java.util.ArrayList;
import java.awt.Graphics;

import com.saptarshi.games.snake.frontend.GameBoard;

public sealed abstract class Sprite permits Snake, Apple {

    private final GameBoard game;
    private final ArrayList<Integer> posX;
    private final ArrayList<Integer> posY;
    private final int margin;

    public Sprite(GameBoard game, int margin, int initialParts) {
        this.game = game;
        this.posX = new ArrayList<>(initialParts);
        this.posY = new ArrayList<>(initialParts);
        this.margin = margin;
    }

    public abstract void initLocation();

    public abstract void paint(Graphics g);

    public GameBoard getGameBoard() {
        return game;
    }

    public int getSize() {
        return posX.size();
    }

    public ArrayList<Integer> getXs() {
        return posX;
    }

    public ArrayList<Integer> getYs() {
        return posY;
    }

    public int getX(int index) {
        return posX.get(index);
    }

    public int getY(int index) {
        return posY.get(index);
    }

    public int getX() {
        return posX.get(0);
    }

    public int getY() {
        return posY.get(0);
    }

    public void setX(int index, int xLoc) {
        if (index >= posX.size())
            posX.add(index, xLoc);
        else
            posX.set(index, xLoc);
    }

    public void setY(int index, int yLoc) {
        if (index >= posY.size())
            posY.add(index, yLoc);
        else
            posY.set(index, yLoc);
    }

    public void setX(int xLoc) {
        if (posX.size() == 0)
            posX.add(0, xLoc);
        else
            posX.set(0, xLoc);
    }

    public void setY(int yLoc) {
        if (posY.size() == 0)
            posY.add(0, yLoc);
        else
            posY.set(0, yLoc);
    }

    public int getMargin() {
        return margin;
    }

}
