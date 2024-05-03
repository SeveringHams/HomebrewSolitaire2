package com.severinghams.homebrewsolitaire.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.MotionEvent;

import androidx.core.view.ActionProvider;

import com.severinghams.homebrewsolitaire.R;
import com.severinghams.homebrewsolitaire.core.enums.*;

import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BaseSingleDeckGameObject {
    public long gameSeed = 0;
    public final int[][] cardFaces =
            {
                    {R.drawable.card_s1,R.drawable.card_s2,R.drawable.card_s3,R.drawable.card_s4,R.drawable.card_s5,R.drawable.card_s6,R.drawable.card_s7,R.drawable.card_s8,R.drawable.card_s9,R.drawable.card_s10,R.drawable.card_s11,R.drawable.card_s12,R.drawable.card_s13},
                    {R.drawable.card_c1,R.drawable.card_c2,R.drawable.card_c3,R.drawable.card_c4,R.drawable.card_c5,R.drawable.card_c6,R.drawable.card_c7,R.drawable.card_c8,R.drawable.card_c9,R.drawable.card_c10,R.drawable.card_c11,R.drawable.card_c12,R.drawable.card_c13},
                    {R.drawable.card_d1,R.drawable.card_d2,R.drawable.card_d3,R.drawable.card_d4,R.drawable.card_d5,R.drawable.card_d6,R.drawable.card_d7,R.drawable.card_d8,R.drawable.card_d9,R.drawable.card_d10,R.drawable.card_d11,R.drawable.card_d12,R.drawable.card_d13},
                    {R.drawable.card_h1,R.drawable.card_h2,R.drawable.card_h3,R.drawable.card_h4,R.drawable.card_h5,R.drawable.card_h6,R.drawable.card_h7,R.drawable.card_h8,R.drawable.card_h9,R.drawable.card_h10,R.drawable.card_h11,R.drawable.card_h12,R.drawable.card_h13}
            };
    public final int cardBack = R.drawable.card_back;
    public final int emptySpot = R.drawable.empty_stack;
    public static final int background = R.drawable.background0;
    public int canvasWidth;
    public int canvasHeight;
    public double cardWidth;
    public double cardHeight;
    public double cardPositionOffset;
    public double cardStackOffset;
    public double margin;
    public double fCardHeight;
    public final ArrayList<CardObject> dealer;
    public final ArrayList<CardObject> hand = new ArrayList<>(1);
    public final Rect cardTemp = new Rect();
    private boolean ignoreRules = false;
    public int[] handPosition = {0,0};
    public final Drawable emptySpotDrawable;
    public final Drawable backgroundDrawable;
    public float cursorX = 100;
    public float cursorY = 500;
    public Paint paint = new Paint();


    @SuppressLint("UseCompatLoadingForDrawables")
    public BaseSingleDeckGameObject(long seed, Context context) {
        this.dealer = randomizer(seed, context);
        paint.setARGB(255,255,255,255);
        emptySpotDrawable = context.getDrawable(emptySpot);
        backgroundDrawable = context.getDrawable(background);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public ArrayList<CardObject> randomizer(long seed, Context context) {
        EnumRank[] arrayRank = {EnumRank.RankA,EnumRank.Rank2,EnumRank.Rank3,EnumRank.Rank4,EnumRank.Rank5,EnumRank.Rank6,EnumRank.Rank7,EnumRank.Rank8,EnumRank.Rank9,EnumRank.Rank10,EnumRank.RankJ,EnumRank.RankQ,EnumRank.RankK};
        EnumSuit[] arraySuit = {EnumSuit.Spades,EnumSuit.Clubs,EnumSuit.Hearts,EnumSuit.Diamonds};
        ArrayList<CardObject> cards = new ArrayList<>(52);
        for (int i = 0; i < 52; i++) {
            //long constructor so I cut it up a bit.
            cards.add
            (
                    new CardObject
                    (
                        arrayRank[i%13],
                        arraySuit[i/13],
                        context.getResources().getDrawable(cardFaces[i/13][(i%13)]),
                        context.getResources().getDrawable(cardBack)
                )
            );
        }
        if (seed == 0) {
            Random rand = new Random();
            this.gameSeed = rand.nextLong();
            Collections.shuffle(cards, new Random(this.gameSeed));
        } else {
            this.gameSeed = seed;
            Collections.shuffle(cards, new Random(this.gameSeed));
        }
        Collections.shuffle(cards,new Random(seed));
        return cards;
    }

    public ArrayList<CardObject> setFromHand() {
        ArrayList<CardObject> toStack = hand;
        hand.clear();
        return toStack;
    }
    public void doTouchEvent(MotionEvent motion) {
        String action = "";
        switch (motion.getAction()) {
            case 0:
                action = "ACTION_DOWN";
                break;
            case 1:
                action = "ACTION_UP";
                break;
            case 2:
                action = "ACTION_MOVE";
                break;
            default:
                action = Integer.toString(motion.getAction());
                break;
        }
        System.out.format("%s%4d%s%4d%s%13s%s%n",
                "| X = ",
                (int)Math.floor(motion.getX()*7.0/canvasWidth),
                " | Y = ",
                (int)Math.floor(motion.getY()*9.0/canvasWidth),
                " | ACTION = ",
                action,
                " |");
        cursorX = motion.getX();
        cursorY = motion.getY();
    }
    public void drawGame(Canvas canvas) {
        backgroundDrawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        backgroundDrawable.draw(canvas);
        System.out.println(cursorX+ "  " +cursorY);
        canvas.drawRect(cursorX-10,cursorY-10,cursorX+10,cursorY+10, paint);
    }

    public void updateGame() {

    }

    public void onSetSize(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
        cardWidth = canvasWidth/7.0-canvasWidth/90.0;
        cardHeight = cardWidth*1.44;
        margin = canvasWidth/(90.0*2);
        cardPositionOffset = canvasWidth/7.0;
        cardStackOffset = cardWidth*0.44;
        fCardHeight = cardHeight + margin*2;
        cardTemp.set(
                (int)(margin),
                (int)(margin),
                (int)(margin + cardWidth),
                (int)(margin + cardHeight)
        );
    }
}