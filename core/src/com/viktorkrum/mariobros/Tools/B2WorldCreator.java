package com.viktorkrum.mariobros.Tools;

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
import com.viktorkrum.mariobros.MarioBros;
import com.viktorkrum.mariobros.Sprites.Enemies.Diablo;
import com.viktorkrum.mariobros.Sprites.Enemies.Doom;
import com.viktorkrum.mariobros.Sprites.Enemies.Fire;
import com.viktorkrum.mariobros.Sprites.Enemies.Skull;
import com.viktorkrum.mariobros.Sprites.Enemies.Sorc;
import com.viktorkrum.mariobros.Sprites.Enemies.Tree;
import com.viktorkrum.mariobros.Sprites.TileObjects.Brick;

/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
    private Array<com.viktorkrum.mariobros.Sprites.Enemies.Goomba> goombas;
    private Array<com.viktorkrum.mariobros.Sprites.Enemies.Turtle> turtles;
    private Array<Skull> skulls;
    private Array<Doom> dooms;
    private Array<Diablo> diablos;
    private Array<Sorc> sorcs;
    private Array<Fire> fires;
    private Array<Tree> trees;
    private Array<com.viktorkrum.mariobros.Sprites.Enemies.Rain> rains;
    private Array<com.viktorkrum.mariobros.Sprites.Enemies.Portal> portals;

    public B2WorldCreator(com.viktorkrum.mariobros.Screens.PlayScreen screen){
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

            new com.viktorkrum.mariobros.Sprites.TileObjects.Coin(screen, object);
        }

        //create all goombas
        goombas = new Array<com.viktorkrum.mariobros.Sprites.Enemies.Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new com.viktorkrum.mariobros.Sprites.Enemies.Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
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
        portals = new Array<com.viktorkrum.mariobros.Sprites.Enemies.Portal>();
        for(MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            portals.add(new com.viktorkrum.mariobros.Sprites.Enemies.Portal(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }



        // //create all Rain
        rains = new Array<com.viktorkrum.mariobros.Sprites.Enemies.Rain>();
        for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rains.add(new com.viktorkrum.mariobros.Sprites.Enemies.Rain(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
        // //create all Tree
        trees = new Array<Tree>();
        for(MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            trees.add(new Tree(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }


        // //create all Fire
        fires = new Array<Fire>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            fires.add(new Fire(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        //create all dooms
        dooms = new Array<Doom>();
        for(MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            dooms.add(new Doom(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }



        //create all skulls
        skulls = new Array<Skull>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            skulls.add(new Skull(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
        turtles = new Array<com.viktorkrum.mariobros.Sprites.Enemies.Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new com.viktorkrum.mariobros.Sprites.Enemies.Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
    }

    public Array<com.viktorkrum.mariobros.Sprites.Enemies.Goomba> getGoombas() {
        return goombas;
    }
    public Array<Skull> getSkulls() {
        return skulls;
    }
    public Array<Doom> getDooms(){ return dooms;}

    public Array<Diablo> getDiablos() {
        return diablos;
    }
    public Array<Sorc> getSorcs() {
        return sorcs;
    }
    public Array<Fire> getFires(){ return  fires;}
    public Array<Tree> getTrees(){ return trees;}
    public Array<com.viktorkrum.mariobros.Sprites.Enemies.Rain> getRains(){ return rains;}
    public Array<com.viktorkrum.mariobros.Sprites.Enemies.Portal> getPortals(){ return portals;}


    public Array<com.viktorkrum.mariobros.Sprites.Enemies.Enemy> getEnemies(){
        Array<com.viktorkrum.mariobros.Sprites.Enemies.Enemy> enemies = new Array<com.viktorkrum.mariobros.Sprites.Enemies.Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        enemies.addAll(skulls);
        enemies.addAll(dooms);
        enemies.addAll(diablos);
        enemies.addAll(sorcs);
        enemies.addAll(fires);
        enemies.addAll(rains);
        enemies.addAll(portals);
        enemies.addAll(trees);
        return enemies;
    }
}
