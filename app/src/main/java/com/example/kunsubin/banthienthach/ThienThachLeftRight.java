package com.example.kunsubin.banthienthach;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by kunsubin on 5/31/2017.
 */

public class ThienThachLeftRight extends GameObject {
    private int speedY;
    private int speedX;
    private Random random=new Random();
    private Animation animation=new Animation();
    private Bitmap spritesheet;
    private int vector;
    private int row;
    public ThienThachLeftRight(Bitmap res, int x, int y, int w, int h, int numFrames,int vector){
        super.x=x;
        super.y=y;
        width=w;
        height=h;
        this.vector=vector;
        //random ngẫu nhiên tốc độ của thiên thạch bay
        //speed=10+(int)(random.nextDouble()*score/30);
        speedY=5+random.nextInt(6);
        speedX=4+random.nextInt(5);
        spritesheet=res;
        Bitmap[] bitmaps=new Bitmap[numFrames];
        for (int i=0;i<bitmaps.length;i++){
            if(i%4==0&&i>0)row++;
            bitmaps[i]=Bitmap.createBitmap(spritesheet,(i-4*row)*width,row*height,width,height);
        }
        animation.setFrames(bitmaps);
        animation.setDelay(30);

    }
    public void update(){
        switch (vector){
            //rơi từ trái qua
            case 0:
                y+=speedY;
                x+=speedX;
                break;
            //rơi từ phải qua
            case 1:
                y+=speedY;
                x-=speedX;
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
