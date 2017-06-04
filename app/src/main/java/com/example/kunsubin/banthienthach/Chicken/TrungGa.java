package com.example.kunsubin.banthienthach.Chicken;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.kunsubin.banthienthach.Animation;
import com.example.kunsubin.banthienthach.GameObject;

import java.util.Random;

/**
 * Created by kunsubin on 5/28/2017.
 */

public class TrungGa extends GameObject {
    private Random random=new Random();
    private Animation animation=new Animation();
    private Bitmap spritesheet;
    private int vector;
    private int speedX;
    private int speedY;
    public TrungGa(Bitmap res, int x, int y, int w, int h, int numFrames, int vector){
        super.x=x;
        super.y=y;
        width=w;
        height=h;

        //random speed
        speedX=random.nextInt(7)+4;
        speedY=random.nextInt(16)+5;

        this.vector=vector;
        spritesheet=res;
        Bitmap[] bitmaps=new Bitmap[numFrames];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        animation.setFrames(bitmaps);
        animation.setDelay(80);

    }
    public void update(){
        switch (vector){
            case 0:
                x-=speedX;
                y+=speedY;
                break;
            case 1:
                y+=speedY;
                break;
            case 2:
                x+=speedX;
                y+=speedY;
                break;
            default:
                break;
        }
        animation.update();
    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch (Exception ex){

        }
    }
}
