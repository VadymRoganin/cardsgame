package com.cardsgame.card;

import com.cardsgame.card.enums.Rank;
import com.cardsgame.card.enums.Suit;
import com.cardsgame.deck.CardDeck;

/**
 * Card interface
 */
public interface Card {
    /**
     * Returns the rank of the card. Returns null for jokers
     *
     * @return The rank of the card
     */
    Rank getRank();

    /**
     * Returns the suit of the card
     *
     * @return The suit of the card
     */
    Suit getSuit();

    /**
     * Folds card. This operation is irreversible. If the card already folded, nothing happens.
     *
     * @param cardDeck A card deck which produced the card.
     * @throws IllegalArgumentException if invalid deck is supplied
     */
    void fold(CardDeck cardDeck);

    /**
     * Checks if the card is already folded
     *
     * @return True if card is already folded, false otherwise
     */
    boolean isFolded();

    /**
     * Gets card deck which created this card.
     *
     * @return A card deck which created this card
     */
    CardDeck getCardDeck();
}
