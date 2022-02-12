package com.saptarshi.games.tris.tile;

import java.awt.image.BufferedImage;
import com.saptarshi.games.tris.backend.MapObject;

public final class TileType {

    private BufferedImage image;
    private final MapObject object;
    private final boolean goThrough;

    public TileType(MapObject object) {
        this.object = object;
        this.goThrough = switch(object) {
            case EARTH, GRASS, SAND -> true;
            case TREE, WALL, WATER -> false;
        };
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public MapObject getMapObject() {
        return object;
    }

    public boolean canGoThrough() {
        return goThrough;
    }

}
