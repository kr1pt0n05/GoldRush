package com.vonderlinde.goldrush;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.vonderlinde.goldrush.GoldRush;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		/*
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxHeight = 4096;
		settings.maxWidth = 4096;
		TexturePacker.process("assets/textures", "assets/packedTextures", "packedTextures");

		settings.edgePadding = true;
		// settings.silent = false;
		settings.filterMin = Texture.TextureFilter.Linear;
		settings.filterMag = Texture.TextureFilter.Linear;
		 */

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("goldrush");
		config.setTitle("com/vonderlinde/goldrush");
		config.setWindowedMode(1024, 512);
		config.useVsync(true);
		new Lwjgl3Application(new GoldRush(), config);
	}
}
