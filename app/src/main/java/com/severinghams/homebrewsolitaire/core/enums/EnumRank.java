package com.severinghams.homebrewsolitaire.core.enums;

public enum EnumRank {
    RankA(1, "A"),
    Rank2(2, "2"),
    Rank3(3, "3"),
    Rank4(4, "4"),
    Rank5(5, "5"),
    Rank6(6, "6"),
    Rank7(7, "7"),
    Rank8(8, "8"),
    Rank9(9, "9"),
    Rank10(10, "10"),
    RankJ(11, "J"),
    RankQ(12, "Q"),
    RankK(13, "K");

    public final int value;
    public final String display;
    private static final EnumRank[] ranks = EnumRank.values();

    EnumRank(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public EnumRank getRank(int rankNumber) {
        if (1 > rankNumber || rankNumber > 13) {
            System.err.println("ERROR: " + rankNumber + " is outside the range of 1-13. Fix your code!");
            return null;
        }
        return ranks[rankNumber + 1];
    }
}