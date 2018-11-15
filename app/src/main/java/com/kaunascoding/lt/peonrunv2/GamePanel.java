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
    private int collisioncounter = 0;

    private Character character;

    private GameOver gameover;
    private ArrayList<Obstacle> currentCollisions;

    //    variables for game reseting
    private boolean newGameCreation;  // naujo game gaacreastion var
    private long startReset;          // reset time var ty kiek laiko uztruks
    private boolean reset;            // pats reset var
    private boolean dissapear;        //char on collisiton dissapearing
    private boolean started;          // ar game started var

    //obstacle

    private ArrayList<Obstacle> obstacle;
    private long obstacleStartTime;


    //ref of main thread
    private MainThread thread;


    public GamePanel(Context context) {
        super(context);
        //obj
        currentCollisions = new ArrayList<>();

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
        gameover = new GameOver(BitmapFactory.decodeResource(getResources(), R.drawable.gameover));

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
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            character.setJump(false);
//            return true;
//        }

        return super.onTouchEvent(event);
    }

    //pagal ideja game kiekviena sec or less turetu updatintis ir taip veikti viskas i hope so...
    private boolean colliding = false;

    public void update() {
        if (character.getPlaying()) {
            bgrd.update();
            character.update();


//            Moku OBSTACLE PADARYTI
            long obstacleElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;

            if (obstacleElapsed > 2500) {

                obstacle.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle),
                        1800, 830, 135, 135, 1));
                //smth bandymas kad judetu

                obstacleStartTime = System.nanoTime();
            }

            for (int i = 0; i < obstacle.size(); i++) {

                //update obstacle
                obstacle.get(i).update();
                if (collision(obstacle.get(i), character) && currentCollisions.indexOf(obstacle) == -1) {
                    currentCollisions.add(obstacle.get(i));

                }
                if (!collision(obstacle.get(i), character) && currentCollisions.indexOf(obstacle) != -1) {
                    currentCollisions.remove(obstacle.get(i));

                }

            }//end of for
            if (currentCollisions.size() > 0 && colliding == false) {
                colliding = true;
                collisioncounter++;
                System.out.println(collisioncounter);
                if (collisioncounter >= 10) {
                    character.setPlaying(false);
                }


            }
            if (currentCollisions.size() <= 0 && colliding == true) {
                colliding = false;
            }


        }
    }//end of update

    //collision meth
    public boolean collision(GameObject character, GameObject obstacle) {


        if (Rect.intersects(character.getRectangle(), obstacle.getRectangle())) {
            return true;
        }
        return false;
    }//end of collision

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
            if (collisioncounter >= 10) {
                gameover.draw(canvas);
            }
            drawText(canvas);
        }

    }//end of draw

    //drawing score methid
    public void drawText(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
        canvas.drawText("Distance  " + (character.getDistance() * 2), 10, 50, paint);

    }//end of drawtext


}//end of gamepanel
