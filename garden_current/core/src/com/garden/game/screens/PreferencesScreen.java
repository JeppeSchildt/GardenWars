package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;

import java.sql.Time;


public class PreferencesScreen implements Screen {
    private GardenGame app;
    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private Label soundEffectTestLabel;

    private TextButton soundEffectsTestButton;

    private Slider volumeMusicSlider;
    private CheckBox musicCheckbox;


    // Once this reaches 1.0f the next scene is shown
    private float alpha = 0;
    // true if fade in, false if fade out
    private boolean fadeDirection = true;

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

        TextButton musicButton = new TextButton("Music",skin);
        musicButton.setPosition(app.maxWidth - 75, 15);
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.soundButtonPress.play();
                if (app.inGameMusic.isPlaying())
                    app.inGameMusic.pause();
                else if (!app.inGameMusic.isPlaying())
                    app.inGameMusic.play();
                else if (app.menuMusic.isPlaying())
                    app.menuMusic.pause();
                else if (!app.menuMusic.isPlaying())
                    app.menuMusic.play();
            }
        });

        if (!app.preferencesBool){
            //Volume Music
            volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
            volumeMusicSlider.setValue( app.menuMusic.getVolume() );
            volumeMusicSlider.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    app.menuMusic.setVolume( volumeMusicSlider.getValue() );
                    // Need fix
                    app.inGameMusic.setVolume( volumeMusicSlider.getValue() );

                    // Global Value
                    app.musicVolume = volumeMusicSlider.getValue();
                    return false;
                }
            });

            //Music
            musicCheckbox = new CheckBox(null, skin);
            musicCheckbox.setChecked( app.menuMusic.isPlaying() );
            musicCheckbox.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = musicCheckbox.isChecked();
                    if (enabled)
                        app.menuMusic.play();
                    else
                        app.menuMusic.pause();

                    return false;
                }
            });
        }else if (app.preferencesBool){
            //Volume Music
            final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
            volumeMusicSlider.setValue( app.inGameMusic.getVolume() );
            volumeMusicSlider.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    app.inGameMusic.setVolume( volumeMusicSlider.getValue() );
                    return false;
                }
            });

            //music
            final CheckBox musicCheckbox = new CheckBox(null, skin);
            musicCheckbox.setChecked( app.inGameMusic.isPlaying() );
            musicCheckbox.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = musicCheckbox.isChecked();
                    if (enabled)
                        app.inGameMusic.play();
                    else
                        app.inGameMusic.pause();
                    return false;
                }
            });
        }

        //volume Sound
        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( app.soundEffectBird.getVolume() );
        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.soundEffectBird.setVolume( soundMusicSlider.getValue() );
                return false;
            }
        });

        //Sound
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( app.soundEffectBird.isPlaying() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                if (enabled)
                    app.soundEffectBird.play();
                else
                    app.soundEffectBird.pause();

                return false;
            }
        });

        //Sound Effect Test Button

        soundEffectsTestButton = new TextButton("Test Sound Effect",skin);
        soundEffectsTestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (app.soundEffectBird.isPlaying())
                    app.soundEffectBird.stop();
                else{
                    if (app.soundEffectBird.isLooping())
                        app.soundEffectBird.setLooping(false);
                        app.soundEffectBird.play();
                }

            }
        });


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.soundButtonPress.play();
                if (app.preferencesBool)
                    app.setScreen(app.pauseScreen);
                else{
                    if (app.inGameMusic.isPlaying()){
                        app.inGameMusic.stop();
                        app.soundEffectBird.stop();

                        // ---------- In game sound start ----------
                        if (app.menuMusic.isPlaying())
                        {
                            app.menuMusic.stop();
                            // Start playing music after X time
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    app.menuMusic.play();
                                    if (!app.menuMusic.isLooping())
                                        app.menuMusic.setLooping(true);
                                }
                            }, 0.5f);
                        }
                    }
                    app.setScreen(app.titleScreen);

                }

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
        //table.add(musicOnOffLabel).left();
        //table.add(musicCheckbox);
        //table.row();
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider).colspan(1);
        table.add(soundEffectsTestButton);
        table.row();
        //table.add(soundOnOffLabel).left();
        //table.add(soundEffectsCheckbox);
        //table.row();
        table.add(backButton).colspan(6).center();

        stage.addActor(musicButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

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


