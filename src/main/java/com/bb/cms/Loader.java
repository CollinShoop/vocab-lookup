package com.bb.cms;

import com.bb.cms.card.Deck;
import com.bb.cms.usage.LookupResponse;
import com.bb.cms.usage.WordUsageLooker;
import com.bb.cms.usage.impl.WebstersLooker;
import com.bb.cms.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Loader {

    public static void load(String path, boolean replace) throws IOException {
        final Deck deck = Deck.from(IOUtil.readQuizletExport(path));
        final WordUsageLooker looker = new WebstersLooker();
        AtomicInteger count = new AtomicInteger();
        deck.getCardList().forEach(card -> {
            log.info("Processing {}/{}: {}...", count.incrementAndGet(), deck.getCardList().size(), card.getWord());
            if (!card.isUsageIncluded() || card.getPronunciation() == null) {
                final LookupResponse lookup = looker.lookup(card.getWord());
                card.apply(lookup);
            }
        });
        IOUtil.saveFile(path + (replace ? "" : ".complete"), deck);
    }

}
