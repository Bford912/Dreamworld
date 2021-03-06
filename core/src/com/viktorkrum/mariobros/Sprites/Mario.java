package com.viktorkrum.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viktorkrum.mariobros.MarioBros;
import com.viktorkrum.mariobros.Screens.GameOverScreen;
import com.viktorkrum.mariobros.Screens.PlayScreen;
import com.viktorkrum.mariobros.Sprites.Items.Item;
import com.viktorkrum.mariobros.Sprites.Other.FireBall;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Viktor Krum
 */
public class Mario extends Sprite {





    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, GOTHIT };
    public State currentState;
    public State previousState;
    public Body bodii;

    public World world;
    public Body b2body;

    public Body b2body2;


    private Animation walkAnimation;


    public boolean fourHearts;





    public boolean threeHearts;
    public boolean twoHearts;

    private Texture druidTexture;
    private SpriteBatch batch;




    private PlayScreen course;




    long lastTap = System.currentTimeMillis();





    //private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    //private TextureRegion marioJump;
    private TextureRegion marioDead;
    private Animation bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation bigMarioRun;
    private Music music;
    private Animation marioStand;


    private Animation growMario;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean getHitAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean marioIsDead;
    private com.viktorkrum.mariobros.Screens.PlayScreen screen;

    public Vector2 bodi;

    private Array<FireBall> fireballs;

    private int marioHealth;


    private MarioBros game;








    public Mario(com.viktorkrum.mariobros.Screens.PlayScreen screen){


        this.marioHealth = 3;



        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 0; i < 12; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("shinrun2"), i * 68, 0, 68, 68));
        marioRun = new Animation(0.08f, frames);

        frames.clear();

        for(int i = 0; i < 11; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), i * 68, 0, 68, 68));
        bigMarioRun = new Animation(0.08f, frames);

        frames.clear();

        //get set animation frames from growing mario
        frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), 0, 0, 68, 68));
        growMario = new Animation(0.2f, frames);


        //get jump animation frames and add them to marioJump Animation
        for(int i = 0; i < 11; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("cooldude"), i * 68, 0, 68, 68));
        marioJump = new Animation(0.08f, frames);


        //marioJump = new TextureRegion(screen.getAtlas().findRegion("cooldude"), 68, 0, 68, 68);
        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("cooldude"), 68, 0, 68, 68);

        //create texture region for mario standing
        frames.clear();

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("shinstand2"), i * 68, 0, 68, 68));
        marioStand = new Animation(.1f, frames);

        frames.clear();



        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 68, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 68, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 68, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 0, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 68, 0, 68, 68));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("shinhit"), 0, 0, 68, 68));
        walkAnimation = new Animation(0.2f, frames);






        for(int i = 1; i < 19; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("skull"), i * 68, 0, 68, 68));
        bigMarioStand = new Animation(.1f, frames);





        //marioStand = new TextureRegion(screen.getAtlas().findRegion("cooldude"), 0, 0, 68, 68);
        //bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("cooldude"),  0, 0, 68, 68);

        //create dead mario texture region
        marioDead = new TextureRegion(screen.getAtlas().findRegion("shinstand2"),  68, 0, 68, 68);

        //define mario in Box2d
        defineMario();
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("shinstand2"), i * 68, 0, 68, 68));
        marioStand = new Animation(.1f, frames);
        frames.clear();

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("shinstand2"), i * 68, 0, 68, 68));
        marioStand = new Animation(.1f, frames);
        frames.clear();

        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 120/ MarioBros.PPM, 120 / MarioBros.PPM);

        //setRegion(marioStand);

        fireballs = new Array<FireBall>();





        music = MarioBros.manager.get("audio/music/fairy.mp3", Music.class);
        MarioBros.manager.get("audio/music/fairy.mp3", Music.class).setVolume(.7f);
        music.setLooping(true);

        music.play();
        /*music = MarioBros.manager.get("audio/music/fairy.mp3", Music.class);
        music.setLooping(true);*/


    }




    public void attendCourse(PlayScreen course){
        this.course = course;
    }


    public PlayScreen getCourse(){
        return course;
    }





    public void update(float dt){

        // time is up : too late mario dies T_T
        // the !isDead() method is used to prevent multiple invocation
        // of "die music" and jumping
        // there is probably better ways to do that but it works for now.
        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }

        //update our sprite to correspond with the position of our Box2D body
        if(marioIsBig)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / MarioBros.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
//            setPosition(b2body2.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));
        if(timeToDefineBigMario)
            defineBigMario();
        if(timeToRedefineMario)
            redefineMario();

        for(FireBall  ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }


        float location = b2body.getPosition().x;
        float location1 = b2body.getPosition().y;
        System.out.println("x: " + location + "y: " + location1);


    }



    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = marioDead;
                break;
            case GROWING:
                region = (TextureRegion) growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer)) {
                    runGrowAnimation = false;
                }
                break;
            case JUMPING:
            case GOTHIT:
                region = (TextureRegion)walkAnimation.getKeyFrame(stateTimer);
                if(walkAnimation.isAnimationFinished(stateTimer)){
                    getHitAnimation = false;
                }
                break;

            case RUNNING:
                region = (TextureRegion) (marioIsBig ? bigMarioRun.getKeyFrame(stateTimer, true) : marioRun.getKeyFrame(stateTimer, true));
                break;
            case FALLING:
            case STANDING:
            default:
                region = (TextureRegion) (marioIsBig ? bigMarioStand.getKeyFrame(stateTimer, true) : marioStand.getKeyFrame(stateTimer, true));
                break;
        }


        //setting up portal
        /*if(b2body.getPosition().y >2. && b2body.getPosition().x <3.0 && b2body.getPosition().x >2.9){
            b2body.setTransform(65, 3, 0);
        }*/

        // end of castle world
        if(b2body.getPosition().y >25 && b2body.getPosition().y <27 && b2body.getPosition().x <14 && b2body.getPosition().x >13.5 ){
            b2body.setTransform(72.5f, 3, 0);

        }

        //first portal selection
        if(b2body.getPosition().y >3.6 && b2body.getPosition().y <4.8 && b2body.getPosition().x <67.9 && b2body.getPosition().x >67.3 ){
            b2body.setTransform(3, 3, 0);

        }
        //forest world
        if(b2body.getPosition().y >3.6 && b2body.getPosition().y <4.8 && b2body.getPosition().x <75.9 && b2body.getPosition().x >75.4 ){
            b2body.setTransform(200, 30, 0);

        }





        //setting up easy world
        if(b2body.getPosition().y >1.5 && b2body.getPosition().y <2 && b2body.getPosition().x <142 && b2body.getPosition().x >141){
            b2body.setTransform(1, 1, 0);

        }







       /* if(b2body.getPosition().x >101 & b2body.getPosition().x < 400){

            MarioBros.manager.get("audio/music/fairy.mp3", Music.class).play();
            music = MarioBros.manager.get("audio/music/fairy.mp3", Music.class);
            music.setLooping(true);


        }*/











        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        if((b2body.getPosition().y < -2)){
            die();
        }





        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(marioIsDead)
            return State.DEAD;
        else if(runGrowAnimation)
            return State.GROWING;

        else if(getHitAnimation){
            return State.GOTHIT;
        }




        /*else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;*/
        //if negative in Y-Axis mario is falling
        /*else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;*/
        //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        //if none of these return then he must be standing

        else
            return State.STANDING;
    }

    public void grow(){



        if( !isBig() ) {
            runGrowAnimation = true;
            marioIsBig = true;
            timeToDefineBigMario = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 1.5f);



            MarioBros.manager.get("audio/sounds/puzzle.wav", Sound.class).play();
        }
    }

    public void die() {

        if (!isDead()) {

            MarioBros.manager.get("audio/music/exorcist.mp3", Music.class).stop();
            music = MarioBros.manager.get("audio/music/simpsons.mp3", Music.class);
            MarioBros.manager.get("audio/music/simpsons.mp3", Music.class).play();

            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MarioBros.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }




            b2body.applyLinearImpulse(new Vector2(0, 11f), b2body.getWorldCenter(), true);

        }
    }



    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public boolean isBig(){
        return marioIsBig;
    }

  /* public void jump(){
        if ( currentState != State.JUMPING ) {
           // b2body.applyLinearImpulse(new Vector2(0, 6f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }*/



    public void hit(com.viktorkrum.mariobros.Sprites.Enemies.Enemy enemy){
        if(enemy instanceof com.viktorkrum.mariobros.Sprites.Enemies.Turtle && ((com.viktorkrum.mariobros.Sprites.Enemies.Turtle) enemy).currentState == com.viktorkrum.mariobros.Sprites.Enemies.Turtle.State.STANDING_SHELL)
            ((com.viktorkrum.mariobros.Sprites.Enemies.Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? com.viktorkrum.mariobros.Sprites.Enemies.Turtle.KICK_RIGHT : com.viktorkrum.mariobros.Sprites.Enemies.Turtle.KICK_LEFT);
        else {
            if (marioHealth >0) {


                getHitAnimation = true;
                marioIsBig = false;
                timeToDefineBigMario = false;
                setBounds(getX(), getY(), getWidth(), getHeight());



                MarioBros.manager.get("audio/sounds/puzzle.wav", Sound.class).play();


                /*Array<TextureRegion> frames = new Array<TextureRegion>();


                for(int i = 1; i < 2; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("skull"), i * 68, 0, 68, 68));
                marioStand = new Animation(.1f, frames);

                */
                marioHealth--;
                marioIsBig = false;
                //timeToRedefineMario = true;
                //setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                MarioBros.manager.get("audio/sounds/powerdown.wav", Sound.class).play();


            } //else {
                //die();
            //}
        }

    }

    public int getMarioHealth(){
        return this.marioHealth;
    }


    public void marioisgondie(){
        if(marioHealth > 0){
            System.out.println(marioHealth);


        }
        else
            die();




    }


    public void redefineMario(){
        Vector2 position = b2body.getPosition();



        world.destroyBody(b2body);




        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24/ MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(0 / MarioBros.PPM, 0/ MarioBros.PPM), new Vector2(0 / MarioBros.PPM, 0 / MarioBros.PPM));
       // fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineMario = false;

    }



    public void defineBigMario(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / MarioBros.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -14 / MarioBros.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MarioBros.PPM, 13 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 13 / MarioBros.PPM));
        fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigMario = false;
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        //castle starting position
        //bdef.position.set(300/ MarioBros.PPM, 300 / MarioBros.PPM);


        //position for the pirate world
       // bdef.position.set(1045.6f/ MarioBros.PPM, 4000 / MarioBros.PPM);

        //bdef.position.set(10000/ MarioBros.PPM, 300 / MarioBros.PPM);
        //position for forest world
        bdef.position.set(1045.6f/ MarioBros.PPM, 4000 / MarioBros.PPM);


        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(32/ MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                //MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-1, 1).scl(1 / MarioBros.PPM);
        vertice[1] = new Vector2(1, 1).scl(1 / MarioBros.PPM);
        vertice[2] = new Vector2(-1, -1).scl(1 / MarioBros.PPM);
        vertice[3] = new Vector2(1, -1).scl(1 / MarioBros.PPM);
        head.set(vertice);




       /* EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MarioBros.PPM, 14/ MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 14 / MarioBros.PPM));*/
        fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void fire(){
        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }
    

    public void draw(Batch batch){

        super.draw(batch);
        for(FireBall ball : fireballs)
            ball.draw(batch);

    }
}
