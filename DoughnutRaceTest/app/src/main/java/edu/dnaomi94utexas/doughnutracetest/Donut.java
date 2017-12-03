package edu.dnaomi94utexas.doughnutracetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by naomimartinez on 4/3/17.
 */

public class Donut {
    private Bitmap donut;
    private int x;
    private int y;
    private static final int SPEED = 5;
    public boolean collided;

    public Donut(int x, int y, Bitmap bmp) {
        donut = bmp;
        this.x = x;
        this.y = y;
        collided = false;
    }

    public void update() {
        if (y <= 0) {
            this.y = 2000;
            collided = false;
        }
        this.y -= 2 * SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getCollided() {
        return collided;
    }

    public void setCollided(boolean c) {
        collided = c;
    }

    public int getWidth() {
        return donut.getWidth();
    }

    public int getHeight() {
        return donut.getHeight();
    }

    public void draw(Canvas c, Paint p) {
        c.drawBitmap(donut, x, y, p);
    }

}
