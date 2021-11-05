package com.garden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.garden.game.screens.*;
import com.garden.game.tools.Assets;
import com.garden.game.tools.SoundFunctions;

public class GardenGame extends Game {
	public SpriteBatch batch;
	public Assets assets;
	public Screen titleScreen, pauseScreen, preferencesScreen, exitScreen, gameOverScreen, weekDayScreen;
	public GameScreen gameScreen;

	public float musicVolume = 1.0f;


	public SoundFunctions sound;

	public Boolean preferencesBool = false, currentGameBool = false;

	public int maxWidth, maxHeight, score;


	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();

		sound = new SoundFunctions(this);

		maxWidth = Gdx.graphics.getWidth();
		maxHeight = Gdx.graphics.getHeight();



		/*
		// --------- InGameMusic setup  ---------
		inGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/PetParkMusic.mp3"));
		inGameMusic.setLooping(true);
		inGameMusic.setVolume(musicVolume);

		// --------- InGameMusic setup  ---------
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Cooking-mania-short.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(musicVolume);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				menuMusic.play();
			}
		}, 0.5f);
		 */


		/*
		// --------- Sound Effect setup  ---------
		soundEffectBird = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/POL-morning-birds.mp3"));
		soundEffectBird.setVolume(0.5f); //1.0f max
		soundEffectBird.setLooping(true);

		//soundButtonPress = Gdx.audio.newSound(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-flute-alert-2307.mp3"));
		soundButtonPress = Gdx.audio.newSound(Gdx.files.internal("soundEffect/ButtonPressSound_mixkit-game-ball-tap-2073.mp3"));
		soundEnd = Gdx.audio.newSound(Gdx.files.internal("soundEffect/EndSound_mixkit-medieval-show-fanfare.mp3"));
		soundGameOver = Gdx.audio.newSound(Gdx.files.internal("soundEffect/GameOver_mixkit-game-over-trombone-1940.mp3"));
		 */



		/* --------- Screen setup  ---------  */
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		pauseScreen = new PauseScreen(this);
		preferencesScreen = new PreferencesScreen(this);
		gameOverScreen = new GameOverScreen(this, score);
		exitScreen = new ExitScreen(this);

		weekDayScreen = new WeekDayScreen(this);


		setScreen(titleScreen);

	}


	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
