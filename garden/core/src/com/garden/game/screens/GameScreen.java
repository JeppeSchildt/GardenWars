package com.garden.game.screens;

import com.badlogic.gdx.Screen;
import com.garden.game.GardenGame;
import com.garden.game.world.World;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen implements Screen {
    public World world;
    GardenGame app;

    public GameScreen(GardenGame app) {
        this.app = app;
        world = new World(app);

    }

    @Override
    public void show() {

    }

    // Render player things like character and plants in this method.
    // Using camera here maybe.
    @Override
    public void render(float delta) {
        world.update(delta);
        world.render();
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
