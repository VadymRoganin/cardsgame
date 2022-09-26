package com.cardsgame.deck;

import com.cardsgame.card.Card;
import com.cardsgame.card.enums.Rank;
import com.cardsgame.card.enums.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicCardDeckTest {

    @Test
    public void shouldDealCard() {
        CardDeck deck = BasicCardDeck.builder().build();
        var initialSize = deck.size();

        Optional<Card> optionalCard = deck.dealCard();

        assertEquals(--initialSize, deck.size());
        assertTrue(optionalCard.isPresent());
        assertFalse(optionalCard.get().isFolded());
    }

    @Test
    public void shouldDealCards() {
        CardDeck deck = BasicCardDeck.builder().build();
        var initialSize = deck.size();
        var cardsAmount = 5;
        assertTrue(initialSize > 5);

        List<Card> res = deck.dealCards(cardsAmount);

        assertEquals(initialSize - cardsAmount, deck.size());
        assertEquals(cardsAmount, res.size());
        res.forEach(c -> assertFalse(c.isFolded()));
    }

    @Test
    public void shouldShuffle() {
        BasicCardDeck deck = BasicCardDeck.builder()
                .build();
        List<Card> cards = deck.dealCards(deck.size());
        IntStream.range(0, 100).forEach(i -> {
            BasicCardDeck deck1 = BasicCardDeck.builder()
                    .build();
            assertEquals(cards, deck1.dealCards(deck1.size()));
        });
        var res = IntStream.range(0, 100).mapToObj(i -> {
            BasicCardDeck deck1 = BasicCardDeck.builder()
                    .build();
            deck1.shuffle();
            return cards.equals(deck1.dealCards(deck1.size()));
        })
                .allMatch(b -> b);
        assertFalse(res);
    }

    @Test
    public void shouldFold() {
        BasicCardDeck deck = BasicCardDeck.builder()
                .withCardPredicate(c -> c.getRank() == Rank.ACE && c.getSuit() == Suit.HEARTS)
                .build();
        Card card = deck.dealCard().orElseThrow();
        assertFalse(card.isFolded());

        deck.fold(card);
        assertTrue(card.isFolded());
    }

    @Test
    public void shouldFoldAllCards() {
        BasicCardDeck deck = BasicCardDeck.builder()
                .withCardPredicate(c -> c.getRank() == Rank.ACE && c.getSuit() == Suit.HEARTS)
                .build();
        List<Card> cards = deck.dealCards(5);
        assertTrue(cards.stream().noneMatch(Card::isFolded));

        deck.foldAll(cards);
        assertTrue(cards.stream().allMatch(Card::isFolded));
    }
}
