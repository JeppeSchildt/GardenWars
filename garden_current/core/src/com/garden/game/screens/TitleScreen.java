package com.garden.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

public class TitleScreen implements Screen {
	private GardenGame app;
	private Stage stage;
	private TextButton continueButton;

	Skin skin;
	public TitleScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();



	}

	private void initStage() {

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();

		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		final int midX = Gdx.graphics.getWidth() / 2;
		final int butY = Gdx.graphics.getHeight() / 3;

		TextButton musicButton = new TextButton("Music",skin);
		musicButton.setPosition(app.maxWidth - 75, 15);
		musicButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (app.music.isPlaying())
					app.music.pause();
				else
					app.music.play();
			}
		});

		Label title = new Label("Garden", app.assets.largeTextStyle);
		title.setFontScale(8);

		Label titleSub = new Label("Game", app.assets.largeTextStyle);
		titleSub.setFontScale(6);

		TextButton playButton = new TextButton("New Game",skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.gameScreen);
				app.gameScreen.world.init("map6.tmx");
			}
		});

		if (app.currentGameBool) {
			continueButton = new TextButton("Continue the game",skin);
			continueButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					app.setScreen(app.gameScreen);
					app.gameScreen.world.init("map6.tmx");


				}
			});
		}

		TextButton settingsButton = new TextButton("Preferences",skin);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(app.preferencesScreen);

			}
		});

		TextButton quitButton = new TextButton("Exit",skin);
		quitButton.setPosition(midX - 200, butY - 30 - 30);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});



		table.add(title).colspan(2).center();
		table.row();
		table.add(titleSub).colspan(2).center();
		table.row();

		table.add(playButton).left();
		table.row();
		table.add(continueButton);
		table.row();
		table.add(settingsButton).left();
		table.row();
		table.add(quitButton).left();
		table.row();



		stage.addActor(musicButton);
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
