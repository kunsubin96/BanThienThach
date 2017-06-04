package com.example.kunsubin.banthienthach;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by kunsubin on 5/20/2017.
 */

public class Backgroud {
    private Bitmap bitmap;
    private int x, y;
    public Backgroud(Bitmap bitmap){
        this.bitmap=bitmap;
        x=0;
        y=0;
       // dy=GamePanel.MOVESPEED;

    }
  /*  public void update(){

        y+=4;
        if(y>0)
            y=-GamePanel.HEIGHT;
    }*/
    public void draw(Canvas canvas){
       /* canvas.drawBitmap(bitmap,x,y,null);
        if(y>-GamePanel.HEIGHT){*/
        canvas.drawBitmap(bitmap,x,y,null);
        //}
    }
}
