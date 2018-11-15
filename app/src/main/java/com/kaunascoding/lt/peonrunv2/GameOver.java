package com.kaunascoding.lt.peonrunv2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameOver {
    public Bitmap image;
    private int x, y,dx;
    public GameOver(Bitmap res) {

        image = res;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }
}

