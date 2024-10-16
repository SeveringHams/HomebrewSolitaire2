package com.severinghams.homebrewsolitaire.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import com.severinghams.homebrewsolitaire.core.enums.EnumRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackType;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackingRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackingSuit;

public class KlondikeGameObject extends BaseSingleDeckGameObject {
    public CardStackObject[] tableau = new CardStackObject[7];
    public CardStackObject[] foundation = new CardStackObject[4];
    public CardStackObject stock = new CardStackObject(
            0,
            0,
            EnumStackType.StockStack,
            false,
            this.emptySpotDrawable
    );
    public CardStackObject waste = new CardStackObject(
            1,
            0,
            EnumStackType.SpreadStackH,
            EnumRank.RankA,
            EnumStackingRank.AceToKing,
            EnumStackingSuit.SameSuit,
            false,
            false,
            false,
            true,
            this.emptySpotDrawable);


    public KlondikeGameObject(long seed, Context context) {
        super(seed, context);
        //create tableau
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = new CardStackObject(
                    i+0,
                    1,
                    EnumStackType.SpreadStackV,
                    EnumRank.RankK,
                    EnumStackingRank.KingToAce,
                    EnumStackingSuit.AltColours,
                    false,
                    true,
                    true,
                    true,
                    this.emptySpotDrawable);
        }
        //create foundation
        for (int i = 0; i < 4; i++) {
            foundation[i] = new CardStackObject(
                    i+3,
                    0,
                    EnumStackType.StraightStack,
                    EnumRank.RankA,
                    EnumStackingRank.AceToKing,
                    EnumStackingSuit.SameSuit,
                    false,
                    true,
                    true,
                    true,
                    this.emptySpotDrawable);
        }
        //add cards in triangle/staircase pattern
        for (int j = 0; j < 7; j++) {
            for (int i = 6; i >= j; i--) {
                tableau[i].setupAddCard(dealer.get(0));
                tableau[i].getTopCard().isFaceDown=true;
                dealer.remove(0);
            }

        }
        // add to stock
        for (int i = 0; i < dealer.size(); i++) {
            stock.setupAddCard(dealer.get(0));
            stock.cardStackList.get(i).isFaceDown = true;
            dealer.remove(0);
        }
    }

    private void drawTableau(Canvas canvas) {
        for (CardStackObject cardStackObject : tableau) {
            cardStackObject.drawStackTop(canvas, cardTemp, cardPositionOffset, cardStackOffset, fCardHeight);
        }
    }
    private void drawFoundation(Canvas canvas) {
        for (CardStackObject cardStackObject : foundation) {
            cardStackObject.drawStackTop(canvas, cardTemp, cardPositionOffset, cardStackOffset, fCardHeight);
        }
    }
    private void drawStockAndWaste(Canvas canvas) {
        stock.drawStackTop(canvas, cardTemp, cardPositionOffset, cardStackOffset, fCardHeight);
        waste.drawStackTop(canvas, cardTemp, cardPositionOffset, cardStackOffset, fCardHeight);
    }
    @Override
    public void drawGame(Canvas canvas) {
        super.drawGame(canvas);
        drawFoundation(canvas);
        drawTableau(canvas);
        drawStockAndWaste(canvas);
    }

    @Override
    public void updateGame() {
        super.updateGame();
        for (int i = 0; i < tableau.length; i++) {
            tableau[i].updateStack();
        }
        for (int i = 0; i < foundation.length; i++) {
            foundation[i].updateStack();
        }
        stock.updateStack();
        waste.updateStack();
    }
}
