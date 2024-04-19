package com.vonderlinde.goldrush.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.vonderlinde.goldrush.AppPreferences;
import com.vonderlinde.goldrush.assets.AssetsManager;
import com.vonderlinde.goldrush.objects.MapRenderer;
import com.vonderlinde.goldrush.objects.Coin;
import com.vonderlinde.goldrush.objects.Player;
import com.vonderlinde.goldrush.objects.PlayerWrapper;
import org.json.JSONException;

import java.util.HashMap;

public class Model {
    private AssetsManager assetsManager;
    private MapRenderer mapRenderer;
    private SocketHandler socketHandler;
    AppPreferences appPreferences;
    TiledMap tiledMap;

    // entities
    protected Player player;
    private boolean moved, jumped;
    private boolean LEFT, RIGHT;
    protected HashMap<String, Player> players;
    protected Array<Coin> coins = new Array<>();
    protected Array<Rectangle> borders = new Array<>();

    // sounds
    public Sound drop;


    public Model(AssetsManager assetsManager, TiledMap tiledMap, AppPreferences appPreferences){
        this.assetsManager = assetsManager;
        this.tiledMap = tiledMap;
        this.appPreferences = appPreferences;
        this.players = new HashMap<>();
        this.mapRenderer = new MapRenderer(tiledMap);
        this.socketHandler = SocketHandler.getInstance(null);
        socketHandler.setModel(this);
        this.player = new Player(10, 100);

        // init map
        borders = mapRenderer.getStaticRectangles();

        assetsManager.queueAddSounds();
        assetsManager.manager.finishLoading();
        drop = assetsManager.manager.get("audio/drop.wav");

        spawnCoin();

        socketHandler.getPlayers();
    }


    public void step(float delta) throws JSONException {


        // set previous player position
        player.setPreviousPosition(player.getBodyPosition());

        // handling horizontal user input && collision
        getUserInput();
        if(moved){
            if(LEFT && RIGHT){
                player.stopMoving();
            }else if(LEFT){
                player.moveLeft(delta);
                checkAndHandleXcollision();
            }else if(RIGHT){
                player.moveRight(delta);
                checkAndHandleXcollision();
            }
        }else{
            player.stopMoving();
        }

        // handling vertical user input && collision
        if(jumped && !player.isJumping()){
            player.jump(delta);
            player.setJump(true);
        }
        player.gravity(delta);

        checkAndHandleYcollision();


        // emit player position, if moved
        if (player.getPreviousPosition().x != player.getBodyPosition().x || player.getPreviousPosition().y != player.getBodyPosition().y) {
            socketHandler.playerMoved(new PlayerWrapper(player));
        }



        // updating coin entities && checking player-coin collision
        int i= 0;
        for(Coin c : coins){
            c.updatePosition(delta);
            if(c.getPosition().y + Coin.HEIGHT < 0) coins.removeIndex(i);
            if(c.getBody().overlaps(player.getBody())){
                player.addScore(1);
                drop.play(appPreferences.getSoundVolume());
                socketHandler.collectedCoin(player.getScore());
                coins.removeIndex(i);
            }
            i++;
        }




        // spawning entities
        if(TimeUtils.nanoTime() - Coin.lastDropTime > Coin.SPAWN_TIME) spawnCoin();

    }

    public void spawnCoin(){
        coins.add(new Coin(MathUtils.random(0, 1024 - Coin.WIDTH), 512));
        Coin.lastDropTime = TimeUtils.nanoTime();
    }

    public void getUserInput(){
        moved = false;
        jumped = false;
        LEFT = false;
        RIGHT = false;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            LEFT = true;
            moved = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            RIGHT = true;
            moved = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            jumped = true;
        }
    }

    public void checkAndHandleXcollision(){
        // check player-border collision
        float penDepth;
        for(Rectangle rec : borders){
            if(rec.overlaps(player.getBody())){
                // check whether player moved left or right
                // and get penetration depth
                if(player.getPreviousPosition().x - player.getBodyPosition().x < 0){
                    // player moved right
                    penDepth = rec.x - player.getMaxX();
                }else{
                    // player moved left
                    penDepth = rec.x + rec.width - player.getMinX();
                }
                // move player opposite of penetration depth
                player.moveBodyPos(new Vector2(penDepth, 0));
            }
        }
    }

    public void checkAndHandleYcollision(){
        float penDepth;
        for(Rectangle rec : borders){
            if(rec.overlaps(player.getBody())){
                // check if player is moving upwards or falling
                if(player.getVelocity().y < 0){
                    // falling
                    penDepth = (rec.y + rec.height) - player.getMinY();
                    player.moveBodyPos(new Vector2(0, penDepth));
                    player.setJump(false);
                }else{
                    // still moving upwards
                    penDepth = rec.y - player.getMaxY();
                    player.moveBodyPos(new Vector2(0, penDepth));
                    // set velocity to 0, so player starts falling down upon hitting his 'head'
                    player.setVelocityY(0);

                }
            }
        }
    }

}
