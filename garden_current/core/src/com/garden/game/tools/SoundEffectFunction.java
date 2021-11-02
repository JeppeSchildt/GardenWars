package com.garden.game.tools;

import com.badlogic.gdx.utils.Timer;
import com.garden.game.GardenGame;

import java.sql.Time;

public class SoundEffectFunction {


    private GardenGame app;

    public SoundEffectFunction(GardenGame app) {
        this.app = app;

    }


    /* -------- Music Function Setup -------- */
    public void Chance_Music(){

        if(app.assets.inGameMusic.isPlaying()) {
            app.assets.inGameMusic.pause();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.assets.menuMusic.play();
                }
            },0.5f);


        }
        else if(app.assets.menuMusic.isPlaying()) {
            app.assets.menuMusic.pause();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.assets.inGameMusic.play();
                    Play_Ambient_Sound();
                }
            },0.5f);

        }
    }


    public void Play_Pause_Music(){
        if (app.preferencesBool){
            if(app.assets.inGameMusic.isPlaying()){
                app.assets.inGameMusic.stop();
            }
            else if(!app.assets.inGameMusic.isPlaying()){
                System.out.println("Playing: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
                app.assets.inGameMusic.play();
                app.assets.inGameMusic.setLooping(true);
            }

        }
        else if (!app.preferencesBool){
            if(app.assets.menuMusic.isPlaying()){
                app.assets.menuMusic.stop();
            }
            else if(!app.assets.menuMusic.isPlaying()){
                System.out.println("Playing: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
                app.assets.menuMusic.play();
                app.assets.menuMusic.setLooping(true);
            }
        }
    }


    public void Play_Music(){
        if (app.preferencesBool){
            if(!app.assets.inGameMusic.isPlaying()){
                System.out.println("Playing: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
                app.assets.inGameMusic.play();
                app.assets.inGameMusic.setLooping(true);
            }

        }
        else if (!app.preferencesBool){
            if(!app.assets.menuMusic.isPlaying()){
                System.out.println("Playing: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
                app.assets.menuMusic.play();
                app.assets.menuMusic.setLooping(true);
            }
        }
    }

    public void Pause_Music(){
        if (app.preferencesBool){
            if(app.assets.inGameMusic.isPlaying()){
                app.assets.inGameMusic.pause();
                System.out.println("Pause: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
            }

        }
        else if (!app.preferencesBool){
            if(app.assets.menuMusic.isPlaying()){
                app.assets.menuMusic.pause();
                System.out.println("Pause: "+ app.assets.inGameMusic + " - " + "PreferencesBool = " + app.preferencesBool);
            }
        }
    }


    public void Play_Ambient_Sound(){
        if(!app.assets.ambientSound_Bird.isPlaying()){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.assets.ambientSound_Bird.play();
                }
            },1.5f);
            app.assets.ambientSound_Bird.setLooping(true);
        }
    }

    public void Pause_Ambient_Sound(){
        if (app.assets.ambientSound_Bird.isPlaying()){
            app.assets.ambientSound_Bird.pause();
        }

    }


    public void Play_Pause_Ambient_Sound(){
        if (app.assets.ambientSound_Bird.isPlaying()){
            app.assets.ambientSound_Bird.stop();
        }
        else if(!app.assets.ambientSound_Bird.isPlaying()){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.assets.ambientSound_Bird.play();
                }
            },1.5f);
            app.assets.ambientSound_Bird.setLooping(true);
        }
    }



    public void GameOver_Sound(){
        StopAll();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                app.assets.soundGameOver.play();
            }
        },0.3f);

    }

    public void EndGame_Sound(){
        StopAll();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                app.assets.soundEnd.play();
            }
        },0.3f);

    }



    public void StopAll(){
        app.assets.inGameMusic.stop();
        app.assets.ambientSound_Bird.stop();

        app.assets.menuMusic.stop();
    }

    /* -------- Sound Effect Function Setup -------- */
    public void buttonMenueSound(){
        app.assets.soundButtonPress.play();
    }


}
