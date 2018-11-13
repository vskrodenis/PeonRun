package com.kaunascoding.lt.peonrunv2;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Obstacle extends GameObject {

    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private int speed;



    public Obstacle(Bitmap res, int x, int y, int w, int h, int numberFrames) {
        super.x=x;
        super.y=y;
        widht=w;
        height=h;
        //obstacle judejimo greitis
        speed = 25;

        //sukuriam bit map table obstacles

        Bitmap [] image = new Bitmap[numberFrames];

        spritesheet = res;
        ;

        for (int i = 0; i <image.length ; i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*widht,0,widht,height);

        }
        animation.setFrames(image);
        animation.setDelay(100);

    }


    public void update (){
        x-=speed;


        animation.update();
    }

    public void draw(Canvas canvas) {

            canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    @Override
    public int getWidht() {
        return widht-10;
    }

    @Override
    public int getHeight() {
        return height-10;
    }
}
