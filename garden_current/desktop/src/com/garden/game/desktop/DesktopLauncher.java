package com.garden.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.garden.game.GardenGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Fix a size for the window, don't allow resizing (full screen etc.). Makes UI stuff easier.
		config.setWindowedMode(1024, 768);
		config.setResizable(false);
		new Lwjgl3Application(new GardenGame(), config);
	}
}
