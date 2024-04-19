package com.vonderlinde.goldrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.vonderlinde.goldrush.screens.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vonderlinde.goldrush.GoldRush;


public class StartScreen implements Screen {
    private GoldRush game;
    private Stage stage;
    Table table;
    TextButton singleplayer;
    TextButton multiplayer;
    TextButton settings;
    TextButton credits;
    TextButton quit;

    public StartScreen(GoldRush game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // tells the screen to send any input from the user to the stage, so it can respond
        createTable();
    }

    private void createTable(){
        table = new Table();
        table.setFillParent(true);
        // table.setDebug(true);
        stage.addActor(table);

        game.assetsManager.queueAddSkins();
        game.assetsManager.manager.finishLoading();

        Skin skin = game.assetsManager.manager.get("skins/flat-earth-ui.json");

        singleplayer = new TextButton("Singleplayer", skin);
        multiplayer = new TextButton("Multiplayer", skin);
        settings = new TextButton("Settings", skin);
        credits = new TextButton("Credits", skin);
        quit = new TextButton("Quit", skin);

        table.add(singleplayer).fillX().uniformY();
        table.row().pad(10, 0, 10, 0);
        table.add(multiplayer).fillX().uniformY();
        table.row();
        table.add(settings).fillX().uniformY();
        table.row().pad(10, 0, 10, 0);
        table.add(credits).fillX().uniformY();
        table.row();
        table.add(quit).fillX().uniformY();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.changeScreen(GoldRush.ScreenType.GamescreenSP);
            }
        });

        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GoldRush.ScreenType.Multiplayer);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GoldRush.ScreenType.Settings);
            }
        });

        credits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // game.changeScreen(ScreenType.Credits);
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
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
