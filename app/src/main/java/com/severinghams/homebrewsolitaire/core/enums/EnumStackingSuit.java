package com.severinghams.homebrewsolitaire.core.enums;

public enum EnumStackingSuit {
    AltColours,
    SameColours,
    SameSuit,
    AnySuit;
    public boolean isValid(EnumSuit cardBot, EnumSuit cardTop) {
        if (this.equals(AnySuit)) {
            return true;
        }
        if (this.equals(SameSuit)) {
            return cardBot.equals(cardTop);
        }
        if (this.equals(SameColours)) {
            return cardBot.colour.equals(cardTop.colour);
        }
        if (this.equals(AltColours)) {
            return !cardBot.colour.equals(cardTop.colour);
        }
        System.err.println("ERROR: Method isValid(EnumSuit, EnumSuit) in class EnumStackingSuit has\r\n" +
                           "defaulted to return false! Check your code!");
        return false;
    }

}
