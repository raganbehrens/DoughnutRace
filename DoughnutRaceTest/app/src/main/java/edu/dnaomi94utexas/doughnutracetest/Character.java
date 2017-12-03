package edu.dnaomi94utexas.doughnutracetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Ragan on 3/27/2017.
 */

public class Character {
    private Bitmap character;
    private int x;
    private int y;
    private int size;

    public Character(Bitmap bmp, int x, int size) {
        character = bmp;
        this.x = x;
        this.size = size;
    }

    public void update(int x) {
        this.x += x;
    }

    public void draw(Canvas c, Paint p) {
        c.drawBitmap(character, x, 0, p);
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
