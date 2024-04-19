package com.vonderlinde.goldrush.objects;

import com.badlogic.gdx.math.Vector2;

public class PlayerWrapper {

    public Vector2 position;
    public short direction;

    public PlayerWrapper(Player player){
        this.position = new Vector2(player.getBodyPosition());
        this.direction = player.direction;
    }

}
