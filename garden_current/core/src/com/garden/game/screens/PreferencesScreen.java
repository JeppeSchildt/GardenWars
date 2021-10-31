package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

import java.awt.*;


public class PreferencesScreen implements Screen {
    private GardenGame app;
    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    Skin skin;

    public PreferencesScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));

        initStage();

    }

    private void initStage() {

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();

        table.setFillParent(true);
        table.setDebug(false);
        table.columnDefaults(6);
        stage.addActor(table);



        skin = new Skin(Gdx.files.internal("uiskin.json"));


        //volume Music
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        volumeMusicSlider.setValue( app.music.getVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.music.setVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( app.music.isPlaying() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                if (enabled)
                    app.music.play();
                else
                    app.music.pause();

                return false;
            }
        });



        //volume Sound
        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( app.soundNextTurn.getVolume() );
        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.soundNextTurn.setVolume( soundMusicSlider.getValue() );
                return false;
            }
        });

        //Sound
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( app.soundNextTurn.isPlaying() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                if (enabled)
                    app.soundNextTurn.play();
                else
                    app.soundNextTurn.pause();

                return false;
            }
        });



        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (app.preferencesBool)
                    app.setScreen(app.pauseScreen);
                else
                    app.setScreen(app.titleScreen);
            }
        });


        titleLabel = new Label( "Preferences", app.assets.largeTextStyle);
        titleLabel.setFontScale(6);
        volumeMusicLabel = new Label( "Music Volume", app.assets.largeTextStyle );
        volumeSoundLabel = new Label( "Sound Volume", app.assets.largeTextStyle );
        musicOnOffLabel = new Label( "Music", app.assets.largeTextStyle );
        soundOnOffLabel = new Label( "Sound Effect", app.assets.largeTextStyle );

        table.add(titleLabel).colspan(6).center();
        table.row();
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row();
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(backButton).colspan(2).center();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
