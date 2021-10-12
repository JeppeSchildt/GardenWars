package com.garden.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.garden.game.screens.GameScreen;
import com.garden.game.tools.Assets;

public class GardenGame extends Game {
	public SpriteBatch batch;
	Texture img;
	public TextureAtlas textureAtlas;
	public Sprite spritePlayer;
	public Sprite spriteHighlight;
	//OrthographicCamera camera;
	//public AssetManager assets;
	public Assets assets;
	public GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//assets = new AssetManager();
		assets = new Assets();
		//assets.load("pack5.atlas", TextureAtlas.class);
		//assets.finishLoading();

		//textureAtlas = assets.get("pack5.atlas");

		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}


	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		assets.dispose();
	}
}
