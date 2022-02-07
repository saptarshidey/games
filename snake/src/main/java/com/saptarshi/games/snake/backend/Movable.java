package com.saptarshi.games.snake.backend;

public sealed interface Movable permits Snake {

    void move();
    Direction getDirection();
    void setDirection(Direction d);
    boolean collision();

}
