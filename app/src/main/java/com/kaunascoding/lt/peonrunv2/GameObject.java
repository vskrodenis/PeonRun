package com.kaunascoding.lt.peonrunv2;

import android.graphics.Rect;

public abstract class GameObject {


    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int height;
    protected int widht;

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidht() {
        return widht;
    }


    //sitas methodas pades ieskoti collision tarp char ir obstacle
    public Rect getRectangle() {

        return new Rect(x, y, x + widht, y + height);
    }

}
