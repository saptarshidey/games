package com.saptarshi.games.tris.backend;

public class GameConstants {

    // Common game settings
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE_FACTOR = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE_FACTOR;
    public static final int TILE_TYPES = 6;
    public static final int FPS = 60;

    // Screen settings
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE;

    // World settings
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = MAX_WORLD_COL * TILE_SIZE;
    public static final int WORLD_HEIGHT = MAX_WORLD_ROW * TILE_SIZE;

    // Player settings
    public static final int PLAYER_START_WORLDX = TILE_SIZE * 23;
    public static final int PLAYER_START_WORLDY = TILE_SIZE * 21;
    public static final int PLAYER_CENTER_SCREENX = GameConstants.SCREEN_WIDTH / 2 - GameConstants.TILE_SIZE / 2;
    public static final int PLAYER_CENTER_SCREENY = GameConstants.SCREEN_HEIGHT / 2 - GameConstants.TILE_SIZE / 2;

}
