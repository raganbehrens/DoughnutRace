package edu.dnaomi94utexas.doughnutracetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Ragan on 3/27/2017.
 */

public class Obstacle {
    private static final int SPEED = 5;

    private Bitmap obstacle;
    private int x;
    private int y;
    private int resetY;
    private int size;

    private boolean isDonut;
    private boolean collided;
    private ArrayList<Integer> lanes;

    public Obstacle(Bitmap bmp, int[] coordinates, boolean isDonut, int canvasHeight,
                    int size, ArrayList<Integer> possibleLanes) {
        collided = false;
        obstacle = bmp;

        this.x = coordinates[0];
        this.y = coordinates[1];

        this.isDonut = isDonut;
        this.resetY = canvasHeight;
        this.size = size;

        this.lanes = new ArrayList<Integer>();
        for (int i = 0; i < possibleLanes.size(); i++) {
            lanes.add(possibleLanes.get(i));
        }
    }

    public void update() {
        if (collided || y <= -getHeight()) {
            this.y = resetY;
            collided = false;

            int rand = (int) (Math.random() * 3);
            this.x = this.lanes.get(rand);
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
        return size;
    }

    public int getHeight() {
        return size;
    }

    public boolean isDonut() {
        return isDonut;
    }

    public void draw(Canvas c, Paint p) {
        c.drawBitmap(obstacle, x, y, p);
    }
}
