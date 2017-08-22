package com.viktorkrum.mariobros.OverlayDemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viktorkrum.mariobros.Sprites.Mario;

/**
 * Created by ViktorKrum on 8/7/2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage1;
    long lastTap = System.currentTimeMillis();
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;




    public Controller(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 480, cam);
        stage1 = new Stage(viewport, com.viktorkrum.mariobros.Screens.PlayScreen.batch);

        stage1.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                   case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }
        });










        Gdx.input.setInputProcessor(stage1);

        Table table = new Table();
        Table table1 = new Table();
        table1.center().bottom();


        table.left().bottom();


        Image jumpImg = new Image(new Texture("jump1.png"));
        jumpImg.setSize(155, 155);
        jumpImg.addListener(new InputListener() {








            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });



        Image upImg = new Image(new Texture("up.png"));
        upImg.setSize(85, 85);
        upImg.addListener(new InputListener() {








            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("down.png"));
        downImg.setSize(135, 135);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("right.png"));

        rightImg.setSize(120, 120);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("left.png"));
        leftImg.setSize(120, 120);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        table.add();
        //table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().padBottom(20);
        table.add(leftImg).padRight(20).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(20);
        table.add();

        table.add();

        table1.add();
        table1.add(downImg).padRight(35).size(downImg.getWidth(), downImg.getHeight());
        table1.row().padBottom(10);

        table1.add(jumpImg).size(jumpImg.getWidth(), jumpImg.getHeight());
        table1.add();

        table1.center().padBottom(20).bottom().padLeft(1380);



        stage1.addActor(table);
        stage1.addActor(table1);
    }

    public void draw(){
        stage1.draw();

    }

    public boolean isUpPressed() {
        return upPressed;
    }
    public void clearUpPressed() {
             upPressed = false;
           }



    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
