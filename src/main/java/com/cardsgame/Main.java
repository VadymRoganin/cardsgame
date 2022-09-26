package com.cardsgame;

import com.cardsgame.deck.BasicCardDeck;

public class Main {

    public static void main(String[] args) {
        var deck = BasicCardDeck.builder()
                .withJokers(2)
                .build();

        System.out.println("Random card: " + deck.dealCard());
    }
}
