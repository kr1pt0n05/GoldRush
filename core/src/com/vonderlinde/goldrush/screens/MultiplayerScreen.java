package com.vonderlinde.goldrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vonderlinde.goldrush.GoldRush;
import com.vonderlinde.goldrush.multiplayer.SocketHandler;

public class MultiplayerScreen implements Screen {
    public GoldRush game;
    private Stage stage;
    Table table;
    Label title;
    Label ipLabel;
    Label portLabel;
    TextField ipEntry;
    TextField portEntry;
    TextButton connect;
    public Label isConnected;
    TextButton back;

    private SocketHandler socketHandler;

    public MultiplayerScreen(GoldRush game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        socketHandler = SocketHandler.getInstance(this);
        createTable();
    }

    private void createTable(){
        this.table = new Table();

        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        game.assetsManager.queueAddSkins();
        game.assetsManager.manager.finishLoading();

        Skin skin = game.assetsManager.manager.get("skins/flat-earth-ui.json");

        title = new Label("Server Connection", skin);
        ipLabel = new Label("IP: ", skin);
        ipEntry = new TextField("", skin);
        ipEntry.setText("localhost");
        ipEntry.setMessageText("Enter IP Address");
        portLabel = new Label("Port: ", skin);
        portEntry = new TextField("", skin);
        portEntry.setText("5000");
        isConnected = new Label("", skin);

        connect = new TextButton("Connect", skin);
        back = new TextButton("Back", skin);

        table.add(title).colspan(2).center();
        table.row().pad(5, 0, 5, 0);
        table.add(ipLabel);
        table.add(ipEntry);
        table.row().pad(5, 0, 5, 0);
        table.add(portLabel);
        table.add(portEntry);
        table.row().pad(5, 0, 5, 0);
        table.add(connect).fillX().uniformY().colspan(2).center();
        table.row().pad(5, 0, 5, 0);
        table.add(isConnected).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(back).colspan(2).center();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!socketHandler.isConnected) {
                    socketHandler.connectSocket(ipEntry.getText(), portEntry.getText());
                }else{
                    isConnected.setText("Already connected!");
                }
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ipEntry.setText("localhost");
                portEntry.setText("5000");
                stage.setKeyboardFocus(back);
                socketHandler.closeConnection();
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
