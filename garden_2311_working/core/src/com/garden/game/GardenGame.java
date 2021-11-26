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
	public Screen titleScreen, pauseScreen, preferencesScreen, exitScreen, gameOverScreen, weekDayScreen, skillTreeScreen;
	public GameScreen gameScreen;

	public float musicVolume = 1.0f;


	public SoundFunctions sound;

	public Boolean preferencesBool, currentGameBool, drySeason, debugMode;
	public int maxWidth, maxHeight, score;


	@Override
	public void create () {
		batch = new SpriteBatch(); // Only use this one SpriteBatch
		assets = new Assets();

		sound = new SoundFunctions(this);

		maxWidth = Gdx.graphics.getWidth();
		maxHeight = Gdx.graphics.getHeight();
		preferencesBool = false;
		currentGameBool = false;

		debugMode = false;

		//-- Event --
		drySeason = false;

		/* --------- Screen setup  ---------  Lazy loading, loading them we need them might be better. */
		titleScreen = new TitleScreen(this);


		setScreen(titleScreen);
	}


	@Override
	public void dispose () {
		batch.dispose();
		assets.unloadAll();
		assets.dispose();

	}
}
