package com.vonderlinde.goldrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOLUME = "sound";
    private static final String PREFS_NAME = "settings";

    protected Preferences getPrefs(){
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public float getMusicVolume(){
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 1.0f);
    }

    public void setMusicVolume(float volume){
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume(){
        return getPrefs().getFloat(PREF_SOUND_VOLUME, 1.0f);
    }

    public void setSoundVolume(float value){
        getPrefs().putFloat(PREF_SOUND_VOLUME, value);
        getPrefs().flush();
    }


    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled){
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public boolean isSoundEffectsEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled){
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }
}
