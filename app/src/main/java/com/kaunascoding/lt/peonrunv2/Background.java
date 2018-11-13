package com.kaunascoding.lt.peonrunv2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {


    public Bitmap image;
    private int x, y,dx;

    public Background(Bitmap res) {

        image = res;
        //bcgrd moving speed
        dx=GamePanel.MOVINGSPEED;


    }//

    //pabaigoje backgrd desime nauja bgrd ir judejimas vyks i kaire puse bgrd
    public void update() {
        x+=dx;

        if(x<-GamePanel.WIDTH){//WIDHT telf screen
            x=0;
        }


    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y,null);

        //kai pirmd bgd x neigiamas reik kad piestu nauja bgrd pacioje desineje
        if(x<0){
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }
    }


}
