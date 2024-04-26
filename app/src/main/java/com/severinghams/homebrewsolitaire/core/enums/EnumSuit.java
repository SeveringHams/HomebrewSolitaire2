package com.severinghams.homebrewsolitaire.core.enums;

import static com.severinghams.homebrewsolitaire.core.enums.EnumColour.*;

public enum EnumSuit {
    Spades(Black,0),
    Clubs(Black,1),
    Diamonds(Red,2),
    Hearts(Red,3);
    public final EnumColour colour;
    public final int value;
    EnumSuit(EnumColour colour, int value) {
       this.colour = colour;
       this.value = value;
    }
}


