package com.bb.cms.card;

import com.bb.cms.usage.LookupResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class Card {

    private static final String PRONUNCIATION_START = "/";
    private static final String PRONUNCIATION_END = "\\";
    private static final int USAGE_SIMPLIFICATION_LENGTH_MAX = 100;

    private String word;
    private String pronunciation;
    private String definition;
    private boolean usageIncluded;

    public void apply(LookupResponse lookup) {
        if (lookup == null) {
            return;
        }
        addUsage(simplifyUsage(lookup.getSimpleUsage()));
        setPronunciation(lookup.getPronunciation());
    }

    public List<String> simplifyUsage(List<String> usage) {
        if (usage == null || usage.isEmpty()) {
            return usage;
        }
        // first remove any ending quote/signature
        usage = usage.stream().map(s -> {
            if (s.contains("—")) {
                return s.substring(0, s.indexOf("—")).trim();
            }
            return s;
        }).collect(Collectors.toList());
        usage.sort(Comparator.comparingInt(String::length));
        if (usage.get(0).length() > USAGE_SIMPLIFICATION_LENGTH_MAX) {
            return Collections.singletonList(usage.get(0));
        }
        return usage.stream()
                .filter(s -> s.length() <= USAGE_SIMPLIFICATION_LENGTH_MAX)
                .collect(Collectors.toList());
    }

    public void addUsage(List<String> usage) {
        if (!isUsageIncluded() && usage != null && !usage.isEmpty()) {
            usageIncluded = true;
            definition += "\nUsage: " + String.join(" ; ", usage);
        }
    }

    public static Card from(String s) {
        String[] tokens = s.split("\t");
        if (tokens.length <= 1) {
            log.warn("Invalid input given: {}", s);
            return null;
        }
        final Card instance = new Card();
        instance.word = tokens[0].trim();
        if (instance.word.contains(PRONUNCIATION_START) && instance.word.contains(PRONUNCIATION_END)) {
            instance.pronunciation = instance.word.substring(
                    instance.word.indexOf(PRONUNCIATION_START) + PRONUNCIATION_START.length(),
                    instance.word.indexOf(PRONUNCIATION_END)
            );
            instance.word = instance.word.substring(0, instance.word.indexOf(PRONUNCIATION_START)).trim();
        }
        instance.definition = tokens[1].trim();
        instance.usageIncluded = instance.definition.contains("Usage:");
        return instance;
    }

    @Override
    public String toString() {
        return word + "  " + PRONUNCIATION_START + pronunciation + PRONUNCIATION_END + "\t" + definition;
    }

}
