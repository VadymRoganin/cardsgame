package com.cardsgame.deck;

import com.cardsgame.card.Card;

import java.util.List;
import java.util.Optional;

/**
 * Card Deck interface
 */
public interface CardDeck {

    /**
     * Deals one card. Returns optional containing the card if the deck is not empty, or emoty oprional otherwise
     *
     * @return Optional containing the card
     */
    Optional<Card> dealCard();

    /**
     * Deals specified amount of cards or maximum available amount of cards in the deck.
     * Returns empty list if the deck is empty.
     *
     * @throws IllegalArgumentException if specified amount is less than 1
     * @return Cards list
     */
    List<Card> dealCards(int amount);

    /**
     * Folds card created by this card deck. If the card is already folded, nothing happens.
     *
     * @throws NullPointerException if provided card is null
     * @param card A card
     */
    void fold(Card card);

    /**
     * Folds a list of cards. Folds cards created by this card deck. If the card is already folded, nothing happens.
     *
     * @throws NullPointerException if provided cards is null
     * @param cards A list of cards to fold
     */
    void foldAll(List<Card> cards);

    /**
     * Shuffles the deck
     */
    void shuffle();

    /**
     * Returns the size of the deck
     *
     * @return The size of the deck
     */
    int size();
}
