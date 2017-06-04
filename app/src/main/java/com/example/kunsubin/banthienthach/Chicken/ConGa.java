package com.example.kunsubin.banthienthach.Chicken;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.kunsubin.banthienthach.Animation;
import com.example.kunsubin.banthienthach.GameObject;
import com.example.kunsubin.banthienthach.GamePanel;

import java.util.Random;

/**
 * Created by kunsubin on 5/28/2017.
 */

public class ConGa extends GameObject {
    private Bitmap spritesheet;
    private Animation animation;
    private Random random;
    int temp;
    public ConGa(Bitmap res,int w, int h, int numFrames){
        animation=new Animation();
        random=new Random();
        x= GamePanel.WIDTH/2-126;
        y=300;
        width=w;
        height=h;
        Bitmap[] image=new Bitmap[numFrames];
        spritesheet=res;
        for (int i=0;i<image.length;i++){
            image[i]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }
        animation.setFrames(image);
        animation.setDelay(100);
    }
    public void  update(){
        temp=random.nextInt(8);
        switch (temp){
            case 0:
                if(x>50)
                    x-=50;
                break;
            case 1:
                if(x<570)
                    x+=50;
                break;
            case 2:
                if(y>150)
                    y-=50;
                break;
            case 3:
                if(y<440)
                    y+=50;
                break;
            case 4:
                if(x>50&&y>150){
                    x-=50;
                    y-=50;
                }
                break;
            case 5:
                if(x<570&&y>150){
                    x+=50;
                    y-=50;
                }
                break;
            case 6:
                if(x>50&&y<440){
                    x-=50;
                    y+=50;
                }
                break;
            case 7:
                if(x<570&&y<440){
                    x+=50;
                    y+=50;
                }
                break;
            default:
                break;
        }
        animation.update();
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
}
