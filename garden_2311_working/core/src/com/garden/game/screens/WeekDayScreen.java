package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

public class WeekDayScreen implements Screen {
    private GardenGame app;
    public World world;
    private Stage stage;
    private Table table;

    public Label title;

    Skin skin;
    public WeekDayScreen(GardenGame app) {
        this.app = app;
        world = new World(app);

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage = new Stage(new ScreenViewport(camera));

        initStage();
    }

    private void initStage() {

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        title = new Label("Week: " + world.weekCount, app.assets.largeTextStyle);
        title.setFontScale(5);

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        table.add(title).colspan(2).center();
        table.row();
        table.add(backButton).colspan(6).center();

    }

    private void resumeGame(){
        app.sound.SoundButtonClick();
        app.setScreen(app.gameScreen);
    }

    public void updateHUD() {

        title.setText("Week: " + world.weekCount);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        updateHUD();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) resumeGame();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) resumeGame();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) resumeGame();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

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
        stage.dispose();
    }


}
