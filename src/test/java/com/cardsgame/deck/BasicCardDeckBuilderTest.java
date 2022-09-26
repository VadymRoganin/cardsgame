package com.cardsgame.deck;

import com.cardsgame.card.enums.Rank;
import com.cardsgame.card.enums.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicCardDeckBuilderTest {

    @Test
    public void shouldBuildDeck() {
        var deck = BasicCardDeck.builder()
                .build();
        assertEquals(52, deck.size());
        assertEquals(0, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithJokersAmount() {
        var deck = BasicCardDeck.builder()
                .withJokers(2)
                .build();
        assertEquals(54, deck.size());
        assertEquals(2, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithJokersAmountAndNumberOfDecks() {
        var deck = BasicCardDeck.builder()
                .withJokers(2)
                .build();
        assertEquals(54, deck.size());
        assertEquals(2, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithJokersAmountAndStartingRank() {
        var deck = BasicCardDeck.builder()
                .withStartingRank(Rank.SIX)
                .build();
        assertEquals(36, deck.size());
        assertEquals(0, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithStartingRank() {
        var deck = BasicCardDeck.builder()
                .withStartingRank(Rank.SIX)
                .build();
        assertEquals(36, deck.size());
        assertEquals(0, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithStartingRankAndJokersAmount() {
        var deck = BasicCardDeck.builder()
                .withStartingRank(Rank.SIX)
                .withJokers(0)
                .build();
        assertEquals(36, deck.size());
        assertEquals(0, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithCardsPredicate() {
        var deck = BasicCardDeck.builder()
                .withCardPredicate(c -> c.getRank() == Rank.ACE
                        && (c.getSuit() == Suit.HEARTS || c.getSuit() == Suit.CLUBS))
                .build();
        assertEquals(2, deck.size());
        assertEquals(0, countJokers(deck));
    }

    @Test
    public void shouldBuildDeckWithCardsPredicateAndJokersAmount() {
        var deck = BasicCardDeck.builder()
                .withCardPredicate(c -> c.getRank() == Rank.ACE
                        && (c.getSuit() == Suit.HEARTS || c.getSuit() == Suit.CLUBS))
                .withJokers(0)
                .build();
        assertEquals(2, deck.size());
        assertEquals(0, countJokers(deck));
    }


    @Test
    public void shouldAddCardDeck() {
        var deck1 = BasicCardDeck.builder()
                .build();
        var deck2 = BasicCardDeck.builder()
                .withCardDeck(deck1)
                .build();

        assertEquals(104, deck2.size());
    }

    @Test
    public void shouldThrowExceptionWhenWithEmptyCardDeck() {
        var deck1 = BasicCardDeck.builder()
                .withCardPredicate(c -> c.getRank() == Rank.ACE && c.getSuit() == Suit.HEARTS)
                .build();

        deck1.dealCard();

        assertThrows(IllegalArgumentException.class, () -> BasicCardDeck.builder()
                .withCardDeck(deck1)
                .build());
    }

    private int countJokers(CardDeck deck) {
        int counter = 0;
        while (true) {
            var cardOptional = deck.dealCard();

            if (cardOptional.isEmpty()) {
                break;
            }
            if (cardOptional.get().getRank() == Rank.JOKER) {
                counter++;
            }
        }
        return counter;
    }
}
