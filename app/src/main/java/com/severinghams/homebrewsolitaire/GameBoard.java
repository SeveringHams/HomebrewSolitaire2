package com.severinghams.homebrewsolitaire;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.annotation.NonNull;

import com.severinghams.homebrewsolitaire.R;
import com.severinghams.homebrewsolitaire.core.BaseSingleDeckGameObject;
import com.severinghams.homebrewsolitaire.core.KlondikeGameObject;

/**
 * TODO: document your custom view class.
 */
public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {

    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();
    int contentWidth = getWidth() - paddingLeft - paddingRight;
    int contentHeight = getHeight() - paddingTop - paddingBottom;
    BaseSingleDeckGameObject gameObject;
    private final MainThread thread;
    public GameBoard(Context context) {
        super(context);
        init(null, 0);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameObject = new KlondikeGameObject(0, context);
        getHolder().addCallback(this);
        this.requestFocusFromTouch();
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameObject = new KlondikeGameObject(0, context);
        getHolder().addCallback(this);
        this.requestFocusFromTouch();
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameObject = new KlondikeGameObject(0, context);
        getHolder().addCallback(this);
        this.requestFocusFromTouch();
    }

    public void update() {

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
        System.out.println(motion.getX()+" "+motion.getY()+" "+motion.getX());
        performClick();
        gameObject.doTouchEvent(motion);
        return true;
    }

    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameObject.drawGame(canvas);

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

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
}