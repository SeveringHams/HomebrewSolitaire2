package com.severinghams.homebrewsolitaire.core;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.severinghams.homebrewsolitaire.core.enums.EnumColour;
import com.severinghams.homebrewsolitaire.core.enums.EnumRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumSuit;

public class CardObject {
    public boolean isFaceDown = false;
    public final EnumRank rank;
    public final EnumSuit suit;
    public final EnumColour colour;
    public int verticalPos;
    public int horizontalPos;


    public final Drawable drawable;
    public final Drawable drawableBack;
    public CardObject (EnumRank rank, EnumSuit suit, Drawable drawFace, Drawable drawBack) {
        this.rank = rank;
        this.suit = suit;
        this.colour = this.suit.colour;
        drawable = drawFace;
        drawableBack = drawBack;
    }

    public void drawCardTop(Canvas canvas) {
        int[] pos = {horizontalPos,verticalPos};
        double[] canSize = {canvas.getWidth()/21.0, canvas.getWidth()/63.0, canvas.getWidth()/90.0, canvas.getWidth()/8.0};
        //System.out.println(horizontalPos+" "+verticalPos);
        Rect rectangle = new Rect(
                (int)(pos[0]*canSize[0]+canSize[2]),
                (int)(pos[1]*canSize[1]+canSize[2]),
                (int)((pos[0]+3)*canSize[0]-canSize[2]),
                (int)((pos[1]+3)*canSize[1]+canSize[3]));

        if (isFaceDown) {
            drawableBack.setBounds(rectangle);
            drawableBack.draw(canvas);
        } else {
            drawable.setBounds(rectangle);
            drawable.draw(canvas);
        }
    }


    public void drawCardBottom(Canvas canvas) {
        int[] pos = {horizontalPos,verticalPos};
        double[] canSize = {canvas.getWidth()/21.0, canvas.getWidth()/63.0, canvas.getWidth()/90.0, canvas.getWidth()/8.0};
        //System.out.println(horizontalPos+" "+verticalPos);
        Rect rectangle = new Rect(
                (int)(pos[0]*canSize[0]+canSize[2]),
                (int)(canvas.getHeight()-((pos[1]+3)*canSize[1]+canSize[3])),
                (int)((pos[0]+3)*canSize[0]-canSize[2]),
                (int)(canvas.getHeight()-(pos[1]*canSize[1]+canSize[2])));

        if (isFaceDown) {
            drawableBack.setBounds(rectangle);
            drawableBack.draw(canvas);
        } else {
            drawable.setBounds(rectangle);
            drawable.draw(canvas);
        }
    }

}
