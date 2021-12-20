package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GameOverScreen implements Screen {

    private GardenGame app;
    private Stage stage;

    private Table table;

    private float score, highestScore;

    Skin skin;

    public GameOverScreen(GardenGame app, float score) {
        this.app = app;
        this.score = score;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));

        initStage();
       // E:/computerspille/GardenWars/garden_2311_working/core/assets
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:/computerspille/GardenWars/garden_2311_working/core/assets/scores.txt"), StandardCharsets.UTF_8))) {
            writer.write(score + "\n");
            System.out.println("Score" + score);
        }
        catch (IOException ex) {
            System.out.println("nope");
        }


        // Get highscore from save file
        Preferences prefs = Gdx.app.getPreferences("gardengame");
        this.highestScore = prefs.getFloat("highestscore", 0);

        // Check if score beats highscore
        if (score > highestScore) {
            prefs.putFloat("highscore", score);
            prefs.flush();
        }


    }
    private void initStage() {

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label titleText = new Label("GAME OVER!", app.assets.largeTextStyle);
        titleText.setFontScale(5);

        Label scoreText = new Label("Score: " + score, app.assets.largeTextStyle);
        Label highscoreText = new Label("highest Score: " + highestScore, app.assets.largeTextStyle);


        TextButton newgameButton = new TextButton("New Game",skin);
        newgameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newGame();
            }
        });

        TextButton mainMenuButton = new TextButton("Main Menu",skin);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMenue();
            }
        });


        table.add(titleText).center();
        table.row();
        table.add(scoreText).left();
        table.row();
        table.add(highscoreText).left();

        table.row();
        table.add(newgameButton).center();
        table.row();
        table.add(mainMenuButton).center();

    }


    @Override
    public void show() {

        app.sound.GameOver_Sound();
        app.preferencesBool = false;

        Gdx.input.setInputProcessor(stage);

        /*
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                app.setScreen(app.titleScreen);
                app.sound.Play_Music();
            }
        },4.2f);
         */

    }

    private void newGame(){
        app.sound.SoundButtonClick();

        app.currentGameBool = false;
        app.preferencesBool = true;

        app.sound.Play_Music();
        app.sound.SoundButtonClick();
        app.setScreen(app.gameScreen);
        app.gameScreen.world.init("World.tmx");
    }
    private void backToMenue(){

        app.sound.SoundButtonClick();

        app.currentGameBool = true;
        app.preferencesBool = false;

        stage.dispose();
        app.sound.Play_Music();
        app.sound.SoundButtonClick();
        app.setScreen(app.titleScreen);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) app.setScreen(app.gameScreen);

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


