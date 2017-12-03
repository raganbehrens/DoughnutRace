package edu.dnaomi94utexas.doughnutracetest;

/**
 * Created by Ragan on 3/27/2017.
 */
import android.os.Looper;
import android.os.Handler;

/**
 * Created by scottm on 3/9/2016.
 * Adapted from Stanford Animation Loop class my Marty Stepp.
 */
public class GameLoop {
    private static final String TAG = "GameLoop";
    public boolean gameOver;
    private GameView mView = null;
    private int fps;
    private Thread thread = null;
    private Handler handler = null;
    private volatile boolean running = false;


    /**
     * Constructs a new drawing thread to update the given view
     * the given number of times per second.
     * Does NOT start the thread running; call start() to do so.
     */
    public GameLoop(GameView view, int fps) {
        if (view == null || fps <= 0) {
            throw new IllegalArgumentException();
        }
        this.mView = view;
        this.fps = fps;
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns true if the drawing thread is currently started and running.
     */
    public boolean isRunning() {
        return thread != null;
    }

    /**
     * Starts the thread running so that it will repaint the view repeatedly.
     */
    public void start() {
        if (thread == null && !gameOver) {
            thread = new Thread(new MainRunner());
            thread.start();
        }
    }

    /**
     * Stops the thread so that it will not repaint the view any more.
     */
    public void stop() {
        if (thread != null) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                // empty
            }
            thread = null;
        }
    }

    /*
     * Small runnable helper class that contains the thread's main loop
     * to repeatedly sleep-and-redraw the view.
     */
    private class MainRunner implements Runnable {
        public void run() {
            running = true;
            while (running) {
                // sleep for a short time between frames of animation
                try {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException ie) {
                    running = false;
                }

                // might have been stopped while sleeping
                if (!running) {
                    break;
                }

                // post a message that will cause the view to redraw
                handler.post(new Updater());
            }
        }
    }

    /*
     * Small runnable helper class needed by Android to redraw a view.
     */
    private class Updater implements Runnable {
        public void run() {
            mView.postInvalidate();
        }
    }
}