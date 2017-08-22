package com.viktorkrum.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.viktorkrum.mariobros.MarioBros;

/**
 * Created by viktorkrum
 */
public class Brick extends InteractiveTileObject {
    public Brick(com.viktorkrum.mariobros.Screens.PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit(com.viktorkrum.mariobros.Sprites.Mario mario) {
        if(mario.isBig()) {
            setCategoryFilter(MarioBros.DESTROYED_BIT);
            getCell().setTile(null);
            com.viktorkrum.mariobros.Scenes.Hud.addScore(200);
            MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }

}
