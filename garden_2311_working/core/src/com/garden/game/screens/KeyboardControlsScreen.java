package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;

public class KeyboardControlsScreen implements Screen {

    private GardenGame app;
    private Stage hud;

    private Image imgKeyboardControls;


    public KeyboardControlsScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new Stage(new ScreenViewport(camera));

        initStage();


    }
    private void initStage() {

        imgKeyboardControls = new Image(new TextureRegion(app.assets.<Texture>get("KeyboardControls.png")));
        imgKeyboardControls.setSize(844,284);
        imgKeyboardControls.setPosition(100, 250);

        hud.addActor(imgKeyboardControls);

    }

    @Override
    public void show() {
        //Gdx.input.setInputProcessor(hud);
    }

    private void backToMenue(){

        app.sound.SoundButtonClick();

        hud.dispose();
        app.sound.SoundButtonClick();
        app.setScreen(app.pauseScreen);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backToMenue();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.act();
        hud.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        hud.dispose();
    }
}


