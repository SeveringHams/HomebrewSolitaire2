package com.severinghams.homebrewsolitaire.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import com.severinghams.homebrewsolitaire.R;
import com.severinghams.homebrewsolitaire.core.enums.*;

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
    public final ArrayList<CardObject> dealer;
    public ArrayList<CardObject> hand = new ArrayList<>(1);
    private boolean ignoreRules = false;
    public int[] handPosition = {0,0};
    public BaseSingleDeckGameObject(long seed, Context context) {
        this.dealer = randomizer(seed, context);
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
        hand = new ArrayList<>(1);
        return toStack;
    }
    public void drawGame(Canvas canvas) {

    }
}