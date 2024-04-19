package com.vonderlinde.goldrush.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.vonderlinde.goldrush.multiplayer.Model;

public class MapRenderer {
    TiledMap tiledMap;

    public MapRenderer(TiledMap tiledMap){
        this.tiledMap = tiledMap;
    }


    public  Array<Rectangle> getStaticRectangles(){
        Array<Rectangle> rectangles = new Array<>();

        MapLayer collisionLayer = tiledMap.getLayers().get("collision layer");
        MapObjects objects = collisionLayer.getObjects();

        for(MapObject obj : objects){
            RectangleMapObject rectObj = (RectangleMapObject) obj;
            Rectangle rect = rectObj.getRectangle();
            rectangles.add(rect);
        }
        return rectangles;
    }





}
