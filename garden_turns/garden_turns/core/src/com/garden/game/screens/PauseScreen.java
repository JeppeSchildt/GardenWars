package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

public class PauseScreen implements Screen {
	private GardenGame app;
	private Stage stage;
	Skin skin;
	public PauseScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();


	}

	private void initStage() {

		final int midX = Gdx.graphics.getWidth() / 2;
		final int butY = Gdx.graphics.getHeight() / 3;

		final int maxWidth = Gdx.graphics.getWidth() / 2;
		final int maxHeight = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		Label title = new Label("Game Paused", app.assets.largeTextStyle);
		title.setPosition(midX - 400, maxHeight - 200);
		title.setFontScale(5);

		//ImageButton playButton = new ImageButton(app.assets.goldIcon);
		TextButton playButton = new TextButton("Resume",skin);
		playButton.setPosition(midX - 200, butY);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);

			}
		});

		TextButton resetButton = new TextButton("Restart",skin);
		resetButton.setPosition(midX - 200, butY - 30);
		resetButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
				app.gameScreen.world = new World(app);
				app.gameScreen.world.init("map6.tmx");
			}
		});

		TextButton settingsButton = new TextButton("Settings",skin);
		settingsButton.setPosition(midX - 200, butY - 30 - 30);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);

			}
		});




		TextButton quitButton = new TextButton("Exit Game",skin);
		quitButton.setPosition(midX - 200, butY - 30 - 30 - 30);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});





		stage.addActor(title);

		stage.addActor(playButton);
		stage.addActor(resetButton);
		stage.addActor(settingsButton);
		stage.addActor(quitButton);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) app.setScreen(app.gameScreen);

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
