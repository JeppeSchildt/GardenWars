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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

public class PauseScreen implements Screen {
	private GardenGame app;
	private Stage stage;
	private Table table;

	Skin skin;
	public PauseScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();


	}

	private void initStage() {

		// Create a table that fills the screen. Everything else will go inside this table.
		table = new Table();

		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		Label title = new Label("Game Paused", app.assets.largeTextStyle);
		title.setFontScale(5);

		//ImageButton playButton = new ImageButton(app.assets.goldIcon);
		TextButton playButton = new TextButton("Resume",skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				resumeGame();
			}
		});



		TextButton resetButton = new TextButton("Restart",skin);
		resetButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				restart();
			}
		});

		TextButton settingsButton = new TextButton("Preferences",skin);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				preferences();

			}
		});

		TextButton quitButton = new TextButton("Back to menu",skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				backToMenue();
			}
		});


		table.add(title).colspan(2).center();
		table.row();

		table.add(playButton).left();
		table.row();
		table.add(resetButton).left();
		table.row();
		table.add(settingsButton).left();
		table.row();
		table.add(quitButton).left();
		table.row();
	}


	private void resumeGame(){
		app.sound.buttonMenueSound();
		app.setScreen(app.gameScreen);
	}
	private void backToMenue(){
		app.sound.buttonMenueSound();
		app.preferencesBool = false;

		app.sound.Chance_Music();
		app.setScreen(app.titleScreen);
	}

	private void restart(){
		app.currentGameBool = false;
		app.sound.buttonMenueSound();

		app.gameScreen.dispose();
		app.gameScreen.world.dispose();

		if (app.gameScreen == null){
			app.gameScreen = new GameScreen(app);
			app.setScreen(app.gameScreen);
			app.gameScreen.world.init("World1.tmx");
		}
		app.setScreen(app.gameScreen);

	}

	private void preferences(){
		app.sound.buttonMenueSound();
		if(app.preferencesScreen == null) {
			app.preferencesScreen = new PreferencesScreen(app);
		}
		app.setScreen(app.preferencesScreen);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backToMenue();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) resumeGame();

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
