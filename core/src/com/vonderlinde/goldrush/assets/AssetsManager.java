package com.vonderlinde.goldrush.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsManager {
    public final AssetManager manager = new AssetManager();


    // textures
    public final String textureAtlas = "packedTextures.atlas";

    // maps
    public final String tiledGrassMap = "grassmap.tmx";

    // music
    public final String dropSound = "audio/drop.wav";

    // sounds
    public final String backgroundMusic = "audio/backgroundMusic.mp3";

    // skins
    public final String skin = "skins/flat-earth-ui.json";


    public void queueAddImages(){
        manager.load(textureAtlas, TextureAtlas.class);
    }

    public void queueAddSounds(){
        manager.load(dropSound, Sound.class);
    }

    public void queueAddMusic(){
        manager.load(backgroundMusic, Music.class);
    }

    public void queueAddSkins(){
        SkinParameter params = new SkinParameter("skins/flat-earth-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddAtlas(){
        manager.load(textureAtlas, TextureAtlas.class);
    }

    public void queueTiledMap(){
        manager.load(tiledGrassMap, TiledMap.class);
    }

}
