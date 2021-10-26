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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;

public class TitleScreen implements Screen {
	private GardenGame app;
	private Stage stage;

	public TitleScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();
	}

	private void initStage() {
		final int midX = Gdx.graphics.getWidth() / 2;

		Label title = new Label("GardenGame", app.assets.largeTextStyle);
		title.setPosition(midX - (title.getPrefWidth() / 2), Gdx.graphics.getHeight() * 0.75f);

		ImageButton playButton = new ImageButton(app.assets.goldIcon);

		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
				app.gameScreen.world.init();
			}
		});

		playButton.setPosition(midX - 200, Gdx.graphics.getHeight() * 0.55f);

		ImageButton quitButton = new ImageButton(app.assets.waterIcon);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		quitButton.setPosition(midX - 200, Gdx.graphics.getHeight() * 0.3f);

		stage.addActor(title);
		stage.addActor(playButton);
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

		Gdx.gl.glClearColor(0.2f, 0.6f, 0.2f, 1);
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

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
