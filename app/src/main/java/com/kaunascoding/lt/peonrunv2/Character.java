package com.kaunascoding.lt.peonrunv2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Character extends GameObject{


    private Bitmap spritesheet;

    //variable kai kiekviena karta paliesim ekrana nes char tik mokes jump
    private double dya;
    //var del jump
    //distacne
    private int distance;
    protected boolean jump;
    private int jumpTime=0;
    //var kad char zinotu kada game prasideda
    private boolean playing;

    //sukuriame obj char
    private Animation animation = new Animation();
    private long startTime;

    public Character(Bitmap res, int widthchar, int heightchar, int nrFrames) {
        //nusakom kur musu char bus nupriestas pacioje pradzioje
        x = 75;
        y= 700;
        //game starte distance yra nulis
        distance = 0;
        //bandymas sukurti jump smth smth

        dy=0;

        height=heightchar;
        widht=widthchar;

        //sukuriame kad pajamtu spirtes char turi tris frame aninamtion

        Bitmap [] image = new Bitmap[nrFrames];
        spritesheet = res;

        for (int i = 0; i <image.length ; i++) {

            image[i]= Bitmap.createBitmap(spritesheet, i*widht,0,widht,height);

        }

        //setting animation and adding delay

        animation.setFrames(image);
        animation.setDelay(100);//su 100 oke

        //timeris kad galetumem naudot update method
        startTime=System.nanoTime();


    }
    //method tam kad zinotu ar palietem ekrana ir daro jupm
    public void setJump(boolean b){
        if(!jump) {
            jump = b;
            jumpTime=0;
        }


    }

    public void update() {
     //jump??????? dang game physics hard stuff brain hurts

        if(jump) {
            float H = -300;
            float jumpDuration = 35;//35 buva
            double l = 740 + H * Math.sin((jumpTime /jumpDuration)* ( Math.PI));
            jumpTime++;
            y = (int) l;
            if (jumpTime >= jumpDuration) {
                jump = false;

            }
        }
        //distance counter by time
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed>100){
            distance++;
            startTime=System.nanoTime();
        }

        animation.update();


    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetdistance(){distance = 0;}// buvo reset DYA DYA = 0
    public int getDistance (){return  distance;}


}
