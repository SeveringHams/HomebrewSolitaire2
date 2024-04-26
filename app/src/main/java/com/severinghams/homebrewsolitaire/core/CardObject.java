package com.severinghams.homebrewsolitaire.core;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.severinghams.homebrewsolitaire.R;
import com.severinghams.homebrewsolitaire.core.enums.EnumColour;
import com.severinghams.homebrewsolitaire.core.enums.EnumRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumSuit;

import java.util.ResourceBundle;

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

    public void drawCard(Canvas canvas) {
        int[] pos = {horizontalPos,verticalPos};
        int[] canSize = {canvas.getWidth()/21, canvas.getHeight()/17/3, (canvas.getWidth()/70), canvas.getHeight()/17};
        //System.out.println(horizontalPos+" "+verticalPos);
        Rect rectangle = new Rect(
                pos[0]*canSize[0]+canSize[2],
                pos[1]*canSize[1]+canSize[2],
                (pos[0]+3)*canSize[0]-canSize[2],
                (pos[1]+3)*canSize[1]+canSize[3]+canSize[2]);

        if (isFaceDown) {
            drawableBack.setBounds(rectangle);
            drawableBack.draw(canvas);
        } else {
            drawable.setBounds(rectangle);
            drawable.draw(canvas);
        }
    }

}
