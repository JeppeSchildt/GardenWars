package com.garden.game.tools;

import com.badlogic.gdx.utils.Timer;
import com.garden.game.GardenGame;

import java.sql.Time;

public class SoundFunctions {


    private GardenGame app;

    public SoundFunctions(GardenGame app) {
        this.app = app;

    }


    /* -------- Music Function Setup -------- */
    public void Chance_Music(){

        if(app.assets.inGameMusic.isPlaying()) {
            app.assets.inGameMusic.stop();
            Pause_Ambient_Sound();

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.assets.menuMusic.play();
                }
            },0.5f);

        }
        else if(app.assets.menuMusic.isPlaying()) {
            app.assets.menuMusic.stop();

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


    public void Chance_InGameMusic(){
        if (app.assets.inGameMusic.isPlaying()){

            app.assets.inGameMusic.stop();
            app.assets.ambientSound_Bird.stop();

            app.assets.inGameDryMusic.play();
            app.assets.ambientSound_Crickets.play();
        }
        else if (app.assets.inGameDryMusic.isPlaying()){
            app.assets.inGameDryMusic.stop();
            app.assets.ambientSound_Crickets.stop();

            app.assets.inGameMusic.play();
            app.assets.ambientSound_Bird.play();
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
            app.assets.ambientSound_Bird.stop();
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

    public void Stop_AmbientBird_Sound(){
        app.assets.ambientSound_Bird.stop();
    }


    public void Play_Pause_EffektBeat_Sound(){
        if (app.assets.soundTestEffektBeat.isPlaying()){
            app.assets.soundTestEffektBeat.stop();
        }
        else if(!app.assets.soundTestEffektBeat.isPlaying()){
            app.assets.soundTestEffektBeat.play();
            app.assets.soundTestEffektBeat.setLooping(true);
        }
    }

    public void Stop_EffektBeat_Sound(){
        app.assets.soundTestEffektBeat.stop();
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
    public void SoundButtonClick(){
        app.assets.soundButtonPress.play();
    }

    public void SoundUseWater(){
        app.assets.soundUseWater.play();
    }
    public void SoundGetWater(){
        app.assets.soundGetWater.play();
    }
    public void SoundUseGold(){
        app.assets.soundUseGold.play();
    }
    public void SoundNewDay(){
        app.assets.soundNewDay.play();
    }





}
