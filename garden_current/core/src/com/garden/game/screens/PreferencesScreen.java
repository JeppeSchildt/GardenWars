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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;


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
        table.setDebug(true);
        stage.addActor(table);




        skin = new Skin(Gdx.files.internal("uiskin.json"));



        //volume Music
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
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
                app.music.play();
                return false;
            }
        });



        //volume Sound
        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( app.music.getVolume() );
        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.music.setVolume( soundMusicSlider.getValue() );
                return false;
            }
        });

        //Sound
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( app.music.isPlaying() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                app.music.play();
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


        titleLabel = new Label( "Preferences", skin );
        volumeMusicLabel = new Label( null, skin );
        volumeSoundLabel = new Label( null, skin );
        musicOnOffLabel = new Label( null, skin );
        soundOnOffLabel = new Label( null, skin );

        table.add(titleLabel);
        table.row();
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel);
        table.add(soundMusicSlider);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(backButton);

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
