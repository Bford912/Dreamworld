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
import com.viktorkrum.mariobros.Sprites.Other.FireBall;

/**
 * Created by Branden Ford
 */
public class Sorc extends Enemy
{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    public float xpos;
    float angle;


    public Sorc(com.viktorkrum.mariobros.Screens.PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("sorc"), i * 68, 0, 68, 68));
        walkAnimation = new Animation(0.1f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 150 / MarioBros.PPM, 150 / MarioBros.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
        b2body.getPosition().x = xpos;
        if (b2body.getPosition().y <1){
            this.b2body.setLinearVelocity(4f, 0);
        }
    }



    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("sorc"), 68, 0, 68, 68));
            stateTime = 0;
        }

        else if(!destroyed) {

            b2body.setLinearVelocity(new Vector2(-4, -4));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }

        if (b2body.getPosition().y <1){
            this.b2body.setLinearVelocity(4f, 0);
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
        shape.setRadius(38 / MarioBros.PPM);
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
        vertice[0] = new Vector2(-38, 38).scl(1 / MarioBros.PPM);
        vertice[1] = new Vector2(38, 38).scl(1 / MarioBros.PPM);
        vertice[2] = new Vector2(-38, -38).scl(1 / MarioBros.PPM);
        vertice[3] = new Vector2(38, -38).scl(1 / MarioBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 3f;
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

    public void hitOnHead1(FireBall fireBall){
        setToDestroy = true;
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL)
            setToDestroy = true;
        else
            reverseVelocity(true, false);
    }
}




