package com.brentaureli.mariobros.Sprites.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Mario;

/**
 * Created by Branden on 8/11/2017.
 */

public class Bullet {
    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    private static Texture texture;
    float x, y;
    PlayScreen screen;

    public boolean remove = false;


    public Bullet(float x){
        this.x = x;
        this.y = DEFAULT_Y;


        if(texture == null)
            texture = new Texture("projectile11.png");
    }
   /* public void update (Float dt) {
        y += SPEED * dt;
        if(y > Gdx.graphics.getHeight())
            remove = true;

    }

    public void render (SpriteBatch batch){


        batch.draw(texture, x, y);


    }*/
}
