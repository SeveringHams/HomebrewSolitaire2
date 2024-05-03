package com.severinghams.homebrewsolitaire;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final GameBoard boardView;
    private boolean running;
    public static Canvas canvas;
    public MainThread(SurfaceHolder surfaceHolder, GameBoard boardView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.boardView = boardView;
    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
    @Override
    public void run() {
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.boardView.update();
                    this.boardView.draw(canvas);
                    this.boardView.invalidate();
                }
            } catch (Exception e) {
                System.out.println("CATCH");
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}
