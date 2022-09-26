package com.cardsgame.card.enums;

/**
 * Cards suits enum
 */
public enum Suit {

    DIAMONDS("♢"), CLUBS("♧"), HEARTS("♥"), SPADES("♤"), JOKER("*");

    private final String name;

    Suit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
