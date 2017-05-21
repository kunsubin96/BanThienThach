package com.example.kunsubin.banthienthach;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kunsubin on 5/20/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public static final int WIDTH=768;
    public static final int HEIGHT=1280;
   // public static final int MOVESPEED=-5;
    private MainThread mainThread;
    private Backgroud backgroud;
    private MayBay mayBay;
    private Random random=new Random();
    public ArrayList<ThienThach> thienThachs;
    private long thienthachStartTime;
    private ArrayList<Dan> dans;
    private long danStartTime;
    private Explosion explosion;
    //
    private boolean newGameCreated;
    private long startReset;
    private boolean reset;
    private boolean disspapear;
    private boolean started;
    private int best;
    private boolean stateDan=false;

    private int soThienThachQua=0;

    private  int gold=100;
    private int mang=1;
    private int countTemp=0;
    private boolean mangbool=false;
    public GamePanel(Context context) {
        super(context);
        //add event the call back
        getHolder().addCallback(this);

        setFocusable(true);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //vẽ khung chính màn hình với hiệu ứng chuyển động
        backgroud=new Backgroud(BitmapFactory.decodeResource(getResources(),R.drawable.galaxy));
        mayBay=new MayBay(BitmapFactory.decodeResource(getResources(),R.drawable.pt),100,98,1);
       // mayBay=new MayBay(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach),49,49,1);
        thienThachs=new ArrayList<ThienThach>();
        thienthachStartTime= System.nanoTime();
        dans=new ArrayList<Dan>();
        danStartTime=System.nanoTime();



        mainThread=new MainThread(getHolder(),this);
        mainThread.setRunning(true);
        mainThread.start();
        //set ban đầu chưa chạy
        mayBay.setPlaying(false);
        newGameCreated=true;
        reset=false;

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry=true;
        int couter=0;
        while (retry&&couter<1000){
            couter++;
            try {
                mainThread.setRunning(false);
                mainThread.join();
                retry=false;
                mainThread=null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!mayBay.isPlaying()&&newGameCreated&&reset&&!mangbool){
                mayBay.setPlaying(true);
            }
            if(mayBay.isPlaying()){
                if(!started) started=true;
                reset=false;
                mayBay.x=(int) event.getX();
                mayBay.y=(int) event.getY();
                stateDan=true;

            }
            return true;
        }
        //nhấn giữ di chuyển
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            mayBay.x=(int) event.getX();
            mayBay.y=(int) event.getY();
            //stateDan=true;
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            return true;
        }
        return super.onTouchEvent(event);
    }
    public void update(){
        //backgroud.update();
        if(mayBay.isPlaying()){
            mayBay.update();
            //xử lý di chuyển của thiên thạch
            long thienthachElapsed=(System.nanoTime()-thienthachStartTime)/1000000;
            if(thienthachElapsed>(1000-(mayBay.getScore())/4)){
                if(thienThachs.size()==0){
                    //tọa độ viên đầu tiên
                    thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),WIDTH/2-20,10,80,80,mayBay.getScore(),1));

                }
                else {
                    //random thiên thạch
                    //level 1 random 2 thiên thach cùng lúc
                    if(mayBay.getLevel()==1){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                    }
                    //level 2 random 3 thiên thạch cùng lúc
                    if(mayBay.getLevel()==2){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                    }
                    //level 3 ramdom 4 thiên thạch cùng lúc
                    if(mayBay.getLevel()==3){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                    }
                    //level 4 ramdom 5 thiên thạch cùng lúc
                    if(mayBay.getLevel()==4){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                    }
                    //level 5 ramdom 6 thiên thạch cùng lúc
                    if(mayBay.getLevel()==5){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                    }
                }
                thienthachStartTime=System.nanoTime();
            }
            for(int i=0;i<thienThachs.size();i++){
                //cập nhật trạng thái thiên thạch
                thienThachs.get(i).update();
                //nếu xảy ra va chạm giữa thiên thạch với máy bay thì dừng game
                if(collision(thienThachs.get(i),mayBay)){
                    thienThachs.remove(i);
                    mang--;
                    if(mang>0){
                       /* explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),mayBay.getX(),mayBay.getY()-30,
                                100,100,25);
                        explosion.update();*/
                        mangbool=true;
                        /*mayBay.setPlaying(false);
                        startReset=System.nanoTime();
                        long resetElapsed=(System.nanoTime()-startReset)/1000000;
                        if(resetElapsed>100){
                            againGame();
                            mayBay.setPlaying(true);
                            startReset= System.nanoTime();
                        }*/

                    }else{
                        mangbool=false;
                        mayBay.setPlaying(false);
                    }

                }
                //xóa bỏ thiên thạch khi vượt ra ngoài màn hình
                if(thienThachs.get(i).getY()>1280){
                    thienThachs.remove(i);
                    soThienThachQua++;
                    if(gold==0)
                        gold=0;
                   else
                        gold--;
                    break;
                }
            }
            //dan
            long danElapsed=(System.nanoTime()-danStartTime)/1000000;
            if(danElapsed>100){
                if(stateDan){
                    dans.add(new Dan(BitmapFactory.decodeResource(getResources(),R.drawable.missile),mayBay.getX()+43,mayBay.getY(),15,45,13));
                    stateDan=false;
                }
                danStartTime=System.nanoTime();
            }
            for(int i=0;i<dans.size();i++){
                dans.get(i).update();
                //nếu xảy ra va chạm giữa đạn với thiên thạch thì dừng bỏ
                for (int j=0;j<thienThachs.size();j++){
                    if(collision(dans.get(i),thienThachs.get(j))){
                        gold++;
                        mayBay.setUpScore();
                        //loại bỏ dạn với thiên thạch bị bắn hạ
                        dans.remove(i);
                        thienThachs.remove(j);
                     }
                }
                //remove đạn
                if(dans.get(i).getY()<-50){
                    dans.remove(i);
                    break;
                }
            }
            //200 điểm cho lên 1 mạng
            if(mayBay.getScore()>0&&mayBay.getScore()%200==0&&countTemp==0){
                mang++;
                countTemp=100;
            }
            if(countTemp>0){
                countTemp--;
            }
        }else{
            if(!reset){
                newGameCreated=false;
                startReset=System.nanoTime();
                reset=true;
                disspapear=true;
                explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),mayBay.getX(),mayBay.getY()-30,
                        100,100,25);
            }
            explosion.update();
            long resetElapsed=(System.nanoTime()-startReset)/1000000;
            if(resetElapsed>2500&&!newGameCreated){
                newGame();
            }
        }


    }
    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX=this.getWidth()/(float)WIDTH;
        final float scaleFactorY=this.getHeight()/(float)HEIGHT;

        if(canvas!=null) {
            final int scaleState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            backgroud.draw(canvas);
            //vẽ text thông tin
            drawText(canvas);
            //
            if(!disspapear){
                mayBay.draw(canvas);
            }
            //vẽ thiên thạch
            for(ThienThach t:thienThachs){
                t.draw(canvas);
            }
           /* if(temp){
                explosion.draw(canvas);
                temp=false;
            }*/
            for (Dan d:dans){
                d.draw(canvas);
            }
            if(started){
                explosion.draw(canvas);
            }

            canvas.restoreToCount(scaleState);
        }

    }
    //check va chạm thien thạch với máy bay
    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(),b.getRectangle())){
            return true;
        }
        return false;
    }
    public void newGame(){
        disspapear=false;
        thienThachs.clear();
        dans.clear();
        mayBay.resetScore();
        mayBay.setX(GamePanel.WIDTH/2-30);
        mayBay.setY(700);
        newGameCreated =true;
        soThienThachQua=0;
        mayBay.resetLevel();
        gold=100;
        mang=1;
    }
    public void drawText(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(25);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
        if(mayBay.getScore()>best){
            best=mayBay.getScore();
        }
        mayBay.updateLevel();
        //góc trái
        canvas.drawText("Điểm Cao Nhất: "+best,5,40,paint);
        canvas.drawText("Điểm: "+(mayBay.getScore()),5,80,paint);
        canvas.drawText("Mạng: "+mang,5,120,paint);
        canvas.drawText("Vàng: "+gold,5,160,paint);
        //góc phải
        canvas.drawText("Level: "+mayBay.getLevel(),520,40,paint);
        canvas.drawText("Số TT Vượt Qua: "+soThienThachQua,520,80,paint);
        if(!mayBay.isPlaying()&&newGameCreated&&reset&&!mangbool){
            Paint paint1=new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(50);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("PRESS TO START",WIDTH/2-200,HEIGHT/2,paint1);
        }
        if(mangbool){
            thienThachs.clear();
            dans.clear();
            mayBay.setX(GamePanel.WIDTH/2-30);
            mayBay.setY(700);
            mangbool=false;
            // mayBay.setPlaying(true);
        }
    }

}
