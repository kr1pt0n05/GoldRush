package com.vonderlinde.goldrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vonderlinde.goldrush.GoldRush;

public class SettingsScreen implements Screen {
    private GoldRush game;
    private Stage stage;
    private Table table;
    private Label settings;
    private Label musicEnabled;
    private Label musicVolume;
    private Label soundEffectsEnabled;
    private Label soundEffectsVolume;
    TextButton back;
    private CheckBox musicEnabledCheckbox;
    private CheckBox soundEffetsCheckbox;
    private Slider musicVolumeSlider;
    private Slider soundEffectsVolumeSlider;

    public SettingsScreen(GoldRush game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());


        createTable();
    }

    public void createTable(){
        table = new Table();
        table.setFillParent(true);
        // table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skins/flat-earth-ui.json"));

        settings = new Label("Settings", skin);
        musicEnabled = new Label("Music", skin);
        musicVolume = new Label("Music Volume", skin);
        soundEffectsEnabled = new Label("Sound Effects", skin);
        soundEffectsVolume = new Label("Sound Volume", skin);

        back = new TextButton("Back", skin);

        musicEnabledCheckbox = new CheckBox(null, skin);
        musicEnabledCheckbox.setChecked(game.getPreferences().isMusicEnabled());

        soundEffetsCheckbox = new CheckBox(null, skin);
        soundEffetsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());

        musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());

        soundEffectsVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundEffectsVolumeSlider.setValue(game.getPreferences().getSoundVolume());

        table.add(settings).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(musicEnabled).left();
        table.add(musicEnabledCheckbox);
        table.row().pad(10, 0, 10, 0);
        table.add(musicVolume).left();
        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 10, 0);
        table.add(soundEffectsEnabled).left();
        table.add(soundEffetsCheckbox);
        table.row().pad(10, 0, 10, 0);
        table.add(soundEffectsVolume).left();
        table.add(soundEffectsVolumeSlider);
        table.row().pad(10, 0, 10, 0);
        table.add(back).colspan(2);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        musicEnabledCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicEnabledCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        soundEffectsEnabled.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffetsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });

        soundEffectsVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setSoundVolume(soundEffectsVolumeSlider.getValue());
                return false;
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GoldRush.ScreenType.Start);
            }
        });


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, true);
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
        stage.clear();
        stage.dispose();

    }
}
