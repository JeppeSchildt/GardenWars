package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen implements Screen {
    public World world;
    GardenGame app;
    Stage hud;
    public Label turnInfo;
    private final InputMultiplexer mux;

    public GameScreen(GardenGame app) {
        this.app = app;
        world = new World(app);
        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new Stage(new ScreenViewport(camera));
        //mux = new InputMultiplexer(hud, world.mapInput);
        mux = new InputMultiplexer();
        mux.addProcessor(world.mapInput);
        mux.addProcessor(hud);
        Gdx.input.setInputProcessor(mux);
        initHUD();

        /*
          InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(camController);
            multiplexer.addProcessor(stage);

            Gdx.input.setInputProcessor(multiplexer);
         */

    }

    private void initHUD() {
        turnInfo = new Label("GOLD: " + world.user.dkk, app.assets.largeTextStyle);
        turnInfo.setAlignment(Align.bottomLeft);

        turnInfo.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/7,  Gdx.graphics.getHeight()-30);
        ImageButton btnEndTurn = new ImageButton(app.assets.button);


		btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.endTurn();
                //Gdx.app.debug("DEBUG", "clicked");
                //System.out.println("clicked");
            }
        });

        // 1/8 not good solution ...
		btnEndTurn.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/8, 25);




        hud.addActor(turnInfo);
        hud.addActor(btnEndTurn);



    }


    public void updateHUD() {
        turnInfo.setText("Gold:" + world.user.dkk);
    }

    @Override
    public void show() {
        //Gdx.input.setInputProcessor(mux);

    }

    // Render player things like character and plants in this method.
    // Using camera here maybe.
    @Override
    public void render(float delta) {
        world.update(delta);
        world.render();

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        // Starting point of menu/hud in right side of screen.
        int rect_x0 = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/7;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0);
        shapeRenderer.rect(rect_x0, 0, Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight());
        shapeRenderer.end();
        updateHUD();
        hud.draw();
        hud.act(delta);
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

    }
}
