package com.severinghams.homebrewsolitaire.core;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.severinghams.homebrewsolitaire.core.enums.EnumRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackType;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackingRank;
import com.severinghams.homebrewsolitaire.core.enums.EnumStackingSuit;

import java.util.ArrayList;


public class CardStackObject {
    public final boolean singleCardOnly;
    public final boolean canFillEmpty;
    public final boolean canStack;
    public final boolean canRemoveFromStack;
    public final EnumRank baseRank;
    public final EnumStackingRank rankStacking;
    public final EnumStackType stackType;
    public final boolean rankRollover;
    private static boolean ignoreRules = false;
    public final EnumStackingSuit suitStacking;
    public final ArrayList<CardObject> cardStackList = new ArrayList<>(1);
    private final int positionH;
    private final int positionV;
    private int emptyPosH;
    private int emptyPosV;
    private final Rect rect = new Rect();
    private final Drawable emptyStack;

    // specify position, base rank, rank pattern, suit stacking, and rank rollover. use this to create a tableau stack, waste stack, or foundation stack.
    public CardStackObject (int posH, int posV, EnumStackType stackType, EnumRank baseRank, EnumStackingRank rankStacking, EnumStackingSuit suitStacking, boolean rankRollover, boolean canFillEmpty, boolean canStack, boolean canRemoveFromStack, Drawable emptyStack) {
        //Position
        positionH = posH;
        positionV = posV;
        //Base rank
        this.baseRank = baseRank;
        //Stacking Pattern Rules
        this.rankStacking = rankStacking;
        this.suitStacking = suitStacking;
        this.stackType = stackType;
        this.rankRollover = rankRollover;
        //can place cards on empty stack
        this.canFillEmpty = canFillEmpty;
        this.canStack = canStack;
        this.singleCardOnly = false;
        this.canRemoveFromStack = canRemoveFromStack;
        this.emptyStack = emptyStack;
        init();
        updateStack();
    }
    // use for stock
    public CardStackObject (int posH, int posV, EnumStackType stackType, boolean canRemoveFromStack, Drawable emptyStack) {
        //Position
        positionH = posH;
        positionV = posV;
        //Base rank
        this.baseRank = null;
        //Stacking Pattern Rules
        this.rankStacking = null;
        this.suitStacking = null;
        this.stackType = stackType;
        this.rankRollover = false;
        //can place cards on empty stack
        this.canFillEmpty = false;
        this.canStack = false;
        this.singleCardOnly = false;
        this.canRemoveFromStack = canRemoveFromStack;
        this.emptyStack = emptyStack;
        init();
        updateStack();
    }
    // specify position. use for reserve slots.
    public CardStackObject (int posH, int posV, Drawable emptyStack) {
        positionH = posH;
        positionV = posV;
        this.baseRank = null;
        //Stacking Pattern Rules
        this.rankStacking = null;
        this.suitStacking = null;
        this.stackType = EnumStackType.StraightStack;
        this.rankRollover = false;
        //can place cards on empty stack
        this.canFillEmpty = true;
        this.canStack = true;
        this.singleCardOnly = true;
        this.canRemoveFromStack = true;
        this.emptyStack = emptyStack;
        init();
        updateStack();
    }
    private void init() {
        switch (stackType) {
            case StockStack:
            case SpreadStackH:
            case StraightStack:
                emptyPosH = positionH*3;
                emptyPosV = positionV*3;
                break;
            case SpreadStackV:
                emptyPosH = positionH*3;
                emptyPosV = positionV*12;
                break;
            default:
                System.err.println("ERROR: init() has defaulted! You so stupid!");
                break;
        }
    }
    public CardObject getTopCard() {
        if (cardStackList.isEmpty()) {
            return null;
        }
        return cardStackList.get(cardStackList.size()-1);
    }
    public ArrayList<CardObject> getCardStackList() {
        return cardStackList;
    }
    public int[] getPos() {
        return new int[]{positionH, positionV};
    }
    public boolean canPlaceOnTop(CardObject card) {
        //if stack allows additional cards
        if (ignoreRules) {
            return true;
        }
        if (!canStack) {
            return false;
        }
        //if stack is empty
        if (cardStackList.isEmpty()) {
            //if empty stack can be filled
            if (canFillEmpty) {
                //if req. base rank is any rank
                if (baseRank == null) {
                    return true;
                }
                //if the current card equals the required rank
                return card.rank.equals(baseRank);
            } else {
                return false;
            }
            //if the stack allows additional cards and the stack is not empty
        } else {
            if (this.singleCardOnly) {
                return false;
            } else {
                if (this.rankStacking.isValid(getTopCard().rank,card.rank,rankRollover)&&this.suitStacking.isValid(getTopCard().suit,card.suit)) {
                    return true;
                }
            }
        }
        System.err.println("ERROR: Method canPlaceOnTop(CardObject) in class CardStackObject has\r\n" +
                "defaulted to return false! Check your code!");
        return false;
    }
    public boolean placeOnTop(ArrayList<CardObject> hand) {
        if (canPlaceOnTop(hand.get(0))) {
            cardStackList.addAll(hand);
            updateStack();
            return true;
        }
        return false;
    }
    public boolean canGrabStack(int stackIndex) {
        int i = stackIndex;
        boolean canGrab = true;
        if (!stackType.equals(EnumStackType.SpreadStackV)) {
            return stackIndex == cardStackList.size() - 1;
        }
        if (ignoreRules) {
            return true;
        }
        if (cardStackList.get(stackIndex).isFaceDown) {
            return false;
        }
        while (i + 1 < cardStackList.size()) {
            if (!(this.rankStacking.isValid(cardStackList.get(i).rank,cardStackList.get(i+1).rank,rankRollover)&&this.suitStacking.isValid(cardStackList.get(i).suit,cardStackList.get(i+1).suit))) {
                canGrab = false;
            }
            i++;
        }
        return canGrab;
    }
    public ArrayList<CardObject> grabStack(int stackIndex) {
        ArrayList<CardObject> toHand = new ArrayList<>(1);
        if (canGrabStack(stackIndex)) {
            for (int i = stackIndex; i < cardStackList.size(); i++) {
                toHand.add(cardStackList.get(stackIndex));
                cardStackList.remove(stackIndex);
            }
            updateStack();
            return toHand;
        }
        return null;
    }
    public void updateStack() {
        if (getTopCard()==null) {
            return;
        }
        if (!stackType.equals(EnumStackType.StockStack)) {
            getTopCard().isFaceDown = false;
        }
        if (stackType.equals(EnumStackType.StockStack)) {
            for (int i = 0; i < cardStackList.size(); i++) {
                cardStackList.get(i).isFaceDown = true;
            }
        }
        switch (stackType) {
            case StockStack:
            case StraightStack:
                for (int i = 0; i < cardStackList.size(); i++) {
                    cardStackList.get(i).verticalPos = 0;
                    cardStackList.get(i).horizontalPos = 0;
                }
                break;
            case SpreadStackV:
                for (int i = 0; i < cardStackList.size(); i++) {
                    cardStackList.get(i).verticalPos = i;
                    cardStackList.get(i).horizontalPos = 0;
                }
                break;
            case SpreadStackH:
                for (int i = 0; i < 3; i++) {
                    if (cardStackList.size() < i+1) break;
                    cardStackList.get(cardStackList.size()-i-1).horizontalPos = i;
                    cardStackList.get(cardStackList.size()-i-1).verticalPos = 0;
                }
                break;
            default:
                System.err.println("ERROR: updateStack() has defaulted! You so stupid!");
                break;
        }

    }

    public void drawStackTop(Canvas canvas, Rect cardTemp, double offsetH, double offsetV, double fCardHeight) {
        rect.set(cardTemp);
        rect.offset((int)(offsetH*(double)positionH),(int)(fCardHeight*(double)positionV));
        if (getTopCard() == null) {
            if (canFillEmpty) {
                drawEmptyTop(canvas, rect, offsetH, offsetV);
            }
            return;
        }
        switch (stackType) {
            case StockStack:
            case StraightStack:
                this.getTopCard().drawCardTop(canvas, rect, offsetH, offsetV);
                break;
            case SpreadStackV:
                drawStackVTop(canvas, rect, offsetH, offsetV);
                break;
            case SpreadStackH:
                drawStackHTop(canvas, rect, offsetH, offsetV);
                break;
        }
    }

    private void drawStackVTop(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        for (int i = 0; i < cardStackList.size(); i++) {
            cardStackList.get(i).drawCardTop(canvas, cardTemp, offsetH, offsetV);
        }
    }

    private void drawStackHTop(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        for (int i = 0; i < cardStackList.size(); i++) {
            cardStackList.get(cardStackList.size()-i+1).drawCardTop(canvas, cardTemp, offsetH, offsetV);
        }
    }

    public void drawEmptyTop(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        Rect rect = new Rect(cardTemp);
        rect.offset((int)(offsetH*(double)emptyPosH),(int)(offsetV*(double)emptyPosV));
        emptyStack.setBounds(cardTemp);
        emptyStack.draw(canvas);
    }

    public void drawStackBottom(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        if (getTopCard() == null) {
            if (canFillEmpty) {
                drawEmptyTop(canvas, cardTemp, offsetH, offsetV);
            }
            return;
        }
        switch (stackType) {
            case StockStack:
            case StraightStack:
                this.getTopCard().drawCardBottom(canvas, cardTemp, offsetH, offsetV);
                break;
            case SpreadStackV:
                drawStackVBottom(canvas, cardTemp, offsetH, offsetV);
                break;
            case SpreadStackH:
                drawStackHBottom(canvas, cardTemp, offsetH, offsetV);
                break;
        }
    }

    private void drawStackVBottom(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        for (int i = 0; i < cardStackList.size(); i++) {
            cardStackList.get(i).drawCardBottom(canvas, cardTemp, offsetH, offsetV);
        }
    }
    private void drawStackHBottom(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        for (int i = 0; i < 3; i++) {
            cardStackList.get(cardStackList.size()-i+1).drawCardBottom(canvas, cardTemp, offsetH, offsetV);
        }
    }

    public void drawEmptyBottom(Canvas canvas, Rect cardTemp, double offsetH, double offsetV) {
        cardTemp.offset((int)(offsetH*(double)emptyPosH),(int)(offsetV*(double)emptyPosV));
        emptyStack.setBounds(cardTemp);
        emptyStack.draw(canvas);
    }

    public static void setIgnoreRules(boolean ignoreRule) {
        ignoreRules = ignoreRule;
    }
    public void setupAddCard(CardObject card) {
        cardStackList.add(card);
    }
}
