package com.bb.cms.card;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Deck {

    private List<Card> cardList;

    public static Deck from(List<String> list) {
        final Deck instance = new Deck();
        instance.setCardList(
                list.stream()
                        .map(Card::from)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
        return instance;
    }

    @Override
    public String toString() {
        return cardList.stream().map(Card::toString).collect(Collectors.joining("\n\n"));
    }

}
