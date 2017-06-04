package com.example.kunsubin.banthienthach;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by kunsubin on 5/20/2017.
 */

public class MayBay extends GameObject {
    private Bitmap spritesheet;

    private int score;

    private boolean playing;
    private Animation animation;
    private long startTime;
    private int level;
    public MayBay(Bitmap res,int w, int h, int numFrames){
        animation=new Animation();
        x=GamePanel.WIDTH/2-30;
        y=700;
        score=0;
        level=1;
        width=w;
        height=h;
        Bitmap[] image=new Bitmap[numFrames];
        spritesheet=res;
        for (int i=0;i<image.length;i++){
            image[i]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }
        animation.setFrames(image);
        animation.setDelay(100);
        startTime= System.nanoTime();
    }
    public void update(){
        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            //score++;
            startTime= System.nanoTime();
        }
        animation.update();
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore() {
        return score;
    }
    public void setUpScore(){
        score++;
    }
    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    public void resetScore(){
        score=0;
    }
    public int getLevel(){
        return level;
    }
    public void updateLevel(){
        if(level==5)
            level=5;
        else{
            if(score>=30&&score<400)
                level=2;
            if(score>=400&&score<450)
                level=3;
            if(score>=450&&score<500)
                level=4;
            if(score>=500)
                level=5;
           /* switch (score){
                case 100:
                    level=2;
                    break;
                case 400:
                    level =3;
                    break;
                case 800:
                    level=4;
                    break;
                case 2000:
                    level=5;
                    break;
                default:
                    break;
            }*/
        }
    }
    public void resetLevel(){
        level=1;
    }
    public void setScore(int score1){
        score=score1;
    }

    @Override
    public Rect getRectangle() {
        return new Rect(x,y,x+width,y+height-100);
    }
}
