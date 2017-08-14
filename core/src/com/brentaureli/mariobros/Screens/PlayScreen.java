package com.brentaureli.mariobros.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.OverlayDemo.Controller;
import com.brentaureli.mariobros.Scenes.Hud;
import com.brentaureli.mariobros.Sprites.Enemies.Enemy;
import com.brentaureli.mariobros.Sprites.Items.Item;
import com.brentaureli.mariobros.Sprites.Items.ItemDef;
import com.brentaureli.mariobros.Sprites.Items.Mushroom;
import com.brentaureli.mariobros.Sprites.Mario;
import com.brentaureli.mariobros.Sprites.Other.Bullet;
import com.brentaureli.mariobros.Tools.B2WorldCreator;
import com.brentaureli.mariobros.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.PriorityQueue;
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
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;



    long lastTap = System.currentTimeMillis();




    //array for bullets




    public PlayScreen(MarioBros game){






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
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / MarioBros.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);



        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10.0F), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //create mario in our game world
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioBros.manager.get("audio/music/exorcist.mp3", Music.class);
        MarioBros.manager.get("audio/music/exorcist.mp3", Music.class).setVolume(.1f);
        music.setLooping(true);

        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();



    }

    @Override
    public void show() {


    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }


    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
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




    public void handleInput(float dt) {
        if(player.currentState != Mario.State.DEAD) {



              if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
                  player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
              }













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
                if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
                    ((Sound) MarioBros.manager.get("audio/sounds/Link_jump.wav", Sound.class)).play();
                    player.b2body.applyLinearImpulse(new Vector2(0, 7f), player.b2body.getWorldCenter(), true);

                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                    player.fire();


            }



        }



            /*if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.player.b2body.getLinearVelocity().x <= 2.0F) {
                this.player.b2body.applyLinearImpulse(new Vector2(0.3F, 0.0F), this.player.b2body.getWorldCenter(), true);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.player.b2body.getLinearVelocity().x >= -2.0F) {
                this.player.b2body.applyLinearImpulse(new Vector2(-0.3F, 0.0F), this.player.b2body.getWorldCenter(), true);
            }*/
    }



    public void update(float dt){
        //handle user input first
        handleInput(dt);
        handleInput1(dt);
        handleSpawningItems();






        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 400 / MarioBros.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        for(Item item : items)
            item.update(dt);


        //bullets
        //render







        //bullets

        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Mario.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
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
        for (Enemy enemy : creator.getEnemies())
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
        ;
    }





    public Hud getHud(){ return hud; }
}
