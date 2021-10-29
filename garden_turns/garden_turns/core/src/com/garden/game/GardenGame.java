package com.garden.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.garden.game.screens.GameScreen;
import com.garden.game.screens.PauseScreen;
import com.garden.game.screens.TitleScreen;
import com.garden.game.tools.Assets;

public class GardenGame extends Game {
	public SpriteBatch batch;
	public Assets assets;
	public Screen titleScreen, pauseScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();

		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);
		pauseScreen = new PauseScreen(this);

		setScreen(titleScreen);
	}


	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
