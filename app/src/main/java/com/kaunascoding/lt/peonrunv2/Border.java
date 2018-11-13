//package com.kaunascoding.lt.peonrunv2;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//
//public class Border extends GameObject{
//
//
//    private Bitmap image;
//    public Border(Bitmap res, int x, int y){
//        height = 1;
//        widht = 1920;
//        //is GameObject pasiemame koordinates
//
//        this.x = x;
//        this.y = y;
//        //nustatome moving speed kad sutaptu su viso game
//        dx= GamePanel.MOVINGSPEED;
//        //sukuriam image ir jis turi viena frame
//        image = Bitmap.createBitmap(res, 0,0,widht,height);
//
//
//    }
//    public void update(){
//
//
//    }
//    public void draw(Canvas canvas){
//        //draw border shananigans
//        canvas.drawBitmap(image,x,y,null);
//    }
//}
