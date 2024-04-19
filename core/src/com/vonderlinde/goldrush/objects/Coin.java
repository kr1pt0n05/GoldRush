package com.vonderlinde.goldrush.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {

    public static final float WIDTH = 32f; // pixels
    public static final float HEIGHT = 32f; // pixels
    public static final float SPAWN_TIME = 1000000000f;
    public static float lastDropTime = 0;
    private static final float VELOCITY_Y = 200f; // pixels

    private Rectangle body;
    private Vector2 position;

    public Coin(float posx, float posy){
        body = new Rectangle(posx, posy, WIDTH, HEIGHT);
    }

    public Vector2 getPosition(){
        return new Vector2(body.getX(), body.getY());
    }

    public void updatePosition(float delta){
        body.setPosition(body.getX(), body.getY() - VELOCITY_Y * delta);
    }

    public Rectangle getBody(){
        return body;
    }

}
