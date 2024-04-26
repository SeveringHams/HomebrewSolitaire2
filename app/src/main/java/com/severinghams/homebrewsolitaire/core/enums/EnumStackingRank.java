package com.severinghams.homebrewsolitaire.core.enums;

public enum EnumStackingRank {
    AceToKing,
    KingToAce,
    BothWays;

    public boolean isValid(EnumRank cardBot, EnumRank cardTop, boolean rankRollover) {
        //ascending
        if (this.equals(AceToKing)) {
            if (cardBot.value == 13) {
                return rankRollover && cardTop.value == 1;
            }
            return cardTop.value == cardBot.value + 1;
        }
        //descending
        if (this.equals(KingToAce)) {
            if (cardBot.value == 1) {
                return rankRollover && cardTop.value == 13;
            }
            return cardTop.value == cardBot.value - 1;
        }
        //both ways
        if (this.equals(BothWays)) {
            if (cardBot.value == 1 && cardTop.value == 13) {
                return rankRollover;
            }
            if (cardBot.value == 13 && cardTop.value == 1) {
                return rankRollover;
            }
            return (cardTop.value == cardBot.value + 1) || (cardTop.value == cardBot.value-1);
        }
        //defaulting case
        System.err.println("ERROR: Method isValid(EnumRank, EnumRank, boolean) in enum class EnumStackingRank\r\n" +
                           "has defaulted to return false! Check your code!");
        return false;
    }
}
