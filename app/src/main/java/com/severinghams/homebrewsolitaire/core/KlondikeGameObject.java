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
    public CardStackObject stock = new CardStackObject(0,1, EnumStackType.StockStack, false);
    public CardStackObject waste = new CardStackObject(1,1, EnumStackType.SpreadStackH, EnumRank.RankA, EnumStackingRank.AceToKing, EnumStackingSuit.SameSuit, false, false, false, true);


    public KlondikeGameObject(long seed, Context context) {
        super(seed, context);
        //create tableau
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = new CardStackObject(i, 1, EnumStackType.SpreadStackV, EnumRank.RankK, EnumStackingRank.KingToAce, EnumStackingSuit.AltColours, false, true, true, true);
            System.out.println(tableau[i].getPos()[0]+" "+tableau[i].getPos()[1]);
        }
        //create foundation
        for (int i = 0; i < 4; i++) {
            foundation[i] = new CardStackObject(i+3, 0, EnumStackType.SpreadStackV, EnumRank.RankA, EnumStackingRank.AceToKing, EnumStackingSuit.SameSuit, false, true, true, true);
        }
        //add cards in triangle/staircase pattern
        for (int j = 0; j < 7; j++) {
            for (int i = 6; i >= j; i--) {
                //System.out.print(i+" ");
                tableau[i].setupAddCard(dealer.get(0));
                tableau[i].getTopCard().isFaceDown=true;
                for (int k = 0; k < tableau.length; k++) {
                    System.out.print(tableau[k].getCardStackList().size()+" ");
                }
                System.out.println();
                dealer.remove(0);
            }

        }
        // add to stock
        for (int i = 0; i < dealer.size(); i++) {
            stock.setupAddCard(dealer.get(0));
            dealer.remove(0);
        }
    }

    private void drawTableau(Canvas canvas) {
        for (int i = 0; i < tableau.length; i++) {
            tableau[i].updateStack();
            tableau[i].drawStack(canvas);
        }
    }
    private void drawFoundation(Canvas canvas) {
        for (int i = 0; i < foundation.length; i++) {
            foundation[i].updateStack();
            foundation[i].drawStack(canvas);
        }
    }
    private void drawStockAndWaste(Canvas canvas) {
        stock.updateStack();
        waste.updateStack();
    }
    public void drawGame(Canvas canvas) {
        //drawFoundation(canvas);
        drawTableau(canvas);
        //drawStockAndWaste(canvas);
    }
}
