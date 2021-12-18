package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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


public class PreferencesScreen implements Screen {
    private GardenGame app;
    private Stage stage;

    private Label titleLabel, volumeMusicLabel, volumeSoundLabel, volumeSoundEffectLabel, musicOnOffLabel, soundOnOffLabel, soundEffectTestLabel;

    private TextButton soundAmbientBirdTestButton, soundSoundEffectTestButton;

    private Slider volumeMusicSlider;
    private CheckBox musicCheckbox;
    private Slider dialougeSpeedSlider;


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
                app.sound.SoundButtonClick();
                app.sound.Play_Pause_Music();
            }
        });

            dialougeSpeedSlider = new Slider(0f,1f,0.1f,false,skin);
            //dialougeSpeedSlider.setValue()
            //Volume Music
            volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
            volumeMusicSlider.setValue( app.assets.menuMusic.getVolume() );
            volumeMusicSlider.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    app.assets.menuMusic.setVolume( volumeMusicSlider.getValue() );
                    // Need fix
                    app.assets.inGameMusic.setVolume( volumeMusicSlider.getValue() );

                    // Global Value
                    app.musicVolume = volumeMusicSlider.getValue();
                    return false;
                }
            });

            //Music
            musicCheckbox = new CheckBox(null, skin);
            musicCheckbox.setChecked( app.assets.menuMusic.isPlaying() );
            musicCheckbox.addListener( new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = musicCheckbox.isChecked();
                    if (enabled)
                        app.assets.menuMusic.play();
                    else
                        app.assets.menuMusic.pause();

                    return false;
                }
            });


        //volume Sound
        final Slider ambientBirdMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        ambientBirdMusicSlider.setValue( app.assets.ambientSound_Bird.getVolume() );
        ambientBirdMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.assets.ambientSound_Bird.setVolume( ambientBirdMusicSlider.getValue() );
                return false;
            }
        });

        //Sound
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( app.assets.ambientSound_Bird.isPlaying() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                if (enabled)
                    app.assets.ambientSound_Bird.play();
                else
                    app.assets.ambientSound_Bird.pause();

                return false;
            }
        });

        //Sound ambientBird Test Button

        soundAmbientBirdTestButton = new TextButton("Test Ambient Sound",skin);
        soundAmbientBirdTestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.sound.SoundButtonClick();
                app.sound.Play_Pause_Ambient_Sound();
            }
        });

        //volume Sound
        final Slider soundEffectSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundEffectSlider.setValue( app.assets.ambientSound_Bird.getVolume() );
        soundEffectSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                app.assets.soundNewDay.setVolume( soundEffectSlider.getValue() );
                app.assets.soundGetWater.setVolume( soundEffectSlider.getValue() );
                app.assets.soundUseWater.setVolume( soundEffectSlider.getValue() );
                app.assets.soundButtonPress.setVolume( soundEffectSlider.getValue() );
                app.assets.soundUseGold.setVolume( soundEffectSlider.getValue() );
                return false;
            }
        });


        //Sound Effect Test Button

        soundSoundEffectTestButton = new TextButton("Test Sound Effect",skin);
        soundSoundEffectTestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.sound.SoundButtonClick();
                app.sound.Play_Pause_EffektBeat_Sound();
            }
        });


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BackButton();
            }
        });


        titleLabel = new Label( "Preferences", app.assets.largeTextStyle);
        titleLabel.setFontScale(6);
        volumeMusicLabel = new Label( "Music Volume", app.assets.largeTextStyle );
        volumeSoundLabel = new Label( "Ambient Volume", app.assets.largeTextStyle );
        volumeSoundEffectLabel = new Label( "Sound Effect Volume", app.assets.largeTextStyle );
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
        table.add(ambientBirdMusicSlider).colspan(1);
        table.add(soundAmbientBirdTestButton);
        table.row();
        // ---------
        table.add(volumeSoundEffectLabel).left();
        table.add(soundEffectSlider).colspan(1);
        table.add(soundSoundEffectTestButton);
        table.row();
        //table.add(soundOnOffLabel).left();
        //table.add(soundEffectsCheckbox);
        //table.row();
        table.add(backButton).colspan(6).center();

        stage.addActor(musicButton);

    }

    public void BackButton(){
        app.sound.SoundButtonClick();
        app.sound.Stop_EffektBeat_Sound();
        app.sound.Stop_AmbientBird_Sound();

        if (app.preferencesBool)
            app.setScreen(app.pauseScreen);
        else{
            app.setScreen(app.titleScreen);
        }
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) BackButton();


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


