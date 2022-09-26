package com.cardsgame.deck;

import com.cardsgame.card.BasicCard;
import com.cardsgame.card.Card;
import com.cardsgame.card.enums.Rank;
import com.cardsgame.card.enums.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.cardsgame.config.Constants.MINIMUM_JOKERS_AMOUNT;
import static com.cardsgame.config.Constants.MAXIMUM_JOKERS_AMOUNT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Basic cards deck
 */
public class BasicCardDeck implements CardDeck {

    private final LinkedList<Card> cards = new LinkedList<>();

    private final Predicate<Card> cardPredicate;

    private final Integer jokersAmount;

    private BasicCardDeck(Predicate<Card> cardPredicate, Integer jokersAmount, List<Card> initialCards) {
        this.cardPredicate = cardPredicate;
        this.jokersAmount = jokersAmount;
        this.cards.addAll(initialCards);
        this.generateCards();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Stream<Card> cards() {
        return this.cards.stream();
    }

    @Override
    public Optional<Card> dealCard() {
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public List<Card> dealCards(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Cards amount should be at least 1");
        }
        return IntStream.range(0, amount)
                .mapToObj(i -> cards.poll())
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void fold(Card card) {
        Objects.requireNonNull(card, "Card should not be null");
        card.fold(this);
    }

    @Override
    public void foldAll(List<Card> cards) {
        Objects.requireNonNull(cards, "Cards should not be null");
        cards.forEach(c -> c.fold(this));
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public int size() {
        return cards.size();
    }

    private void generateCards() {
        var ordinaryCards = Arrays.stream(Suit.values())
                .flatMap(this::generateOrdinaryCards)
                .filter(nullSafePredicate(cardPredicate)
                        .and(s -> s.getSuit() != Suit.JOKER))
                .toList();
        this.cards.addAll(ordinaryCards);

        if (nonNull(jokersAmount)) {
            this.cards.addAll(generateJokers());
        }
    }

    private <T> Predicate<T> nullSafePredicate(Predicate<T> predicate) {
        return isNull(predicate) ? c -> true : predicate;
    }

    private Stream<Card> generateOrdinaryCards(Suit suit) {
        return Arrays.stream(Rank.values())
                .filter(r -> r != Rank.JOKER)
                .map(r -> createCard(r, suit));
    }

    private List<Card> generateJokers() {
        return IntStream.range(0, jokersAmount)
                .mapToObj(i -> createCard(Rank.JOKER, Suit.JOKER))
                .toList();
    }

    private Card createCard(Rank r, Suit s) {
        return new BasicCard(r, s, this);
    }

    public static class Builder {
        private Predicate<Card> cardPredicate;
        private Integer jokersAmount;
        private final List<Card> initialCards = new ArrayList<>();

        private Builder() {
        }

        /**
         * Builds card deck with the card predicate. Please note that the predicate will have no effect on jokers.
         * For jokers setup please use {@link BasicCardDeck.Builder#withJokers} method.
         * Please note that this method and {@link Builder#withStartingRank(Rank)}} will <strong>override</strong> each other
         * so the latest supplied will have effect.
         *
         * @param cardPredicate Card predicate
         * @throws NullPointerException if null value is supplied
         * @return BasicCardDeck builder instance
         */
        public Builder withCardPredicate(Predicate<Card> cardPredicate) {
            Objects.requireNonNull(cardPredicate, "Provided card predicate cannot be null");
            this.cardPredicate = cardPredicate;
            return this;
        }

        /**
         * Builds card deck with starting rank - so there will be no cards in the deck with rank less than the specified.
         * Please note that the predicate will have no effect on jokers.
         * For jokers setup please use {@link BasicCardDeck.Builder#withJokers} method.
         * Please note that this method and {@link Builder#withCardPredicate(Predicate)} will <strong>override</strong> each other
         * so the latest supplied will have effect.
         *
         * @param rank Minimum card rank
         * @throws NullPointerException if null value is supplied
         * @return BasicCardDeck builder instance
         */
        public Builder withStartingRank(Rank rank) {
            Objects.requireNonNull(rank, "Provided rank cannot be null");
            this.cardPredicate = c -> c.getRank().compareTo(rank) >= 0;
            return this;
        }

        /**
         * Builds card deck with the specified amount of jokers (0 - 3) per card deck.
         * Please note that this method specifies amount per entire deck.
         * If this method isn't used, the card deck will be built with no jokers in it.
         *
         * @param jokersAmount Jokers amount
         * @throws IllegalArgumentException if invalid amounts of joker is supplied
         * @return BasicCardDeck builder instance
         */
        public Builder withJokers(int jokersAmount) {
            if (jokersAmount < MINIMUM_JOKERS_AMOUNT || jokersAmount > MAXIMUM_JOKERS_AMOUNT) {
                throw new IllegalArgumentException("Invalid amount of jokers provided");
            }
            this.jokersAmount = jokersAmount;
            return this;
        }

        /**
         * Builds card deck with another card deck.
         *
         * @param cardDeck A card deck
         * @throws NullPointerException if null value is supplied
         * @throws IllegalArgumentException if invalid card deck is supplied
         * @return BasicCardDeck builder instance
         */
        public Builder withCardDeck(CardDeck cardDeck) {
            Objects.requireNonNull(cardDeck, "Cannot add null card deck");

            if (cardDeck.size() == 0) {
                throw new IllegalArgumentException("Cannot use empty card deck");
            }

            while (cardDeck.size() > 0) {
                this.initialCards.addAll(cardDeck.dealCards(cardDeck.size()));
            }

            return this;
        }

        /**
         * Builds the card deck
         *
         * @return The card deck
         */
        public BasicCardDeck build() {
            return new BasicCardDeck(cardPredicate, jokersAmount, initialCards);
        }
    }
}
