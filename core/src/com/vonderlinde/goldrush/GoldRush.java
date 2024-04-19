package com.vonderlinde.goldrush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vonderlinde.goldrush.assets.AssetsManager;
import com.vonderlinde.goldrush.multiplayer.GameScreenMP;
import com.vonderlinde.goldrush.screens.*;

public class GoldRush extends Game {
	// screens
	private StartScreen startScreen;
	private SingleplayerScreen singleplayerScreen;
	private MultiplayerScreen multiplayerScreen;
	private SettingsScreen settingsScreen;
	private CreditsScreen creditsScreen;
	private GameScreenMP gameScreenMP;

	public AssetsManager assetsManager;
	public AppPreferences appPreferences;


	public static enum ScreenType{
		Start, Singleplayer, Multiplayer, Settings, Credits, SP, MP
	}


	public void create() {
		this.appPreferences = new AppPreferences();
		this.assetsManager = new AssetsManager();

		this.setScreen(new StartScreen(this));
	}


	public void changeScreen(ScreenType screen){
		switch(screen){
			case Start:
				if(startScreen == null) startScreen = new StartScreen(this);
				this.setScreen(startScreen); break;
			case Singleplayer:
				if(singleplayerScreen == null) singleplayerScreen = new SingleplayerScreen(this);
				this.setScreen(singleplayerScreen); break;
			case Multiplayer:
				if(multiplayerScreen == null) multiplayerScreen = new MultiplayerScreen(this);
				this.setScreen(multiplayerScreen); break;
			case Settings:
				if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
				this.setScreen(settingsScreen); break;
			case Credits:
				if(creditsScreen == null) creditsScreen = new CreditsScreen(this);
				this.setScreen(creditsScreen); break;
			case MP:
				if(gameScreenMP == null) gameScreenMP = new GameScreenMP(this);
				this.setScreen(gameScreenMP); break;
		}
	}


	public AppPreferences getPreferences(){
		return this.appPreferences;
	}


	public void render() {
		super.render(); // important!
	}


	public void dispose() {
	}
}
