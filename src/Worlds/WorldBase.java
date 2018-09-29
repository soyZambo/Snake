package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;

import java.awt.*;
import java.util.LinkedList;

public abstract class WorldBase {
 
    public int GridWidthHeightPixelCount;
    public int GridPixelsize;
    public Player player;

    protected Handler handler;

    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;

    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();

    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;


    }
    public void tick(){



    }

    public void render(Graphics g){

        for (int i = 0; i <= 600; i = i + GridPixelsize) {

            g.setColor(Color.WHITE);
            g.drawLine(0, i, handler.getWidth() , i);
            g.drawLine(i,0,i,handler.getHeight());

        }



    }

}
