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
       // dy=GamePanel.MOVESPEED;

    }
   /* public void update(){

        y+=dy;
        if(y<-GamePanel.HEIGHT)
            y=0;
    }*/
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
       /* if(y<0){
            canvas.drawBitmap(bitmap,x,y+GamePanel.HEIGHT,null);
        }*/
    }
}
