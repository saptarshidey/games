package com.saptarshi.games.tris.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

import com.saptarshi.games.tris.frontend.GameBoard;
import com.saptarshi.games.tris.backend.KeyHandler;
import com.saptarshi.games.tris.backend.Direction;
import com.saptarshi.games.tris.backend.GameConstants;
import com.saptarshi.games.tris.tile.TileType;

public final class Player extends Entity {

    private final GameBoard game;
    private final KeyHandler keyHandler;

    public Player(GameBoard game, KeyHandler handler) {
        super(
            GameConstants.PLAYER_START_WORLDX,
            GameConstants.PLAYER_START_WORLDY,
            GameConstants.PLAYER_CENTER_SCREENX,
            GameConstants.PLAYER_CENTER_SCREENY,
            3,
            new Rectangle(8, 8, 32, 32)
        );
        setDirection(Direction.DOWN);
        this.game = game;
        this.keyHandler = handler;
    }

    @Override
    public void loadImages() {
        try {
            setUpImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_up_1.png"))), 1);
            setUpImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_up_2.png"))), 2);
            setDownImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_down_1.png"))), 1);
            setDownImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_down_2.png"))), 2);
            setLeftImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_left_1.png"))), 1);
            setLeftImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_left_2.png"))), 2);
            setRightImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_right_1.png"))), 1);
            setRightImg(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/player/boy_right_2.png"))), 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (keyHandler.isArrowPressed()) {
            setDirection(
                keyHandler.isUpPressed() ? Direction.UP : (
                    keyHandler.isDownPressed() ? Direction.DOWN : (
                        keyHandler.isLeftPressed() ? Direction.LEFT : Direction.RIGHT
                    )
                )
            );

            // player can move if there is no collision
            if (!collide()) {
                switch(getDirection()) {
                    case UP -> setWorldY(getWorldY() - getSpeed());
                    case DOWN -> setWorldY(getWorldY() + getSpeed());
                    case LEFT -> setWorldX(getWorldX() - getSpeed());
                    case RIGHT -> setWorldX(getWorldX() + getSpeed());
                }
            }

            setSpriteFrameCounter(getSpriteFrameCounter() + 1);
            if (getSpriteFrameCounter() > 10) {
                switchSprite();
                setSpriteFrameCounter(0);
            }
        }
    }

    @Override
    public boolean collide() {
        int entityLeftCol = (getWorldX() + getSolidArea().x) / GameConstants.TILE_SIZE;
        int entityRightCol = (getWorldX() + getSolidArea().x + getSolidArea().width) / GameConstants.TILE_SIZE;
        int entityTopRow = (getWorldY() + getSolidArea().y) / GameConstants.TILE_SIZE;
        int entityBottomRow = (getWorldY() + getSolidArea().y + getSolidArea().height) / GameConstants.TILE_SIZE;

        TileType[] tileTypes = game.getTileManager().getTileTypes();
        int[][] mapObjects = game.getTileManager().getMapObjects();

        int mapObjectLeft, mapObjectRight;
        int mapObjectTop, mapObjectBottom;
        boolean collided = false;
        switch(getDirection()) {
            case UP -> {
                entityTopRow = (getWorldY() + getSolidArea().y - getSpeed()) / GameConstants.TILE_SIZE;
                mapObjectLeft = mapObjects[entityLeftCol][entityTopRow];
                mapObjectRight = mapObjects[entityRightCol][entityTopRow];
                collided = !tileTypes[mapObjectLeft].canGoThrough() || !tileTypes[mapObjectRight].canGoThrough();
            }
            case DOWN -> {
                entityBottomRow = (getWorldY() + getSolidArea().y + getSolidArea().height + getSpeed()) / GameConstants.TILE_SIZE;
                mapObjectLeft = mapObjects[entityLeftCol][entityBottomRow];
                mapObjectRight = mapObjects[entityRightCol][entityBottomRow];
                collided = !tileTypes[mapObjectLeft].canGoThrough() || !tileTypes[mapObjectRight].canGoThrough();
            }
            case LEFT -> {
                entityLeftCol = (getWorldX() + getSolidArea().x - getSpeed()) / GameConstants.TILE_SIZE;
                mapObjectTop = mapObjects[entityLeftCol][entityTopRow];
                mapObjectBottom = mapObjects[entityLeftCol][entityBottomRow];
                collided = !tileTypes[mapObjectTop].canGoThrough() || !tileTypes[mapObjectBottom].canGoThrough();
            }
            case RIGHT -> {
                entityRightCol = (getWorldX() + getSolidArea().x + getSolidArea().width + getSpeed()) / GameConstants.TILE_SIZE;
                mapObjectTop= mapObjects[entityRightCol][entityTopRow];
                mapObjectBottom = mapObjects[entityRightCol][entityBottomRow];
                collided = !tileTypes[mapObjectTop].canGoThrough() || !tileTypes[mapObjectBottom].canGoThrough();
            }
        };

        return collided;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage bf = switch (getDirection()) {
            case UP -> getUpImg(getSprite());
            case DOWN -> getDownImg(getSprite());
            case LEFT -> getLeftImg(getSprite());
            case RIGHT -> getRightImg(getSprite());
        };

        Rectangle rect = getSolidArea();
        g2.drawImage(bf, getScreenX(), getScreenY(), GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);

        g2.setColor(Color.red);
        g2.drawRect(getScreenX() + rect.x, getScreenY() + rect.y, rect.width, rect.height);
    }

}
