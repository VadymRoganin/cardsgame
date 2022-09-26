package com.cardsgame;

import com.cardsgame.deck.BasicCardDeck;

import static com.cardsgame.card.enums.Rank.ACE;

public class Main {

//    Test task
//
//    Description:
//    You need to build several classes/interfaces for describing a behavior of deck of cards.
//    Basic methods to be implemented:
//
//             shuffle
// deal cards
// fold
// some other you consider it necessary
//
//    You don’t need to implement particular card game, this should be a base, foundation for
//    further developing.

    public static void main(String[] args) {
        var deck1 = BasicCardDeck.builder().withStartingRank(ACE).withJokers(2).build();
        var deck2 = BasicCardDeck.builder().withStartingRank(ACE).withJokers(2).build();
        System.out.println(deck1);
    }
}
