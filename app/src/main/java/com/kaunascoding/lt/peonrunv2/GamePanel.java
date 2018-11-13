package com.kaunascoding.lt.peonrunv2;

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

import java.util.Random;
import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Random random = new Random();
    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;

    public static final int MOVINGSPEED = -25;//50
    private Background bgrd;

    private Character character;

    //obstacle

    private ArrayList<Obstacle> obstacle;
    private long obstacleStartTime;


    //ref of main thread
    private MainThread thread;


    public GamePanel(Context context) {
        super(context);
        //obj


        //gamepanel surface makes focusable for haldling events
        setFocusable(true);

        //callback allosws to intercept events
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(), this);

        //in game draw image
        bgrd = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));

        //REIKES EDITING cia kad graziai BUTU
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.characterrun), 321, 270, 4);


        //obstacle creation
        obstacle = new ArrayList<Obstacle>();
        obstacleStartTime = System.nanoTime();


        //starting game loop/game
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //stoping game loop/game

        boolean retry = true;

        while (retry) {
            try {
                //su situ pasakome jog nebenorime kad game vyktu
                thread.setRunning(false);
                thread.join();
                //pradejus game bus naujas  thread
                thread = null;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //char do smth kai ekranas lieciamas
//          working stufff
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!character.getPlaying()) {
                character.setPlaying(true);
            } else {
                character.setJump(true);
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            character.setJump(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    //pagal ideja game kiekviena sec or less turetu updatintis ir taip veikti viskas i hope so...

    public void update() {
        if (character.getPlaying()) {
            bgrd.update();
            character.update();

//              border nereik nes padariau kad nekristu char
//            long borderElapsed = (System.nanoTime() - borderStartTime) / 1000000;
//            if (borderElapsed > 100) {
//                borders.add(new Border(BitmapFactory.decodeResource(getResources(), R.drawable.border), WIDTH, 960));
//                borderStartTime = System.nanoTime();
//            }
//            for (int i = 0; i < obstacle.size(); i++) {
//                //update obstacle
//                obstacle.get(i).update();
//
//                if (obstacle.get(i).getX() < 10) {
//                    obstacle.remove(i);
//                }
//            }
//////            NEMOKU OBSTACLE PADARYTI
            long obstacleElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;

            if (obstacleElapsed > 2500) {

                obstacle.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle),
                        1800, 830, 135, 135, 1));
//                //smth bandymas kad judetu

                obstacleStartTime = System.nanoTime();
            }
            for (int i = 0; i < obstacle.size(); i++) {
                //update obstacle
                obstacle.get(i).update();
                if (collision(obstacle.get(i), character)) {
                    character.setPlaying(false);

                }
                if (obstacle.get(i).getX() < 50) {
                    obstacle.remove(i);
                }
            }

        }
    }//end of update
    //collision meth
    public boolean collision(GameObject character, GameObject obstacle){
        if(Rect.intersects(character.getRectangle(),obstacle.getRectangle())){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {


        //kad game butu compatible reik padaryti adaptavie scalinga bgrd

        super.draw(canvas);
        final float scaleFactorX = getWidth() / WIDTH * 1.f;
        final float scaleFactorY = getHeight() / HEIGHT * 1.f;

        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bgrd.draw(canvas);
            character.draw(canvas);

            canvas.restoreToCount(savedState);

            for (Obstacle obst : obstacle) {
                obst.draw(canvas);


            }
            drawText(canvas);
        }

    }

    //drawing score methid
    public void  drawText(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
        canvas.drawText("Distance" + (character.getDistance()*2), 10,50, paint);

    }


}//end of gamepanel
