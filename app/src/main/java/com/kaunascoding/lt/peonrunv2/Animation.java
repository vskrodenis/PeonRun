package com.kaunascoding.lt.peonrunv2;

import android.graphics.Bitmap;

public class Animation {
    //animation per frame
    private Bitmap[] frames;

    //animation tracking per frame
    private int currecntFrame;

    //timer of animaton
    private long startTime;

    //delay of animination per frame
    private long delay;

    //kai kolidins su obsticle bus game over ir tada turetu but rip animation
    private boolean colidedAnimation;


    public void setFrames(Bitmap[] frames){

        this.frames=frames;
        //image will start from 1 frame/sprite
        currecntFrame = 0;
        // timer of animations
        startTime = System.nanoTime();
    }
    //delay tarp frames
    public void setDelay(long d){
        delay = d;
    }
    public void setFrame(int i){
        currecntFrame = i;
    }

    public void update(){
        //nusakome kuris frame image bus grazinamas naudojama tam kad zinoti kada object atsiras
        //ir kokius actions darys.
        long elapsed = (System.nanoTime()-startTime)/1000000;
        //delay yra 10milisecs

        if(elapsed>delay){

            currecntFrame++;
            startTime = System.nanoTime();
        }
        if (currecntFrame==frames.length){
            currecntFrame=0;
            colidedAnimation = true;
        }
    }

    //method piest char animations

    public Bitmap getImage(){
        return frames[currecntFrame];
    }

    //grazinimas frames ir ar buvo colidinta su obsticle animation

    public int getFrame(){
        return currecntFrame;
    }
    public boolean colidedAnimation (){
        return colidedAnimation;
    }



}
