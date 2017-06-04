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

import com.example.kunsubin.banthienthach.CSDL.BussinessGame;
import com.example.kunsubin.banthienthach.CSDL.StaticObject;
import com.example.kunsubin.banthienthach.Chicken.ConGa;
import com.example.kunsubin.banthienthach.Chicken.TrungGa;

import java.util.ArrayList;
import java.util.List;
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
    private boolean started=false;
    private int best;
    private boolean stateDan=false;

    private int soThienThachQua=0;

    private  int gold=100;
    private int mang=2;
    private int countTemp=0;
    private boolean mangbool=false;
    //vẽ
    private List<Explosion> explosionList;
    private long explosionStartTime;

    private ConGa conGa=null;
    private long conGaStartTime;
    private int countDieGa=10;

    private ArrayList<TrungGa> trungGalist;
    private long trungGaStartTime;

    //
    private ArrayList<ThienThachLeftRight> thienThachLeftRights;
    private long thienthachLeftRight;

    private long timeUpMang;
    private boolean boolmang=false;
    private int mangY;
    private boolean gameOver=false;

    private long timeNextToGa;

    private String user="";

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
        mayBay=new MayBay(BitmapFactory.decodeResource(getResources(), R.drawable.phithuyen),107,160,5);
       // mayBay=new MayBay(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach),49,49,1);
        thienThachs=new ArrayList<ThienThach>();
        thienthachStartTime= System.nanoTime();
        dans=new ArrayList<Dan>();
        danStartTime=System.nanoTime();

        explosionList=new ArrayList<Explosion>();
        explosionStartTime= System.nanoTime();

        conGaStartTime=System.nanoTime();


        trungGalist=new ArrayList<TrungGa>();
        trungGaStartTime=System.nanoTime();

        thienThachLeftRights=new ArrayList<ThienThachLeftRight>();
        thienthachLeftRight=System.nanoTime();

        timeNextToGa=System.nanoTime();

        //get name user
        if(StaticObject.getUser()!=null){
            user=StaticObject.getUser();
        }

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
                mayBay.x=(int) event.getX()-53;
                mayBay.y=(int) event.getY()+80;
                stateDan=true;

            }
            return true;
        }
        //nhấn giữ di chuyển
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            mayBay.x=(int) event.getX()-53;
            mayBay.y=(int) event.getY()+80;
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
            //cập nhật trạng thái level 2
            long congaElapsed=(System.nanoTime()-conGaStartTime)/1000000;
            if(congaElapsed>1000){
                if(mayBay.getLevel()==2){
                    if(conGa!=null){
                        conGa.update();
                        //add trứng gà
                        if(trungGalist.size()==0){
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.left),conGa.getX(),conGa.getY()+70,50,59,1,0));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.left),conGa.getX(),conGa.getY()+174,50,59,1,0));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.right),conGa.getX()+148,conGa.getY()+70,51,50,1,2));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.right),conGa.getX()+148,conGa.getY()+174,51,50,1,2));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.center),conGa.getX()+62,conGa.getY()+174,50,62,1,1));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.center),conGa.getX(),conGa.getY()+174,50,62,1,1));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.center),conGa.getX()+148,conGa.getY()+174,50,62,1,1));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.left),conGa.getX()+148,conGa.getY()+174,50,59,1,0));
                            trungGalist.add(new TrungGa(BitmapFactory.decodeResource(getResources(),R.drawable.right),conGa.getX(),conGa.getY()+174,51,50,1,2));

                        }
                        if(collision(conGa,mayBay)){
                            conGa=null;
                            //giảm mạng khi phi thuyền va chạm với con gà
                            mang--;
                            if(mang>0){
                                mangbool=true;
                            }else{
                                mangbool=false;
                                mayBay.setPlaying(false);
                            }

                        }
                    }else {
                        explosionList.clear();
                        thienThachs.clear();
                        dans.clear();
                        long gaElapsed=(System.nanoTime()-timeNextToGa)/1000000;
                        if(gaElapsed>2000){
                            conGa=new ConGa(BitmapFactory.decodeResource(getResources(),R.drawable.bigchicken),174,148,1);
                        }
                    }
                }
                conGaStartTime=System.nanoTime();
            }
            //xử lý di chuyển của thiên thạch
            long thienthachElapsed=(System.nanoTime()-thienthachStartTime)/1000000;
            if(thienthachElapsed>(1500-(mayBay.getScore())/6)){
                    //level 1 random 2 thiên thạch cùng lúc
                    if(mayBay.getLevel()==1){
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach3),
                                random.nextInt(768-78)+1,10,78,73,mayBay.getScore(),3));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach3),
                                random.nextInt(768-78)+1,10,78,73,mayBay.getScore(),3));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach3),
                                random.nextInt(768-78)+1,10,78,73,mayBay.getScore(),3));
                       /* thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/
                    }
                    //level 3 ramdom 3 thiên thạch cùng lúc
                    if(mayBay.getLevel()==3){
                        /*thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),1,random.nextInt(800)+1,73,72,16,0));
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),1,random.nextInt(800)+1,73,72,16,0));
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),1,random.nextInt(800)+1,73,72,16,0));
                    }
                    //level 4 ramdom 4 thiên thạch cùng lúc
                    if(mayBay.getLevel()==4){
                        /*thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),695,random.nextInt(800)+1,73,72,16,1));
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),695,random.nextInt(800)+1,73,72,16,1));
                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),695,random.nextInt(800)+1,73,72,16,1));

                    }
                    //level 5 ramdom 5 thiên thạch cùng lúc
                    if(mayBay.getLevel()==5){
                       /* thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach3),
                                random.nextInt(768-78)+1,10,78,73,mayBay.getScore(),3));
                       /* thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/

                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),1,random.nextInt(800)+1,73,72,16,0));
                        //thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),1,random.nextInt(700)+1,73,72,16,0));

                        thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),695,random.nextInt(800)+1,73,72,16,1));
                       //thienThachLeftRights.add(new ThienThachLeftRight(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach2),695,random.nextInt(700)+1,73,72,16,1));
                      /*  thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));
                        thienThachs.add(new ThienThach(BitmapFactory.decodeResource(getResources(),R.drawable.thienthach1),
                                random.nextInt(768-81)+1,10,80,80,mayBay.getScore(),1));*/
                    }
               // }
                thienthachStartTime=System.nanoTime();
            }
            //thiên thach level 3,4,5
                for (int i=0;i<thienThachLeftRights.size();i++){
                    thienThachLeftRights.get(i).update();
                    //nếu xảy ra va chạm giữa thiên thạch với máy bay thì dừng game
                    if(collision(thienThachLeftRights.get(i),mayBay)){
                        thienThachLeftRights.remove(i);
                        //giảm mạng khi phi thuyền va chạm với thiên thạch
                        mang--;
                        if(mang>0){

                            mangbool=true;

                        }else{
                            mangbool=false;
                            mayBay.setPlaying(false);
                        }

                    }
                    //check thiên thạch qua khỏi màn hình chưa
                    if(thienThachLeftRights.get(i).getX()>780||thienThachLeftRights.get(i).getY()>1300||thienThachLeftRights.get(i).getX()<-10){
                        thienThachLeftRights.remove(i);
                        soThienThachQua++;
                        if(gold==0)
                            gold=0;
                        else
                            gold--;
                        break;
                    }

                }
            //cập nhật trạng thái di chuyển của các thiên thạch
            for(int i=0;i<thienThachs.size();i++){
                //cập nhật trạng thái thiên thạch
                thienThachs.get(i).update();
                //nếu xảy ra va chạm giữa thiên thạch với máy bay thì dừng game
                if(collision(thienThachs.get(i),mayBay)){
                    thienThachs.remove(i);
                    //giảm mạng khi phi thuyền va chạm với thiên thạch
                    mang--;
                    if(mang>0){
                        mangbool=true;
                      /*  explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),mayBay.getX(),mayBay.getY()-30,
                                100,100,25);
                        explosion.update();
*/
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
                    dans.add(new Dan(BitmapFactory.decodeResource(getResources(),R.drawable.dando),mayBay.getX()+40,mayBay.getY(),26,82,5));
                    stateDan=false;
                }
                danStartTime=System.nanoTime();
            }
            //cập nhật trạng thái đạn
            for(int i=0;i<dans.size();i++){
                //cập nhật trạng thái đạn
                dans.get(i).update();
                //nếu xảy ra va chạm giữa đạn với thiên thạch thì dừng bỏ
                for (int j=0;j<thienThachs.size();j++){
                    if(collision(dans.get(i),thienThachs.get(j))){
                        //tạo nổ kki đạn chạm thiên thạch
                        explosionList.add(new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),dans.get(i).getX()-37,dans.get(i).getY(),
                                    100,100,25));

                        gold++;
                        mayBay.setUpScore();
                       /* if(mayBay.getScore()==30)
                            timeUpMang=System.nanoTime();*/
                        //gắn mốc thời gian chuyển level 2
                        if(mayBay.getScore()==30){
                            conGaStartTime=System.nanoTime();
                            timeNextToGa=System.nanoTime();
                        }
                        //loại bỏ dạn với thiên thạch bị bắn hạ
                        dans.remove(i);
                        thienThachs.remove(j);
                     }
                }
                //thiên thạch left right
                for (int j=0;j<thienThachLeftRights.size();j++){
                    if(collision(dans.get(i),thienThachLeftRights.get(j))){
                        //tạo nổ kki đạn chạm thiên thạch
                        /*long expElapsed=(System.nanoTime()-explosionStartTime)/1000000;
                        if(expElapsed>100){*/
                            explosionList.add(new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),dans.get(i).getX()-37,dans.get(i).getY(),
                                    100,100,25));
                       /*     explosionStartTime=System.nanoTime();
                        }
*/
                        gold++;
                        mayBay.setUpScore();
                        //loại bỏ dạn với thiên thạch bị bắn hạ
                        dans.remove(i);
                        thienThachLeftRights.remove(j);
                    }
                }
                //va chạm với gà
                if(mayBay.getLevel()==2){
                    if(collision(dans.get(i),conGa)){
                        //tạo nổ kki đạn chạm con gà
                       /* long expElapsed=(System.nanoTime()-explosionStartTime)/1000000;
                        if(expElapsed>100){*/
                            explosionList.add(new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),dans.get(i).getX()-37,dans.get(i).getY(),
                                    100,100,25));
                       /*     explosionStartTime=System.nanoTime();
                        }*/
                        if(countDieGa>0){
                            countDieGa--;
                        }else{
                            conGa=null;
                            mayBay.setScore(400);
                        }
                        dans.remove(i);
                    }
                }
                //remove đạn
                if(dans.get(i).getY()<-50){
                    dans.remove(i);
                    break;
                }
            }
            //update trạng thái vụ nổ
            for (int i=0;i<explosionList.size();i++){
                explosionList.get(i).update();
            }
            //50 điểm cho lên 1 mạng
            if(mayBay.getScore()>0&&mayBay.getScore()%50==0&&countTemp==0){
                mang++;
                boolmang=true;
                timeUpMang=System.nanoTime();
                mangY=620;
                countTemp=100;
            }
            if(countTemp>0){
                countTemp--;
            }
            //cập nhật trạng thái trứng gà level 2
            if(mayBay.getLevel()==2){
                for(int i=0;i<trungGalist.size();i++){
                    //cập nhật trạng thái trứng gà
                    trungGalist.get(i).update();
                    //kiểm tra trứng gà ra khỏi màn hình chưa để xóa đi
                    if(trungGalist.get(i).getX()>780||trungGalist.get(i).getX()<-10||trungGalist.get(i).getY()>1300){
                        trungGalist.remove(i);
                    }
                    //va chạm máy bay với trứng
                    if(collision(trungGalist.get(i),mayBay)){
                        trungGalist.remove(i);
                        //giảm mạng khi phi thuyền va chạm với thiên thạch
                        mang--;
                        if(mang>0){
                            mangbool=true;
                        }else{
                            mangbool=false;
                            mayBay.setPlaying(false);
                        }
                    }
                    //
                }
            }

        }else{
            if(!reset){
                newGameCreated=false;
                startReset=System.nanoTime();
                reset=true;
                disspapear=true;
                explosion=new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),mayBay.getX(),mayBay.getY()-30,
                        100,100,25);

                gameOver=true;
                thienThachs.clear();
                thienThachLeftRights.clear();
                trungGalist.clear();
                explosionList.clear();
                dans.clear();
                conGa=null;
            }

            explosion.update();

            long resetElapsed=(System.nanoTime()-startReset)/1000000;
            if(resetElapsed>3500&&!newGameCreated){
                //cập nhật data
                BussinessGame bussinessGame=new BussinessGame(getContext());
                boolean f=bussinessGame.updateDiem(StaticObject.getUser(),best,gold);
                //new game
                gameOver=false;
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
            //va chạm đạn với thiên thạch
            for (Explosion e:explosionList){
                e.draw(canvas);
                //kiểm tra coi vụ nổ tại vị trí đã xong rùi thì remove di
                if(e.isPlayedOnce()){
                    explosionList.remove(e);
                }
            }

            //
          /*  if(mangbool){
                explosion.draw(canvas);
            }*/
            //
            if(!disspapear){
                mayBay.draw(canvas);
            }
            //vẽ thiên thạch
            for(ThienThach t:thienThachs){
                t.draw(canvas);
            }
            //vẽ khi tăng mạng lên
            if(boolmang){
                 long gaElapsed=(System.nanoTime()-timeUpMang)/1000000;
                 if(gaElapsed>2000){
                     boolmang=false;
                 }
                 canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.themmang),324,mangY,null);
                 mangY-=10;
            }
            if(gameOver&&started){
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gameover),237,493,null);
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
            if(mayBay.getLevel()==2&&conGa!=null){
                conGa.draw(canvas);
                 for (TrungGa tg:trungGalist){
                     tg.draw(canvas);
                 }
                 //canvas.drawText("Số TT Vượt Qua: "+soThienThachQua,520,80,paint);
            }
            //thiên thạch trong các level 3,4,5
            for (ThienThachLeftRight tt:thienThachLeftRights){
                tt.draw(canvas);
            }

            canvas.restoreToCount(scaleState);
        }

    }
    //check va chạm hai đối tượng
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
        mang=2;
        explosionList.clear();

        trungGalist.clear();
        thienThachLeftRights.clear();
        conGa=null;
        countDieGa=10;
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
        canvas.drawText("Người chơi: "+user,520,120,paint);
        //
        if(mayBay.getLevel()==2){
            canvas.drawText(""+countDieGa,380,120,paint);
        }


        if(!mayBay.isPlaying()&&newGameCreated&&reset&&!mangbool){
            Paint paint1=new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(50);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("PRESS TO START",WIDTH/2-200,HEIGHT/2,paint1);
        }
        if(mangbool){
            thienThachs.clear();
            thienThachLeftRights.clear();
            dans.clear();
            mayBay.setX(GamePanel.WIDTH/2-30);
            mayBay.setY(700);
            mangbool=false;
            //level 2
            trungGalist.clear();
            conGa=null;
            countDieGa=10;
            // mayBay.setPlaying(true);
        }
    }

}
