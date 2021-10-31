package com.garden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.garden.game.screens.GameScreen;
import com.garden.game.screens.PauseScreen;
import com.garden.game.screens.PreferencesScreen;
import com.garden.game.screens.TitleScreen;
import com.garden.game.tools.Assets;

public class GardenGame extends Game {
	public SpriteBatch batch;
	public Assets assets;
	public Screen titleScreen, pauseScreen, preferencesScreen;
	public GameScreen gameScreen;

	public Music music, soundNextTurn;

	public Boolean preferencesBool = false;
	public Boolean currentGameBool = false;
	public int maxWidth, maxHeight;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();

		/* --------- Music setup  ---------  */
		music = Gdx.audio.newMusic(Gdx.files.internal("music/PetParkMusic.mp3"));
		music.setLooping(true);
		//music.setVolume(1.0f);  //1.0f max Volume

		//music.setVolume(0);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				//music.play();
			}
		}, 0.5f);

		/*
		// Start playing music after X time
		for (float i = 0; i < 1.0f;) {
			final float finalI = i;
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					music.setVolume(finalI);
					System.out.println("Music volume: " +finalI);
				}
			}, 0.3f);
			i += 0.1f;
		}
		 */


		/* --------- Music setup  ---------  */
		soundNextTurn = Gdx.audio.newMusic(Gdx.files.internal("soundEffect/POL-morning-birds.mp3"));

		maxWidth = Gdx.graphics.getWidth();
		maxHeight = Gdx.graphics.getHeight();


		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		pauseScreen = new PauseScreen(this);
		preferencesScreen = new PreferencesScreen(this);

		setScreen(titleScreen);


	}


	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
