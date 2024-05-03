package com.severinghams.homebrewsolitaire.core;


import android.graphics.Bitmap;
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
    private final Rect rect = new Rect();
    public final Drawable drawable;
    public final Drawable drawableBack;
    public CardObject (EnumRank rank, EnumSuit suit, Drawable drawFace, Drawable drawBack) {
        this.rank = rank;
        this.suit = suit;
        this.colour = this.suit.colour;
        drawable = drawFace;
        drawableBack = drawBack;
    }
    public void drawCardTop(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        rect.set(cardTemp);
        rect.offset((int)(offsetH*(double)horizontalPos),(int)(offsetV*(double)verticalPos));
        if (isFaceDown) {
            drawableBack.setBounds(rect);
            drawableBack.draw(canvas);
        } else {
            drawable.setBounds(rect);
            drawable.draw(canvas);
        }
    }


    public void drawCardBottom(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        if (isFaceDown) {
            drawableBack.setBounds(cardTemp);
            drawableBack.draw(canvas);
        } else {
            drawable.setBounds(cardTemp);
            drawable.draw(canvas);
        }
    }

}
