package com.viktorkrum.mariobros.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viktorkrum.mariobros.MarioBros;
import com.viktorkrum.mariobros.OverlayDemo.Controller;
import com.viktorkrum.mariobros.Scenes.Hud;
import com.viktorkrum.mariobros.Sprites.Items.Item;
import com.viktorkrum.mariobros.Sprites.Mario;
import com.viktorkrum.mariobros.Tools.B2WorldCreator;
import com.viktorkrum.mariobros.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Viktor Krum.
 */
public class PlayScreen extends ApplicationAdapter implements Screen{
    Controller controller;
    public static SpriteBatch batch;
    //Reference to our Game, used to set Screens
    private MarioBros game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    public boolean doubleJumped;

    //Create a variable to store the current number of
    //jumps made by the player. This should never be greater
    //than 2.
    private int jump;

    private long jumpDelayStart;

    private final int JUMP_DELAY;

    private int jumpDelayTimer;

    private boolean canDoubleJump;



    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Mario player;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<com.viktorkrum.mariobros.Sprites.Items.ItemDef> itemsToSpawn;



    long lastTap = System.currentTimeMillis();
    long lastTap1 = System.currentTimeMillis();



    //array for bullets




    public PlayScreen(MarioBros game){



        //Initialize the jump variable.
        jump = 0;

        JUMP_DELAY = 3;

        canDoubleJump = false;

        jumpDelayStart = System.currentTimeMillis();

        batch = new SpriteBatch();
        controller = new Controller();




        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("leveldesign1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / MarioBros.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);



        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -15.0F), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //create mario in our game world
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

      /*  music = MarioBros.manager.get("audio/music/exorcist.mp3", Music.class);
        MarioBros.manager.get("audio/music/exorcist.mp3", Music.class).setVolume(.2f);
        music.setLooping(true);

        music.play();*/

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<com.viktorkrum.mariobros.Sprites.Items.ItemDef>();



    }

    @Override
    public void show() {


    }

    public void spawnItem(com.viktorkrum.mariobros.Sprites.Items.ItemDef idef){
        itemsToSpawn.add(idef);
    }


    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            com.viktorkrum.mariobros.Sprites.Items.ItemDef idef = itemsToSpawn.poll();
            if(idef.type == com.viktorkrum.mariobros.Sprites.Items.Mushroom.class){
                items.add(new com.viktorkrum.mariobros.Sprites.Items.Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }


    public TextureAtlas getAtlas(){
        return atlas;
    }


    public boolean handleInput1 (float dt) {

        if (controller.isDownPressed()) {


            if (System.currentTimeMillis() - lastTap < 300)
            return true;

            player.fire();

            lastTap = System.currentTimeMillis();
        }
            return true;

    }




    /*
    * Is this a duplicate jump handler??? -Josh
    * */
    public boolean handleInput2 (float dt) {

        if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {


            if (System.currentTimeMillis() - lastTap < 470)
                return true;

            player.b2body.applyLinearImpulse(new Vector2(0, 7f), player.b2body.getWorldCenter(), true);

            lastTap = System.currentTimeMillis();
        }
        return true;

    }




    public void handleInput(float dt) {
        if(player.currentState != Mario.State.DEAD) {



           /* if(controller.isUpPressed()) {


                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

                    //If player is grounded?
                    if (player.b2body.getLinearVelocity().y == 0) {
                        player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                        doubleJumped = false;
                    } else if (player.b2body.getLinearVelocity().y > 0 && !doubleJumped) {
                        player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                        doubleJumped = true;
                    }
                }
            }*/


            /*
            * ========================================
            * Josh's jump handler, work in progress...
            * ========================================
            * */
            //I am not sure about these first two if statements,
            //but if they are correct I think the underlying logic
            //should work?
            //I don't really know what 'isUpPressed()' and 'isKeyJustPressed()' do.
            //I gotta look up the API specs...
            if(controller.isUpPressed()) {
                //If player is grounded?
                if (player.b2body.getLinearVelocity().y == 0) {
                    // Clear the jump counter when player touches the ground...
                    jump = 0;

                    player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);

                    //Increment jump count
                    jump++;

                    // Clear the upPressed boolean
                    //controller.clearUpPressed();
                } else if (jump < 2 && (System.currentTimeMillis() - jumpDelayStart) / 1000 > JUMP_DELAY) {

                    //Zero out vertical velocity??
                    //This should prevent the second jump from being too high I think by momentarily
                    //freezing the player in mid-air, ready to prepare for second jump.
                    float currentVelocity = player.b2body.getLinearVelocity().y;
                    player.b2body.applyLinearImpulse(new Vector2(0, -currentVelocity), player.b2body.getWorldCenter(), true);

                    //Perform jump
                    player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);

                    //Increment the jump counter.
                    jump++;

                    // Clear the upPressed boolean
                    //controller.clearUpPressed();
                }
                else {
                    //Reset the jump counter.
                    //Do not perform a jump.
                    jump = 0;

                    jumpDelayStart = System.currentTimeMillis();
                }
                controller.clearUpPressed();
            }
            /*
            * ========================================
            *
            * ========================================
            * */












            if(controller.isRightPressed())
                player.b2body.setLinearVelocity(new Vector2(4.5f, player.b2body.getLinearVelocity().y));
            else if (controller.isLeftPressed())
                player.b2body.setLinearVelocity(new Vector2(-4.5f, player.b2body.getLinearVelocity().y));
            else
                player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
           /* if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {




                player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                ((Sound) MarioBros.manager.get("audio/sounds/Link_jump.wav", Sound.class)).play();

            }*/

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))




            if(player.currentState != Mario.State.DEAD) {



                if(controller.isRightPressed()) {
                    player.b2body.setLinearVelocity(new Vector2(4.5f, player.b2body.getLinearVelocity().y));

                }
                else if (controller.isLeftPressed())
                    player.b2body.setLinearVelocity(new Vector2(-4.5f, player.b2body.getLinearVelocity().y));
                else
                    player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
               /* if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
                    ((Sound) MarioBros.manager.get("audio/sounds/Link_jump.wav", Sound.class)).play();
                    player.b2body.applyLinearImpulse(new Vector2(0, 7f), player.b2body.getWorldCenter(), true);

                }*/


                /*
                * Some kind of shooting mechanic? -Josh
                * */
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                    player.fire();


            }



        }




    }



    public void update(float dt){
        //handle user input first
        handleInput(dt);
        handleInput1(dt);
        handleInput2(dt);
        handleSpawningItems();






        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for(com.viktorkrum.mariobros.Sprites.Enemies.Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 670 / MarioBros.PPM) {
                if(player.getX() > 63 && player.getX() < 88){
                    enemy.b2body.setActive(false);
                }
                else enemy.b2body.setActive(true);

            }
            if(enemy.b2body.isActive() && enemy.getX() < player.getX() - 10 && enemy.getX() > player.getX() -11){
                enemy.b2body.setTransform(enemy.b2body.getPosition().x + 25, enemy.b2body.getPosition().y, 0);
            }
        }

        for(Item item : items)
            item.update(dt);


        //bullets
        //render







        //bullets

        hud.update(dt);

        //attach our gamecam to our players.x  and player.y? coordinate
        if(player.currentState != Mario.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y +1.1f;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {







        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();






        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (com.viktorkrum.mariobros.Sprites.Enemies.Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);








        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        controller.draw();

    }

    public boolean gameOver(){
        if(player.currentState == Mario.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);
        controller.resize(width, height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }





    public Hud getHud(){ return hud; }
}
