package com.garden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
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

	public Music music;
	public Boolean preferencesBool = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();

		/* --------- Music setup  ---------  */
		music = Gdx.audio.newMusic(Gdx.files.internal("PetParkMusic.mp3"));
		music.setLooping(true);
		//music.setVolume(1.0f);  //1.0f max Volume
		music.play();


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
