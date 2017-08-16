package com.viktorkrum.mariobros.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.viktorkrum.mariobros.MarioBros;

/**
 * Created by brentaureli on 9/4/15.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case MarioBros.MARIO_HEAD_BIT | MarioBros.BRICK_BIT:
            case MarioBros.MARIO_HEAD_BIT | MarioBros.COIN_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT)
                    ((com.viktorkrum.mariobros.Sprites.TileObjects.InteractiveTileObject) fixB.getUserData()).onHeadHit((com.viktorkrum.mariobros.Sprites.Mario) fixA.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.TileObjects.InteractiveTileObject) fixA.getUserData()).onHeadHit((com.viktorkrum.mariobros.Sprites.Mario) fixB.getUserData());
                break;
           /* case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData()).hitOnHead((com.viktorkrum.mariobros.Sprites.Mario) fixB.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData()).hitOnHead((com.viktorkrum.mariobros.Sprites.Mario) fixA.getUserData());
                break;*/
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case MarioBros.ENEMY_BIT | MarioBros.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;


            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Mario) fixA.getUserData()).hit((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.Mario) fixB.getUserData()).hit((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:

                ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData()).hitByEnemy((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData());

                ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData()).hitByEnemy((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData());
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Items.Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((com.viktorkrum.mariobros.Sprites.Items.Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Items.Item)fixA.getUserData()).use((com.viktorkrum.mariobros.Sprites.Mario) fixB.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.Items.Item)fixB.getUserData()).use((com.viktorkrum.mariobros.Sprites.Mario) fixA.getUserData());
                break;






            case MarioBros.ENEMY_HEAD_BIT | MarioBros.FIREBALL_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData()).hitOnHead1((com.viktorkrum.mariobros.Sprites.Other.FireBall) fixB.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData()).hitOnHead1((com.viktorkrum.mariobros.Sprites.Other.FireBall) fixA.getUserData());
                break;

            case MarioBros.FIREBALL_BIT | MarioBros.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.FIREBALL_BIT)
                    ((com.viktorkrum.mariobros.Sprites.Other.FireBall) fixA.getUserData()).hit((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixB.getUserData());
                else
                    ((com.viktorkrum.mariobros.Sprites.Other.FireBall) fixB.getUserData()).hit((com.viktorkrum.mariobros.Sprites.Enemies.Enemy)fixA.getUserData());
                break;

























        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
