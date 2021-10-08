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

public class GardenGame extends Game {
	public SpriteBatch batch;
	Texture img;
	public TextureAtlas textureAtlas;
	public Sprite spritePlayer;
	public Sprite spriteHighlight;
	//OrthographicCamera camera;
	public AssetManager assets;
	public GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new AssetManager();
		//assets.load("pack_flower_character.atlas", TextureAtlas.class);
		//assets.load("map3.tmx", TiledMap.class);
		//assets.load("highlight_tile.png", Texture.class);
		assets.load("pack5.atlas", TextureAtlas.class);
		assets.finishLoading();
		//textureAtlas = assets.get("pack_flower_character.atlas");
		textureAtlas = assets.get("pack5.atlas");
		//textureAtlas = assets.get("map3.tmx");
		//spritePlayer = textureAtlas.createSprite("character000");
		//spriteHighlight = textureAtlas.createSprite("higlight_tile.png");
		//camera = new OrthographicCamera(100, 100);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	/*@Override
	public void render () {
		// Debug reveals that this calls the render method of selected screen.
		super.render();
	}*/
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		assets.dispose();
	}
}
