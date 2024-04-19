package com.vonderlinde.goldrush.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.vonderlinde.goldrush.GoldRush;
import com.vonderlinde.goldrush.objects.Player;
import com.vonderlinde.goldrush.objects.PlayerWrapper;
import com.vonderlinde.goldrush.screens.MultiplayerScreen;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;

public class SocketHandler {
    // Singleton
    public static SocketHandler thisInstance;
    public boolean isConnected = false;

    private Socket socket;

    MultiplayerScreen multiplayerScreen;
    Model model;

    private SocketHandler(MultiplayerScreen multiplayerScreen){
        this.multiplayerScreen = multiplayerScreen;
    }


    public void connectSocket(String ip, String port){
        try {
            socket = IO.socket("http://" + ip + ":" + port);
            socket.connect();
            configSocketEvents();

        } catch (Exception e) {
            connectionSuccessful(false);
        }
    }


    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                connectionSuccessful(true);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        multiplayerScreen.game.changeScreen(GoldRush.ScreenType.MP);
                    }
                });
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                connectionSuccessful(false);
                closeConnection();
            }
        }).on("activePlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    JSONObject players = data.getJSONObject("players");
                    Iterator player = players.keys();
                    while(player.hasNext()){
                        String id = (String) player.next();
                        JSONObject coords = players.getJSONObject(id);
                        float x = Float.parseFloat(coords.getString("x"));
                        float y = Float.parseFloat(coords.getString("y"));
                        model.players.put(id, new Player(x, y));

                        System.out.println("Player " + id + "at: (" + x + ", " + y + ")");
                    }
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error fetching active players!");
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    model.players.put(id, new Player(0, 100));

                    System.out.println("New Player connected: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error fetching new player!");
                }
            }
        }).on("playerMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    float x = Float.parseFloat(data.getString("x"));
                    float y = Float.parseFloat(data.getString("y"));
                    short direction = Short.parseShort(data.getString("direction"));

                    Player player = model.players.get(id);
                    player.setBodyPosition(x, y);
                    player.direction = direction;

                    System.out.println("Player " + id + " moved to: " + x + ", " + y);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error fetching player movement!");
                }
            }
        }).on("collectedCoin", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    int score = Integer.parseInt(data.getString("score"));
                    model.players.get(id).setScore(score);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    model.players.remove(id);

                    System.out.println("Player disconnected: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error fetching disconnected player!");
                }
            }
        });
    }

    public static SocketHandler getInstance(MultiplayerScreen multiplayerScreen){
        if(thisInstance == null) thisInstance = new SocketHandler(multiplayerScreen);
        return thisInstance;
    }


    public  void setModel(Model model_){
        this.model = model_;
    }

    public void connectionSuccessful(boolean bool){
        if(bool){
            multiplayerScreen.isConnected.setText("Connected!");
            isConnected = true;
        }else{
            multiplayerScreen.isConnected.setText("Connection failed!");
            isConnected = false;
        }
    }

    public void closeConnection(){
        if(socket != null){
            socket.disconnect();
        }
    }

    public void getPlayers(){
        socket.emit("getPlayers");
    }

    public void playerMoved(PlayerWrapper player) {
        try {
            JSONObject data = new JSONObject();
            data.put("x", player.position.x);
            data.put("y", player.position.y);
            data.put("direction", player.direction);
            socket.emit("playerMoved", data);
        }catch(JSONException e){
            Gdx.app.log("SocketIO", "Failed to update player position.");
        }
    }

    public void collectedCoin(int score){
        try {
            JSONObject data = new JSONObject();
            data.put("score", score);
            socket.emit("collectedCoin", data);
        }catch(JSONException e){
            Gdx.app.log("SocketIO", "Failed to update player score.");
        }
    }

}
