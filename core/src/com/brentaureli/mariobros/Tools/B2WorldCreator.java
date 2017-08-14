package com.brentaureli.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Enemies.Diablo;
import com.brentaureli.mariobros.Sprites.Enemies.Enemy;
import com.brentaureli.mariobros.Sprites.Enemies.Fire;
import com.brentaureli.mariobros.Sprites.Enemies.Portal;
import com.brentaureli.mariobros.Sprites.Enemies.Rain;
import com.brentaureli.mariobros.Sprites.Enemies.Skull;
import com.brentaureli.mariobros.Sprites.Enemies.Sorc;
import com.brentaureli.mariobros.Sprites.Enemies.Turtle;
import com.brentaureli.mariobros.Sprites.TileObjects.Brick;
import com.brentaureli.mariobros.Sprites.TileObjects.Coin;
import com.brentaureli.mariobros.Sprites.Enemies.Goomba;

/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
    private Array<Goomba> goombas;
    private Array<Turtle> turtles;
    private Array<Skull> skulls;
    private Array<Diablo> diablos;
    private Array<Sorc> sorcs;
    private Array<Fire> fires;
    private Array<Rain> rains;
    private Array<Portal> portals;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MarioBros.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen, object);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new Coin(screen, object);
        }

        //create all goombas
        goombas = new Array<Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }


        // //create all Diablo
        diablos = new Array<Diablo>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            diablos.add(new Diablo(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        // //create all Sorcs
        sorcs = new Array<Sorc>();
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            sorcs.add(new Sorc(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        // //create all portals
        portals = new Array<Portal>();
        for(MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            portals.add(new Portal(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }



        // //create all Rain
        rains = new Array<Rain>();
        for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rains.add(new Rain(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }


        // //create all Fire
        fires = new Array<Fire>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            fires.add(new Fire(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        //create all skulls
        skulls = new Array<Skull>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            skulls.add(new Skull(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }
    public Array<Skull> getSkulls() {
        return skulls;
    }

    public Array<Diablo> getDiablos() {
        return diablos;
    }
    public Array<Sorc> getSorcs() {
        return sorcs;
    }
    public Array<Fire> getFires(){ return  fires;}
    public Array<Rain> getRains(){ return rains;}
    public Array<Portal> getPortals(){ return portals;}


    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        enemies.addAll(skulls);
        enemies.addAll(diablos);
        enemies.addAll(sorcs);
        enemies.addAll(fires);
        enemies.addAll(rains);
        enemies.addAll(portals);
        return enemies;
    }
}
