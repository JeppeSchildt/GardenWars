package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;

public class TitleScreen implements Screen {
	private GardenGame app;
	private Stage stage;
	private TextButton playButton, settingsButton, continueButton,quitButton, debugModeButton;
	private Table table;
	private Label title, titleSub;

	Skin skin;
	public TitleScreen(GardenGame app) {
		this.app = app;

		final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));

		initStage();
	}

	private void initStage() {
		app.sound.Play_Music();

		//Delete when done debug/testing
 		//app.sound.Play_Pause_Music();

		// Create a table that fills the screen. Everything else will go inside this table.
		table = new Table();

		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		final int midX = Gdx.graphics.getWidth() / 2;
		final int butY = Gdx.graphics.getHeight() / 3;





		title = new Label("Garden", app.assets.largeTextStyle);
		title.setFontScale(8);

		titleSub = new Label("Game", app.assets.largeTextStyle);
		titleSub.setFontScale(6);

		playButton = new TextButton("New Game",skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.debugMode = false;
				newGame();
			}
		});


		settingsButton = new TextButton("Preferences",skin);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//stage.addAction(Actions.sequence(Actions.fadeOut(5f),Actions.hide ()));
				preferences();
			}
		});


		quitButton = new TextButton("Exit",skin);
		quitButton.setPosition(midX - 200, butY - 30 - 30);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				exit();
			}
		});

		table.add(title).colspan(2).center();
		table.row();
		table.add(titleSub).colspan(2).center();
		table.row();

		table.add(playButton).left();
		table.row();
		table.add(settingsButton).left();
		table.row();
		table.add(quitButton).left();
		table.row();


		debugModeButton = new TextButton("Debug Mode",skin);
		debugModeButton.setPosition(app.maxWidth - 75, 15);
		debugModeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.debugMode = true;
				newGame();
			}
		});

		debugModeButton.setPosition(app.maxWidth - 125, 15);
		stage.addActor(debugModeButton);

	}

	private void newGame(){
		app.sound.Chance_Music();
		app.sound.buttonMenueSound();

		app.gameScreen = new GameScreen(app);
		app.setScreen(app.gameScreen);
		app.gameScreen.world.init("World1.tmx");
	}

	private void preferences(){
		app.sound.buttonMenueSound();
		if(app.preferencesScreen == null) {
			app.preferencesScreen = new PreferencesScreen(app);
		}
		app.setScreen(app.preferencesScreen);
	}

	private void continueGame(){
		app.sound.buttonMenueSound();
		app.setScreen(app.gameScreen);
		//app.gameScreen.world.init("World1.tmx");
	}

	// Lazy load screens
	private void exit(){
		app.sound.buttonMenueSound();
		if(app.exitScreen == null) {
			app.exitScreen = new ExitScreen(app);
		}
		app.setScreen(app.exitScreen);
	}

	@Override
	public void show() {
		if (continueButton == null){
			if (app.currentGameBool) {
				table.removeActor(playButton);
				table.removeActor(settingsButton);
				table.removeActor(quitButton);
				continueButton = new TextButton("Continue the game",skin);
				continueButton.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						continueGame();
					}
				});


				table.add(playButton).left();
				table.row();
				table.add(continueButton).left();
				table.row();
				table.add(settingsButton).left();
				table.row();
				table.add(quitButton).left();
				table.row();


			}
		}

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) exit();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) newGame();

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
