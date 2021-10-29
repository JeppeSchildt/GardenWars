package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
<<<<<<< HEAD
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
=======
>>>>>>> parent of 6c027cc (:))
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;

public class TitleScreen implements Screen {
	private GardenGame app;
	private Stage stage;
<<<<<<< HEAD
	Skin skin;
=======

>>>>>>> parent of 6c027cc (:))
	public TitleScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();
<<<<<<< HEAD


	}

	private void initStage() {

		final int midX = Gdx.graphics.getWidth() / 2;
		final int butY = Gdx.graphics.getHeight() / 3;

		final int maxWidth = Gdx.graphics.getWidth() / 2;
		final int maxHeight = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		Label title = new Label("Garden", app.assets.largeTextStyle);
		title.setPosition(midX - 325, maxHeight - 200);
		title.setFontScale(8);

		Label titleSub = new Label("Game", app.assets.largeTextStyle);
		titleSub.setPosition(midX - 200, maxHeight - 200 - 150);
		titleSub.setFontScale(6);

		//ImageButton playButton = new ImageButton(app.assets.goldIcon);
		TextButton playButton = new TextButton("Start Game",skin);
		playButton.setPosition(midX - 200, butY);
=======
	}

	private void initStage() {
		final int midX = Gdx.graphics.getWidth() / 2;

		Label title = new Label("GardenGame", app.assets.largeTextStyle);
		title.setPosition(midX - (title.getPrefWidth() / 2), Gdx.graphics.getHeight() * 0.75f);

		ImageButton playButton = new ImageButton(app.assets.goldIcon);

>>>>>>> parent of 6c027cc (:))
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
				app.gameScreen.world.init();
			}
		});

<<<<<<< HEAD
		TextButton settingsButton = new TextButton("Settings",skin);
		settingsButton.setPosition(midX - 200, butY - 30);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
				app.gameScreen.world.init();
			}
		});




		TextButton quitButton = new TextButton("Exit Game",skin);
		quitButton.setPosition(midX - 200, butY - 30 - 30);
=======
		playButton.setPosition(midX - 200, Gdx.graphics.getHeight() * 0.55f);

		ImageButton quitButton = new ImageButton(app.assets.waterIcon);
>>>>>>> parent of 6c027cc (:))
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

<<<<<<< HEAD




		stage.addActor(title);
		stage.addActor(titleSub);

		stage.addActor(playButton);
		stage.addActor(settingsButton);
=======
		quitButton.setPosition(midX - 200, Gdx.graphics.getHeight() * 0.3f);

		stage.addActor(title);
		stage.addActor(playButton);
>>>>>>> parent of 6c027cc (:))
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

<<<<<<< HEAD
		Gdx.gl.glClearColor(1, 1, 1, 1);
=======
		Gdx.gl.glClearColor(0.2f, 0.6f, 0.2f, 1);
>>>>>>> parent of 6c027cc (:))
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
