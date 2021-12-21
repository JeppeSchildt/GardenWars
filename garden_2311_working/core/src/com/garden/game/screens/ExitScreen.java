package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

public class ExitScreen implements Screen {

    private GardenGame app;
    private Stage stage;

    private Table table;

    public static String[] credits_strs = {
            "Thank you so much for playing",
            "Hope to see you again",
            "",
            "CREDITS",
            "========",
            "MUSIC BY:      Playonloop",
            "               Mixkit",
            "",
            "PROGAMMED BY:  NAMES_XXX",
            "               NAMES_XXX",
            "               NAMES_XXX",
            "               NAMES_XXX",
    };



    Skin skin;
    public ExitScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

        Label title = new Label("Thank you so much for playing Park Life", app.assets.largeTextStyle);
        title.setFontScale(2);
        Label titleSub = new Label("Hope to see you again", app.assets.largeTextStyle);
        titleSub.setFontScale(2);

        table.add(title).center();
        table.row();
        table.add(titleSub).center();

        /*
        String  reallyLongString = "This is a really long string that has lots of lines and repeats itself over and over again This is a really long string that has\" +\n" +
                "\" lots of lines and repeats itself over and over again This is a really long string that has lots of lines and repeats itself over and over\"+\n" +
                "\" again This is a really long string that has lots of lines and repeats itself over and over again\"+\n" +
                "\" This is a really long string that has lots of lines and repeats itself over and over again This is a really long string that has lots\"+\n" +
                "\" of lines and repeats itself over and over again";

        ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);

        table = new Table();
        table.setFillParent(true);
        table.add(scroll).expandX().left().width(100);
        stage.addActor(table);

        scroll.setWidth(app.maxWidth);
        scroll.hasScrollFocus();
        scroll.add(reallyLongString);

         */
    }



    @Override
    public void show() {
        app.sound.EndGame_Sound();

        Gdx.input.setInputProcessor(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        },8.5f);

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

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


