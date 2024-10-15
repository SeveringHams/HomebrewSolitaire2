package com.severinghams.homebrewsolitaire;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;

import com.severinghams.homebrewsolitaire.core.BaseSingleDeckGameObject;
import com.severinghams.homebrewsolitaire.core.KlondikeGameObject;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * The game board view.
 */
public class GameBoard extends GLSurfaceView implements SurfaceHolder.Callback2, GLSurfaceView.Renderer {
    private final GameRenderer renderer;
    BaseSingleDeckGameObject gameObject;
    private final MainThread thread;
    public GameBoard(Context context) {
        super(context);
        init(null, 0);
        getHolder().addCallback(this);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = new GameRenderer();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameObject = new KlondikeGameObject(0, context);
        gameObject.updateGame();
    }
    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        getHolder().addCallback(this);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = new GameRenderer();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameObject = new KlondikeGameObject(0, context);
        gameObject.updateGame();
    }

    public void update() {
        //gameObject.updateGame();
    }

    private void init(AttributeSet attrs, int defStyle) {
        this.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public boolean onTouchEvent(MotionEvent motion) {
        //System.out.println(motion.getX()+" "+motion.getY()+" "+motion.toString());
        gameObject.doTouchEvent(motion);
        performClick();
        return true;
    }

    public boolean performClick() {
        super.performClick();
        return true;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        System.out.println("test?");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gameObject.onSetSize(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gameObject.drawGame(gl);
    }

}