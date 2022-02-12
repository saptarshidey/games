package com.saptarshi.games.tris.tile;

import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import com.saptarshi.games.tris.frontend.GameBoard;
import com.saptarshi.games.tris.backend.GameConstants;
import com.saptarshi.games.tris.backend.MapObject;

public final class TileManager {

    private final GameBoard game;
    private final TileType[] tileTypes;
    private final int[][] mapObjects;

    public TileManager(GameBoard game) {
        this.game = game;
        tileTypes = new TileType[GameConstants.TILE_TYPES];
        for (int i = 0; i < tileTypes.length; i++) {
            MapObject objType = switch(i) {
                case 0 -> MapObject.GRASS;
                case 1 -> MapObject.WALL;
                case 2 -> MapObject.WATER;
                case 3 -> MapObject.EARTH;
                case 4 -> MapObject.TREE;
                case 5 -> MapObject.SAND;
                default -> null;
            };
            tileTypes[i] = new TileType(objType);
        }
        mapObjects = new int[GameConstants.MAX_WORLD_COL][GameConstants.MAX_WORLD_ROW];

        loadImages();
        loadMap();
    }

    public TileType[] getTileTypes() {
        return tileTypes;
    }

    public int[][] getMapObjects() {
        return mapObjects;
    }

    public void loadImages() {
        for (TileType tileType : tileTypes) {
            try {
                switch(tileType.getMapObject()) {
                    case EARTH -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/earth.png"))));
                    case GRASS -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/grass.png"))));
                    case SAND -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/sand.png"))));
                    case TREE -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/tree.png"))));
                    case WALL -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/wall.png"))));
                    case WATER -> tileType.setImage(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/tiles/water.png"))));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMap() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(this.getClass().getResourceAsStream("/maps/world01.db"))
                    )
            );
            int col = 0, row = 0;
            while (col < GameConstants.MAX_WORLD_COL && row < GameConstants.MAX_WORLD_ROW) {
                String[] mapLine = br.readLine().split(" ");
                while (col < GameConstants.MAX_WORLD_COL) {
                    mapObjects[col][row] = Integer.parseInt(mapLine[col]);
                    col++;
                }
                if (col == GameConstants.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0, row = 0;
        int ttype;
        int worldX, worldY;
        int screenX, screenY;

        while (col < GameConstants.MAX_WORLD_COL && row < GameConstants.MAX_WORLD_ROW) {
            ttype = mapObjects[col][row];
            worldX = col * GameConstants.TILE_SIZE;
            worldY = row * GameConstants.TILE_SIZE;
            screenX = worldX - game.getPlayer().getWorldX() + game.getPlayer().getScreenX();
            screenY = worldY - game.getPlayer().getWorldY() + game.getPlayer().getScreenY();

            // draw only when image is within screen bounds (performance tuning)
            if (
                screenX + GameConstants.TILE_SIZE > 0 &&
                screenX - GameConstants.TILE_SIZE < GameConstants.SCREEN_WIDTH &&
                screenY + GameConstants.TILE_SIZE > 0 &&
                screenY - GameConstants.TILE_SIZE < GameConstants.SCREEN_HEIGHT
            ) {
                g2.drawImage(tileTypes[ttype].getImage(), screenX, screenY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
            }

            col++;
            if (col == GameConstants.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

}
