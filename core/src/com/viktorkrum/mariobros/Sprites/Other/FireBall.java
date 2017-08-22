package com.viktorkrum.mariobros.Sprites.Other;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.viktorkrum.mariobros.MarioBros;

/**
 * Created by Viktor Krum
 */
public class FireBall extends Sprite {




    com.viktorkrum.mariobros.Screens.PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;


    CollisionRect rect;


    Body b2body;
    public FireBall(com.viktorkrum.mariobros.Screens.PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("lightning1"), i * 200, 0, 200, 50));
        fireAnimation= new Animation(0.125f, frames);


        fireAnimation = new Animation(0.1f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 200 / MarioBros.PPM, 100/ MarioBros.PPM);
        defineFireBall();



    }




    public void defineFireBall(){


        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /MarioBros.PPM : getX() - 12 /MarioBros.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18 / MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.FIREBALL_BIT;
        fdef.filter.maskBits = MarioBros.ENEMY_HEAD_BIT |
               // MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
               // MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                //MarioBros.OBJECT_BIT |

                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;

        fdef.shape = shape;


        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 14 : -11.5f, 9.5f));







        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MarioBros.PPM, 6 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 6 / MarioBros.PPM));
        fdef.filter.categoryBits = MarioBros.FIREBALL_BIT;
        fdef.shape = head;



    }












    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 2 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > -88f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 0);
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();
        if(b2body.getLinearVelocity().x ==0){
            setToDestroy();
        }
    }

    public void hit(com.viktorkrum.mariobros.Sprites.Enemies.Enemy enemy){
        if(enemy instanceof com.viktorkrum.mariobros.Sprites.Enemies.Turtle && ((com.viktorkrum.mariobros.Sprites.Enemies.Turtle) enemy).currentState == com.viktorkrum.mariobros.Sprites.Enemies.Turtle.State.STANDING_SHELL)
            ((com.viktorkrum.mariobros.Sprites.Enemies.Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? com.viktorkrum.mariobros.Sprites.Enemies.Turtle.KICK_RIGHT : com.viktorkrum.mariobros.Sprites.Enemies.Turtle.KICK_LEFT);
        else {

                setBounds(getX(), getY(), getWidth(), getHeight() / 2);










        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void dispose() {
        //dispose of all our opened resources


        world.dispose();
        dispose();


    }


}
