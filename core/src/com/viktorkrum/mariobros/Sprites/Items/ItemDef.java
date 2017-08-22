package com.viktorkrum.mariobros.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Viktor Krum
 */
public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
