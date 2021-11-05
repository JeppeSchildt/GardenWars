package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Logger;
import com.garden.game.GardenGame;
//import jdk.internal.org.jline.terminal.Terminal;

public class MapInput implements InputProcessor {
    private final GardenGame app;
    private final World world;
    private final boolean[] keyPressed;
    private int[] toggleKey;
    Logger debugLog;
    boolean tileSelected;
    private final float maxZoom = 1.75f;

    public MapInput(GardenGame app, World world) {
        this.app = app;
        this.world = world;
        keyPressed = new boolean[256];
        toggleKey = new int[256];
        debugLog = new Logger("MapInput:");
    }

    @Override
    public boolean keyDown(int keycode) {
        keyPressed[keycode] = true;
        toggleKey[keycode] = (toggleKey[keycode] + 1) % 2;
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

    @Override // Fix this function. We never want to move to a tile covered by HUD. Click should 'go' to HUD.
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = world.worldCamera.unproject(clickCoordinates);
        int tileX = (int) position.x / world.tileSize;
        int tileY = (int) position.y / world.tileSize;

        if(button == Input.Buttons.RIGHT) {
            tileSelected = true;
            world.hoveredX = tileX;
            world.hoveredY = tileY;

        } else if(button == Input.Buttons.LEFT) {
            tileSelected = false;

            //System.out.println(tileX + " " + tileY);
            if (world.user.unit.canMove(tileX, tileY)) {

                world.user.unit.move(tileX, tileY);
                world.user.unit.setPosition(position.x, position.y);
                //world.worldCamera.position.set(position);

                return true;
            }

        }
       return false;
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
        if(!tileSelected) {
            Vector3 mouseCoordinates = new Vector3(screenX, screenY, 0);
            Vector3 position = world.worldCamera.unproject(mouseCoordinates);

            world.hoveredX = (int) (position.x) / world.tileSize;
            world.hoveredY = (int) (position.y) / world.tileSize;
            //System.out.println("Hovered tile: " + world.hoveredX + "," + world.hoveredY);
        }
        return true;
    }

    // Zoom map.
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (world.worldCamera.zoom * (1 + amountY * 0.05f) > maxZoom)
            return false;
        world.worldCamera.zoom *= 1 + amountY * 0.05f;
        return true;
    }

    // Pan around map using keyboard.
    void adjustCamera(float delta) {
        int xVelocity = 0;
        int yVelocity = 0;

        if(keyPressed[Input.Keys.UP] || keyPressed[Input.Keys.W]) {
            if(world.worldCamera.position.y < Gdx.graphics.getHeight()*1.2)
                yVelocity = 100;
        }

        if(keyPressed[Input.Keys.DOWN] || keyPressed[Input.Keys.S]) {
            if (200 < world.worldCamera.position.y)
                yVelocity = -100;
        }

        if(keyPressed[Input.Keys.LEFT] || keyPressed[Input.Keys.A]) {
            if(200 < world.worldCamera.position.x)
                xVelocity = -100;
        }

        if(keyPressed[Input.Keys.RIGHT] || keyPressed[Input.Keys.D]) {
            if(world.worldCamera.position.x < 1500)
                xVelocity = 100;
        }

        /*
        if(keyPressed[Input.Keys.SPACE]) {
            if (world.worldCamera.position.x != world.user.unit.getX() && world.worldCamera.position.y != world.user.unit.getY()){
                world.worldCamera.position.x = world.user.unit.getX();
                world.worldCamera.position.y = world.user.unit.getY();
            }
        }
         */

        if (toggleKey[Input.Keys.SPACE] == 1){

            System.out.println("Input.Keys.Y");
            world.worldCamera.position.x = world.user.unit.getX();
            world.worldCamera.position.y = world.user.unit.getY();
        }

        // Multiply by zoom to make scrolling through map faster when zoomed out. Within some bounds...
        world.worldCamera.position.x += xVelocity*delta*world.worldCamera.zoom;
        world.worldCamera.position.y += yVelocity*delta*world.worldCamera.zoom;


    }

    // Delta argument is time since last frame. Provided by libgdx.
    void update(float delta) {
        adjustCamera(delta);
    }
}
