package com.viktorkrum.mariobros.Sprites.Enemies;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.viktorkrum.mariobros.MarioBros;

/**
 * Created by Branden Ford
 */
public class Diablo extends Enemy
{
    private float stateTime;
    private Animation walkAnimation;
    private Animation deathAnimation;
    private Array<TextureRegion> frames;

    private boolean setToDestroy;
    private boolean destroyed;
    public float xpos;
    float angle;
    public float inactiveBody;
    public float inactiveBodyy;
    public float travelDirection;
    public float travelDirectiony;


    public Diablo(com.viktorkrum.mariobros.Screens.PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("diablo"), i * 100, 0, 100, 100));
        walkAnimation = new Animation(0.1f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 225 / MarioBros.PPM, 225 / MarioBros.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
       /* if (b2body.getPosition().y <1){
            this.b2body.setLinearVelocity(4f, 0);
        }*/


    }



    public void update(float dt){
        stateTime += dt;


        if(setToDestroy && !destroyed){




            world.destroyBody(b2body);
            destroyed = true;












            setRegion(new TextureRegion(screen.getAtlas().findRegion("explosion"), 272, 0, 68, 68));
            stateTime = 0;
        }

        else if(!destroyed) {
            travelDirection = -4;
            travelDirectiony =-1;

            this.b2body.setLinearVelocity(new Vector2(travelDirection, travelDirectiony));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }


        else if(this.b2body.getLinearVelocity().x <1 && this.b2body.getLinearVelocity().x >-1){
            travelDirection = 4;
            travelDirectiony = 1;
            this.b2body.setLinearVelocity(new Vector2(travelDirection, travelDirectiony));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(48 / MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.ENEMY_BIT;
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.FIREBALL_BIT|
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-52, 52).scl(1 / MarioBros.PPM);
        vertice[1] = new Vector2(52, 52).scl(1 / MarioBros.PPM);
        vertice[2] = new Vector2(-52, -52).scl(1 / MarioBros.PPM);
        vertice[3] = new Vector2(52, -52).scl(1 / MarioBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = MarioBros.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);





    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }



    @Override
    public void hitOnHead(com.viktorkrum.mariobros.Sprites.Mario mario) {
        setToDestroy = true;
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }

    public void hitOnHead1(com.viktorkrum.mariobros.Sprites.Other.FireBall fireBall){
        setToDestroy = true;
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL)
            setToDestroy = true;

     //       reverseVelocity(true, false);
    }

    public void dispose() {
        //dispose of all our opened resources


        world.dispose();
        dispose();


    }
}




