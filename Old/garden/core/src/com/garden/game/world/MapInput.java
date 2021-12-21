package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.garden.game.GardenGame;

public class MapInput implements InputProcessor {
    private final GardenGame app;
    private final World world;
    private final boolean[] keyPressed;

    public MapInput(GardenGame app, World world) {
        this.app = app;
        this.world = world;
        keyPressed = new boolean[256];
    }

    @Override
    public boolean keyDown(int keycode) {
        keyPressed[keycode] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyPressed[keycode] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = world.worldCamera.unproject(clickCoordinates);
        // Move selected unit or select tile, etc.
        world.user.unit.move((int) position.x / world.tileSize, (int) position.y / world.tileSize);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    // Adjust 'highlighted' tile
    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = world.worldCamera.unproject(mouseCoordinates);

        world.hoveredX = (int) (position.x) / world.tileSize;
        world.hoveredY = (int) (position.y) / world.tileSize;

        return true;
    }

    // Zoom map.
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (Gdx.input.getX() > Gdx.graphics.getHeight())
            return false;
        world.worldCamera.zoom *= 1 + amountY * 0.05f;
        return true;
    }

    // Pan around map using keyboard.
    void adjustCamera(float delta) {
        int xVelocity = 0;
        int yVelocity = 0;
        if(keyPressed[Input.Keys.UP])
            yVelocity = 100;
        if(keyPressed[Input.Keys.LEFT])
            xVelocity = -100;
        if(keyPressed[Input.Keys.RIGHT])
            xVelocity = 100;
        if(keyPressed[Input.Keys.DOWN])
            yVelocity = -100;

        // Multiply by zoom to make scrolling through map faster when zoomed out.
        world.worldCamera.position.x += xVelocity*delta*world.worldCamera.zoom;
        world.worldCamera.position.y += yVelocity*delta*world.worldCamera.zoom;

    }

    // Delta argument is time since last frame. Provided by libgdx.
    void update(float delta) {
        adjustCamera(delta);
    }
}

/*

    // Implement this to highlight tiles??
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //world.tiledMap.s
        //TiledMapTileLayer.Cell
        //TiledMapTileLayer.Cell cell = world.tileLayer.getCell(screenX, screenY);
        //TiledMapTile tile = cell.getTile();
        Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = world.worldCamera.unproject(mouseCoordinates);
        higlightedCell = world.tileLayer.getCell(screenX, screenY);
        highlightedTile = higlightedCell.getTile();
        world.hoveredX = (int) highlightedTile.getOffsetX();
        world.hoveredY = (int) highlightedTile.getOffsetY();

        //Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        //Vector3 position = world.worldCamera.unproject(mouseCoordinates);
        //world.hoveredX = (int) position.x;// * world.worldWidth;
        //world.hoveredY = (int) position.y;// * world.worldHeight;
        return true;


            // Implement this to highlight tiles??
    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = world.worldCamera.unproject(mouseCoordinates);
        //float x, y;
        //x = position.x + world.worldCamera.position.x;
        //y = position.y + world.worldCamera.position.y;

        //higlightedCell = world.tileLayer.getCell(screenX, screenY);
        //highlightedTile = higlightedCell.getTile();
        //world.hoveredX = (int) highlightedTile.getOffsetX();
        //world.hoveredY = (int) highlightedTile.getOffsetY();
        //world.hoveredX = (int) x / world.tileSize;
        //world.hoveredY = (int) y / world.tileSize;
        //higlightedCell = world.tileLayer.getCell(screen, screenY);
        //highlightedTile = higlightedCell.getTile();
        //Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
        //Vector3 position = world.worldCamera.unproject(mouseCoordinates);
        //world.hoveredX = (int) position.x;// * world.worldWidth;
        //world.hoveredY = (int) position.y;// * world.worldHeight;
        int x = (int) (position.x + world.worldCamera.position.x) / world.tileSize;
        int y = (int) (position.y + world.worldCamera.position.y) / world.tileSize;
        world.hoveredX = x;
        world.hoveredY = y;
        //higlightedCell = world.tileLayer.getCell(x, y);
        //highlightedTile = world.tileLayer.getCell(x, y);
        //higlightedCell.setTile()
        world.highlightedCell = world.tileLayer.getCell(x, y);
        return true;
    }

    }
 */