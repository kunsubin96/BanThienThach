package com.example.kunsubin.banthienthach;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by kunsubin on 5/20/2017.
 */

public class ThienThach extends GameObject {
    private int score;
    private int speed;
    private Random random=new Random();
    private Animation animation=new Animation();
    private Bitmap spritesheet;
    public ThienThach(Bitmap res, int x, int y, int w, int h,int s, int numFrames){
        super.x=x;
        super.y=y;
        width=w;
        height=h;
        score=s;
        //random ngẫu nhiên tốc độ của thiên thạch bay
        //speed=10+(int)(random.nextDouble()*score/30);
        speed=10+random.nextInt(10)+score/30;
        if(speed>40) speed=40;
        spritesheet=res;
        Bitmap[] bitmaps=new Bitmap[numFrames];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }
        animation.setFrames(bitmaps);
        animation.setDelay(100-speed);

    }
    public void update(){
        y+=speed;
        animation.update();
    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch (Exception ex){

        }
    }
  /*  public int getWidth(){
        return width-10;
    }*/
}
