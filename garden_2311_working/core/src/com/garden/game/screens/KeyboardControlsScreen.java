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

    private Skin skin;


    public KeyboardControlsScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new Stage(new ScreenViewport(camera));

        initStage();


    }
    private void initStage() {

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        imgKeyboardControls = new Image(new TextureRegion(app.assets.<Texture>get("KeyboardControls.png")));
        imgKeyboardControls.setPosition(10, 200);

        hud.addActor(imgKeyboardControls);

        TextButton backButton = new TextButton("Back",skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMenue();
            }
        });

        backButton.setPosition(app.maxWidth/2, 85);
        hud.addActor(backButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud);
    }

    private void backToMenue(){
        app.sound.SoundButtonClick();
        //hud.dispose();
        if(app.pauseScreen == null) {
            app.pauseScreen = new PauseScreen(app);
        }
        app.setScreen(app.pauseScreen);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.act();
        hud.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            backToMenue();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            app.sound.SoundButtonClick();

            app.setScreen(app.gameScreen);
        }
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


