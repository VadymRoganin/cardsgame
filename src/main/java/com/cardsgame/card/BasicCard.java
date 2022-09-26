package com.cardsgame.card;

import com.cardsgame.card.enums.Rank;
import com.cardsgame.card.enums.Suit;
import com.cardsgame.deck.CardDeck;

import java.util.Objects;

/**
 * Basic card implementation
 */
public class BasicCard implements Card, Comparable<BasicCard> {

    private final Rank rank;
    private final Suit suit;
    private final CardDeck cardDeck;
    private boolean folded;

    public BasicCard(Rank rank, Suit suit, CardDeck cardDeck) {
        Objects.requireNonNull(rank, "Rank cannot be null");
        Objects.requireNonNull(suit, "Suit cannot be null");
        Objects.requireNonNull(cardDeck, "Card deck cannot be null");
        this.rank = rank;
        this.suit = suit;
        this.cardDeck = cardDeck;
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public void fold(CardDeck cardDeck) {
        Objects.requireNonNull(cardDeck, "Card deck cannot be null");
        if (!this.cardDeck.equals(cardDeck)) {
            throw new IllegalArgumentException("Invalid CardDeck instance supplied. " +
                    "You need to supply the original CardDeck instance which produced the card");
        }
        this.folded = true;
    }

    @Override
    public boolean isFolded() {
        return this.folded;
    }

    @Override
    public CardDeck getCardDeck() {
        return cardDeck;
    }

    @Override
    public int compareTo(BasicCard basicCard) {
        return this.getRank().compareTo(basicCard.getRank());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicCard basicCard = (BasicCard) o;
        return rank == basicCard.rank && suit == basicCard.suit && folded == basicCard.folded;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit, folded);
    }

    @Override
    public String toString() {
        return "BasicCard{" +
                "rank=" + rank +
                ", suit=" + suit +
                ", folded=" + folded +
                '}';
    }
}
