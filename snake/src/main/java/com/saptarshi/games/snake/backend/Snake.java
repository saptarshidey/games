package com.saptarshi.games.snake.backend;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import com.saptarshi.games.snake.frontend.GameBoard;

public final class Snake extends Sprite implements Movable {

    private final static int INIT_PARTS = 3;

    private final Color headColor;
    private final Color bodyColor;
    private Direction direction;

    public Snake(GameBoard game, Color headColor, Color bodyColor, int margin) {
        super(game, margin, INIT_PARTS);
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        this.direction = Direction.RIGHT;
    }

    public int hadLunch() {
        Apple[] applesOnBoard = getGameBoard().getApples();
        int i;
        for (i = 0; i < applesOnBoard.length; i++) {
            if (getX() == applesOnBoard[i].getX() && getY() == applesOnBoard[i].getY()) break;
        }
        return (i == applesOnBoard.length) ? -1: i;
    }

    public void grow() {
        int gridSize = getGameBoard().getGridSize();
        ArrayList<Integer> posX = getXs();
        ArrayList<Integer> posY = getYs();

        switch (tailDirection()) {
            case LEFT -> {
                posX.add(getX(posX.size() - 1) + gridSize);
                posY.add(getY(posY.size() - 1));
            }
            case RIGHT -> {
                posX.add(getX(posX.size() - 1) - gridSize);
                posY.add(getY(posY.size() - 1));
            }
            case UP -> {
                posX.add(getX(posX.size() - 1));
                posY.add(getY(posY.size() - 1) + gridSize);
            }
            case DOWN -> {
                posX.add(getX(posX.size() - 1));
                posY.add(getY(posY.size() - 1) - gridSize);
            }
        }
    }

    public void reborn() {
        getXs().clear();
        getYs().clear();
        setDirection(Direction.RIGHT);
    }

    public Direction tailDirection() {
        int snakeSize = getSize();
        int tailIndex = snakeSize - 1;
        int secondLastIndex = snakeSize - 2;

        int tailX = getX(tailIndex);
        int tailY = getY(tailIndex);
        int secondLastX = getX(secondLastIndex);
        int secondLastY = getY(secondLastIndex);

        if (tailX - secondLastX == 0) {     // moving up or down
            return (tailY - secondLastY < 0) ? Direction.DOWN : Direction.UP;
        }
        if (tailY - secondLastY == 0) {     // moving left or right
            return (tailX - secondLastX < 0) ? Direction.RIGHT : Direction.LEFT;
        }

        return getDirection();  // will never reach here
    }

    @Override
    public void move() {
        int snakeSize = getSize();
        for (int i = snakeSize - 1; i > 0; i--) {
            setX(i, getX(i - 1));
            setY(i, getY(i - 1));
        }

        int gridSize = getGameBoard().getGridSize();
        switch (direction) {
            case LEFT -> setX(getX() - gridSize);
            case RIGHT -> setX(getX() + gridSize);
            case UP -> setY(getY() - gridSize);
            case DOWN -> setY(getY() + gridSize);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction d) {
        direction = d;
    }

    @Override
    public boolean collision() {
        int snakeSize = getSize();

        // check if head collided with body
        for (int i = snakeSize - 1; i > 0; i--) {
            if (getX() == getX(i) && getY() == getY(i)) {
                return true;
            }
        }

        // check if head collided with the walls
        Dimension d = getGameBoard().getScreenDimension();
        return getX() < 0 || getX() > d.width || getY() < 0 || getY() > d.height;
    }

    @Override
    public void initLocation() {
        int gridSize = getGameBoard().getGridSize();

        setX(0,gridSize * (INIT_PARTS - 1));
        setX(1, gridSize);
        setX(2, 0);

        setY(0, 0);
        setY(1, 0);
        setY(2, 0);
    }

    @Override
    public void paint(Graphics g) {
        int snakeSize = getSize();
        int gridSize = getGameBoard().getGridSize();
        int oneThirdGrid = (int)(gridSize / 3.0f);
        int twoThirdGrid = (int)(2 * gridSize / 3.0f);
        int halfGrid = (int)(gridSize / 2.0f);
        int bodyDiameter = gridSize - getMargin() * 2;
        int secondLastDiameter = gridSize - getMargin() * 3;
        int tailDiameter = gridSize - getMargin() * 4;
        int twoTimesMargin = getMargin() * 2;
        int threeOverTwoMargin = (int)(3 * getMargin() / 2.0f);

        for (int i = 0; i < snakeSize; i++) {
            if (i == 0) {   // head
                g.setColor(headColor);
                g.fillOval(getX(i), getY(i), gridSize, gridSize);

                // eyes
                g.setColor(Color.black);
                switch(direction) {
                    case RIGHT -> {
                        g.fillOval(getX(i) + twoThirdGrid, getY(i) + oneThirdGrid - 1, 4, 4);
                        g.fillOval(getX(i) + twoThirdGrid, getY(i) + twoThirdGrid - 1, 4, 4);
                    }
                    case LEFT -> {
                        g.fillOval(getX(i) + oneThirdGrid, getY(i) + oneThirdGrid - 1, 4, 4);
                        g.fillOval(getX(i) + oneThirdGrid, getY(i) + twoThirdGrid - 1, 4, 4);
                    }
                    case UP -> {
                        g.fillOval(getX(i) + oneThirdGrid - 1, getY(i) + oneThirdGrid, 4, 4);
                        g.fillOval(getX(i) + twoThirdGrid - 1, getY(i) + oneThirdGrid, 4, 4);
                    }
                    case DOWN -> {
                        g.fillOval(getX(i) + oneThirdGrid - 1, getY(i) + twoThirdGrid, 4, 4);
                        g.fillOval(getX(i) + twoThirdGrid - 1, getY(i) + twoThirdGrid, 4, 4);
                    }
                }

                // tongue
                g.setColor(Color.red);
                switch(direction) {
                    case RIGHT -> g.drawLine(getX(i) + gridSize, getY(i) + halfGrid, getX(i) + gridSize + 8, getY(i) + halfGrid);
                    case LEFT -> g.drawLine(getX(i), getY(i) + halfGrid, getX(i) - 8, getY(i) + halfGrid);
                    case UP -> g.drawLine(getX(i) + halfGrid, getY(i), getX(i) + halfGrid, getY(i) - 8);
                    case DOWN -> g.drawLine(getX(i) + halfGrid, getY(i) + gridSize, getX(i) + halfGrid, getY(i) + gridSize + 8);
                }
            }
            else {
                g.setColor(bodyColor);
                if (i == snakeSize - 1) {   // tail
                    g.fillOval(getX(i) + twoTimesMargin, getY(i) + twoTimesMargin, tailDiameter, tailDiameter);
                }
                else if (i == snakeSize - 2) {  // 1 position before tail
                    g.fillOval(getX(i) + threeOverTwoMargin, getY(i) + threeOverTwoMargin, secondLastDiameter, secondLastDiameter);
                }
                else {  // rest of the body
                    g.fillOval(getX(i) + getMargin(), getY(i) + getMargin(), bodyDiameter, bodyDiameter);
                }
            }
        }
    }

}
