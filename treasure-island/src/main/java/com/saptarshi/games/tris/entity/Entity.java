package com.saptarshi.games.tris.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.saptarshi.games.tris.backend.Direction;

public sealed abstract class Entity permits Player {

    private int worldX;     // x position relative to the world
    private int worldY;     // y position relative to the world
    private int screenX;    // x position relative to the screen
    private int screenY;    // y position relative to the screen
    private int speed;
    private Direction direction;
    private BufferedImage up1, up2;
    private BufferedImage down1, down2;
    private BufferedImage left1, left2;
    private BufferedImage right1, right2;
    private final Rectangle solidArea;
    private int currentSprite;
    private int spriteFrameCounter;

    public Entity(int worldX, int worldY, int screenX, int screenY, int speed, Rectangle solid) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;
        this.speed = speed;
        solidArea = solid;
        loadImages();
        currentSprite = 1;
    }

    public abstract void loadImages();
    public abstract void update();
    public abstract boolean collide();
    public abstract void draw(Graphics2D g2);

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction d) {
        direction = d;
    }

    public BufferedImage getUpImg(int num) {
        return (num == 1) ? up1: up2;
    }

    public BufferedImage getDownImg(int num) {
        return (num == 1) ? down1: down2;
    }

    public BufferedImage getLeftImg(int num) {
        return (num == 1) ? left1: left2;
    }

    public BufferedImage getRightImg(int num) {
        return (num == 1) ? right1: right2;
    }

    public void setUpImg(BufferedImage image, int num) {
        switch(num) {
            case 1 -> up1 = image;
            case 2 -> up2 = image;
        }
    }

    public void setDownImg(BufferedImage image, int num) {
        switch(num) {
            case 1 -> down1 = image;
            case 2 -> down2 = image;
        }
    }

    public void setLeftImg(BufferedImage image, int num) {
        switch(num) {
            case 1 -> left1 = image;
            case 2 -> left2 = image;
        }
    }

    public void setRightImg(BufferedImage image, int num) {
        switch(num) {
            case 1 -> right1 = image;
            case 2 -> right2 = image;
        }
    }

    public Rectangle getSolidArea() { return solidArea; }

    public int getSprite() {
        return currentSprite;
    }

    public void switchSprite() {
        currentSprite = (currentSprite == 1) ? 2 : 1;
    }

    public int getSpriteFrameCounter() {
        return spriteFrameCounter;
    }

    public void setSpriteFrameCounter(int counter) {
        spriteFrameCounter = counter;
    }

}
