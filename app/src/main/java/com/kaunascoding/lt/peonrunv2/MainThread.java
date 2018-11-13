package com.kaunascoding.lt.peonrunv2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private int FPS = 30;

    private double averageFPS;

    private SurfaceHolder surfaceHolder;

    private GamePanel gamePanel;

    private boolean running;

    //canvas allows to draw images in code
    public static Canvas canvas;

    //construktorius for game thread
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    @Override
    public void run() {
        //locking for 30fps
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        while (running) {


            startTime = System.nanoTime();

            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();

                //synchoniziname kad priestu surfacce
                synchronized (surfaceHolder) {

                    this.gamePanel.update();
                    //kai sitas method yra kviecias turetu suteikti iliuzija animation
                    this.gamePanel.draw(canvas);
                }//end of sync


            } catch (Exception e) {

            }//end of try catch
            finally {
                if (canvas != null) {
                    try {

                        surfaceHolder.unlockCanvasAndPost(canvas);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //konvertiname i milli secs
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            //wait time yra tam tikras laikas kuris yra tarp sekancio frame kuris bus loope
            waitTime = targetTime-timeMillis;
            //
            try {
                this.sleep(waitTime);
            } catch (Exception e){}

            //counting total time of frames per second
            totalTime+=System.nanoTime()-startTime;
            frameCount++;
            if(frameCount==FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("avrg FPS"+averageFPS);
            }


        }//end of while




    }//end of run method

    public void setRunning (boolean b){
        running=b;
    }

}//end of thread
