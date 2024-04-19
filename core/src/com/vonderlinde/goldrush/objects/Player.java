package com.vonderlinde.goldrush.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public static final float WIDTH = 64f; // pixels
    public static final float HEIGHT = 64f; // pixels
    private static final float ACCELERATION_X = 50f;
    private static final float ACCELERATION_Y = 50f;
    private static final Vector2 MIN_VELOCITY = new Vector2(200f, -400f);
    private static final Vector2 MAX_VELOCITY = new Vector2(400f, 50f);

    static final short FORWARD = 0;
    static final short LEFT = 1;
    static final short RIGHT = 2;
    static final short BACKWARD = 4;

    private Rectangle body;
    private Vector2 velocity = new Vector2(200f, -50f);
    private Vector2 position;
    private int score = 0;
    public short direction = 0;
    protected boolean jumping = false;


    public Player(float posx, float posy){
        this.position = new Vector2(posx, posy);
        this.body = new Rectangle(1024/2f - WIDTH/2, posy, WIDTH, HEIGHT);
    }

    public void moveLeft(float delta){
        body.setPosition(getBodyPosition().add(-velocity.x * delta, 0));
        if(velocity.x < MAX_VELOCITY.x) velocity.x += ACCELERATION_X;
        direction = LEFT;
    }

    public void moveRight(float delta){
        body.setPosition(getBodyPosition().add(velocity.x * delta, 0));
        if(velocity.x < MAX_VELOCITY.x) velocity.x += ACCELERATION_X;
        direction = RIGHT;
    }

    public void jump(float delta){
        velocity.y = 1000;
        moveBodyPos(new Vector2(0, velocity.y * delta));
    }

    public void stopMoving(){
        if(velocity.x > MIN_VELOCITY.x) velocity.x -= ACCELERATION_X;
        direction = FORWARD;
    }

    public void gravity(float delta){
        if(velocity.y > MIN_VELOCITY.y) velocity.y -= ACCELERATION_Y;
        moveBodyPos(new Vector2(0, velocity.y * delta));
    }

    public Vector2 getBodyPosition(){
        return new Vector2(body.getX(), body.getY());
    }
    public void setBodyPosition(float posx, float posy){
        body.setPosition(posx, posy);
    }
    public void setBodyPosition(Vector2 vec){
        setBodyPosition(vec.x, vec.y);
    }
    public void moveBodyPos(Vector2 vec){
        body.setPosition(body.x + vec.x, body.y + vec.y);
    }
    public Vector2 getPreviousPosition(){
        return position;
    }
    public void setPreviousPosition(Vector2 vec){
        position = new Vector2(vec);
    }
    public Vector2 getVelocity(){
        return velocity;
    }
    public void addScore(float points){
        score += points;
    }
    public int getScore(){
        return score;
    }
    public Rectangle getBody(){
        return body;
    }
    public float getMinX(){
        return body.x;
    }
    public float getMaxX(){
        return getMinX() + body.width;
    }
    public float getMinY(){
        return body.y;
    }
    public float getMaxY(){
        return getMinY() + body.height;
    }
    public boolean isJumping(){
        return jumping;
    }
    public void setJump(boolean bool){
        jumping = bool;
    }
    public void setVelocityY(float velY){
        velocity.y = velY;
    }
    public void setScore(int points){
        score = points;
    }

}
