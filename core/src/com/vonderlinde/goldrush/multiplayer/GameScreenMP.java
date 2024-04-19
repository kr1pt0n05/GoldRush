package com.vonderlinde.goldrush.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.vonderlinde.goldrush.GoldRush;
import com.vonderlinde.goldrush.objects.Coin;
import com.vonderlinde.goldrush.objects.Player;
import org.json.JSONException;

public class GameScreenMP implements Screen {

    // Screen size
    public static final float SCREEN_WIDTH = 1024;
    public static final float SCREEN_HEIGHT = 512;

    GoldRush game;
    Model model;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch batch;
    private Stage stage;

    // textures
    TextureAtlas atlas;
    TextureRegion[][] playerTextures;
    Animation<TextureRegion> coinAnimation;

    // map
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    // audio
    Music music;

    float stateTime = 0f;

                                                // debugging
                                                ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GameScreenMP(GoldRush game){
        this.game = game;
        this.camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        this.batch = new SpriteBatch();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.setProjectionMatrix(camera.combined);
        this.stage = new Stage();

        game.assetsManager.manager.setLoader(TiledMap.class, new TmxMapLoader());
        game.assetsManager.queueAddImages();
        game.assetsManager.queueAddMusic();
        game.assetsManager.queueAddAtlas();
        game.assetsManager.queueTiledMap();
        game.assetsManager.manager.finishLoading();

        music = game.assetsManager.manager.get("audio/backgroundMusic.mp3", Music.class);
        music.play();
        music.setVolume(game.appPreferences.getMusicVolume());
        music.setLooping(true);

        atlas = game.assetsManager.manager.get("packedTextures.atlas");

        tiledMap = game.assetsManager.manager.get("grassmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.model = new Model(game.assetsManager, tiledMap, game.appPreferences);

        initTextures();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        ScreenUtils.clear(1, 0, 0, 1);

        // render map
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        // logic
        try {
            model.step(delta);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // debugging
                                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                                shapeRenderer.rect(model.player.getBodyPosition().x, model.player.getBodyPosition().y, Player.WIDTH, Player.HEIGHT);

                                for(Coin c : model.coins){
                                    shapeRenderer.rect(c.getPosition().x, c.getPosition().y, 32, 32);
                                }

                                for(Rectangle rec : model.borders){
                                    shapeRenderer.rect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());

                                }

                                shapeRenderer.end();

        // render models
        batch.begin();
            batch.draw(playerTextures[model.player.direction][model.player.getScore()], model.player.getBodyPosition().x, model.player.getBodyPosition().y, Player.WIDTH, Player.HEIGHT);

            for(Player p : model.players.values()){
                batch.draw(playerTextures[p.direction][p.getScore()], p.getBodyPosition().x, p.getBodyPosition().y);
                p.direction = 0;
            }

            for(Coin c : model.coins){
                TextureRegion currentFrame = coinAnimation.getKeyFrame(stateTime, true);
                batch.draw(currentFrame, c.getPosition().x, c.getPosition().y);
            }

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);
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
        batch.dispose();
    }

    public void initTextures(){
        playerTextures = new TextureRegion[3][21];
        for(int i = 0; i < 20; i++){
            playerTextures[0][i] = atlas.findRegion("chest01", i);
        }
        for(int i = 0; i < 20; i++){
            playerTextures[1][i] = atlas.findRegion("chest02", i);
        }
        for(int i = 0; i < 20; i++){
            playerTextures[2][i] = atlas.findRegion("chest03", i);
        }

        TextureRegion[] coinFrames = new TextureRegion[8];
        for(int i = 0; i < 8; i++){
            coinFrames[i] = atlas.findRegion("coin", i+1);
        }
        coinAnimation = new Animation<>(8 / 60f, coinFrames);
    }
}
