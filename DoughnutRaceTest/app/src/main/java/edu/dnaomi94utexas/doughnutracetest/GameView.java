package edu.dnaomi94utexas.doughnutracetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ragan on 3/23/2017.
 */

public class GameView extends View {
    private static final String TAG = "GameView";
    private int mScore;
    private int mLives;

    private Character mCharacter;
    private ArrayList<Obstacle> mObstacles;
    private int chaSize;
    private int obsSize;

    private boolean mFirstDraw = true;
    public boolean mLeftPressed;
    public boolean mRightPressed;

    public TextView mLivesTextView;
    public TextView mScoreTextView;
    public GameLoop mGameLoop;

    private static final int LINE_SIZE = 12;
    private static final int NUM_LANES = 3;
    private ArrayList<Integer> lanes;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Called during the first onDraw call
    public void initialize() {
        // Initialize variables
        mScore = 0;
        mLives = 5;
        chaSize = getWidth() / NUM_LANES - NUM_LANES * LINE_SIZE;
        obsSize = chaSize / 2;

        // Initialize ArrayList of possible Obstacle positions at each lane
        int incr = getWidth() / 6;
        lanes = new ArrayList<Integer>();
        for(int i = 0; i < NUM_LANES; i++){
            lanes.add((int)((incr + (incr * i * 2)) - obsSize/2));
        }

        // Initializing Character
        Bitmap mCharacterBmp = BitmapFactory.decodeResource(getResources(), R.drawable.character1);
        mCharacterBmp = Bitmap.createScaledBitmap(mCharacterBmp, chaSize, chaSize, false);
        mCharacter = new Character(mCharacterBmp, getWidth() / 2 - chaSize / 2, chaSize);

        // Initializing Obstacles
        mObstacles = new ArrayList<Obstacle>();
        Bitmap mObstacleBmp = BitmapFactory.decodeResource(getResources(), R.drawable.broccoli);
        mObstacleBmp = Bitmap.createScaledBitmap(mObstacleBmp, chaSize/3, chaSize/3, false);
        mObstacles.add(new Obstacle(mObstacleBmp, getCoordinates(false), false, getHeight(), obsSize, lanes));
        mObstacles.add(new Obstacle(mObstacleBmp, getCoordinates(false), false, getHeight(), obsSize, lanes));
        mObstacles.add(new Obstacle(mObstacleBmp, getCoordinates(false), false, getHeight(), obsSize, lanes));

        // Initializing Donuts
        Bitmap mDonutBmp = BitmapFactory.decodeResource(getResources(), R.drawable.donut1);
        mDonutBmp = Bitmap.createScaledBitmap(mDonutBmp, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(mDonutBmp, getCoordinates(true), true, getHeight(), obsSize, lanes));

        Bitmap mDonutBmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.donut9);
        mDonutBmp2 = Bitmap.createScaledBitmap(mDonutBmp2, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(mDonutBmp2, getCoordinates(true), true, getHeight(), obsSize, lanes));

        Bitmap mDonutBmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.donut8);
        mDonutBmp3 = Bitmap.createScaledBitmap(mDonutBmp3, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(mDonutBmp3, getCoordinates(true), true, getHeight(), obsSize, lanes));
    }

    // Check collisions between Character and Obstacles
    private boolean checkCollisions(Obstacle obs, Character cha) {
        int chaLeft = cha.getX();
        int chaRight = chaLeft + cha.getWidth();
        int obsLeft = obs.getX();
        int obsRight = obsLeft + obs.getWidth();

        if ((chaLeft < obsLeft && chaRight > obsRight) || (chaLeft >= obsLeft && chaLeft < obsRight)
                || (chaRight <= obsRight && chaRight > obsLeft)) {
            if (cha.getHeight()  >= obs.getY()) {
                obs.setCollided(true);
                if (!obs.isDonut()) {
                    mLives--;
                    mLivesTextView.setText(String.valueOf("Lives:" + mLives));
                }
                if(obs.isDonut()) {
                    mScore++;
                }
                return true;
            }
        }
        return false;
    }

    // Check collisions between obstacles and donuts
    private boolean checkCollisions(int y, Obstacle other) {
        int top = y;
        int bottom = top + obsSize;
        int otherTop = other.getY();
        int otherBottom = otherTop + other.getHeight();

        if((bottom >= otherTop && bottom < otherBottom) || (top >= otherTop && top < otherBottom)
                || (top < otherTop && bottom > otherBottom)) {
            return true;
        }
        return false;
    }

    // Grab x and y coordinates for an Obstacle
    private int[] getCoordinates(boolean isDonut) {
        int[] coordinates = new int[2];
        int x = lanes.get((int)(Math.random() * NUM_LANES));

        // Randomly get a y-location for the obstacles
        int offset = chaSize + obsSize;
        int range = getHeight();
        int y = (int)(Math.random() * range) - offset;
        y = checkSafe(0, mObstacles.size(), x, y, range, offset, isDonut);

        coordinates[0] = x;
        coordinates[1] = y;

        // Return coordinates for the new obstacle
        return coordinates;
    }

    // Check to ensure no objects overlap each other
    private int checkSafe(int start, int end, int x, int y, int range, int offset, boolean isDonut) {
        int tempY = y;

        for (int i = start; i < end; i++) {
            Obstacle other = mObstacles.get(i);
            int otherX = other.getX();
            boolean otherIsDonut = other.isDonut();

            // Check that new obstacle will not overlap with any of the already created obstacles
            if (x == otherX && checkCollisions(tempY, other)) {
                Log.d(TAG, i + " THIS X: " + x + " THIS Y: " + tempY + " ++ obs X: " + otherX + " obs Y: " + other.getY());
            }

            // Check that 2 broccoli obstacles are not directly besides each other
            if (!isDonut && !otherIsDonut && (((int)Math.abs(x - otherX)) == (lanes.get(1) - lanes.get(0)))) {
                tempY = checkSafe(0, i, x, (int)(Math.random() * range) - offset, range, offset, isDonut);
            }
        }

        return tempY;
    }

    private void gameOver() {
        mGameLoop.gameOver = true;
        mGameLoop.stop();
        // TODO save to high scores list
    }

    public void moveCharacter(int distance) {
        mCharacter.update(distance);
    }

    public void onDraw(Canvas canvas) {
        // Move character
        if (mLeftPressed && !(mCharacter.getX() < 1)) {
//            Log.d(TAG, "insde mLeft X-COOR " + mCharacter.getX());
            moveCharacter(-20);
        }
        if (mRightPressed && (mCharacter.getX() < canvas.getWidth() - mCharacter.getWidth())) {
//            Log.d(TAG, "insde mRight Y-COOR " + mCharacter.getX());
            moveCharacter(20);
        }


        mScoreTextView.setText(String.valueOf("Score: " + mScore));
        if (mFirstDraw) {
            initialize();
            mLivesTextView.setText(String.valueOf("Lives:" + mLives));
            mScoreTextView.setText(String.valueOf("Score: " + mScore));
            mFirstDraw = false;
        }

        // Check if character has hit any obstacles
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
//            Log.d(TAG, i+ " obs coord" + obs.getX() + "     " + obs.getY() + "-" + (obs.getY() + obs.getHeight()));
            if (!obs.getCollided() && checkCollisions(obs, mCharacter)) {
                if (mLives <= 0) {
                    gameOver();
                }
            }
        }

        // Update both the obstacles and donuts
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
            obs.update();
        }

        // Set paint object attributes
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(LINE_SIZE);

        // Draw the obstacles and donuts
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
            obs.draw(canvas, paint);
        }

        // Draw road lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(), paint);
        canvas.drawLine(getWidth() / 3 * 2, 0, getWidth() / 3 * 2, getHeight(), paint);

        // Draw character
        mCharacter.draw(canvas, paint);
    }
}
