package com.bb.cms.usage.impl;

import com.bb.cms.usage.LookupResponse;
import com.bb.cms.usage.WordUsageLooker;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class WebstersLooker implements WordUsageLooker {

    private static final String BASE_URL = "https://www.merriam-webster.com/dictionary";

    @Override
    public LookupResponse lookup(String word) {
        try {
            return doLookup(word);
        } catch (IOException e) {
            log.error("Unable to find word={}; {}; message={}; cause={}; trace={};",
                    word, e.getClass().getSimpleName(), e.getMessage(), e.getCause(), e.getStackTrace());
            return null;
        }
    }

    private LookupResponse doLookup(String word) throws IOException {
        final Connection.Response response = Jsoup.connect(getUrl(word))
                .userAgent("Mozilla")
                .timeout(3000)
                .method(Connection.Method.GET)
                .execute();
        return parseBody(response.body());
    }

    private String getUrl(String word) {
        return BASE_URL + "/" + word.toLowerCase();
    }

    private LookupResponse parseBody(String html) {
        final LookupResponse response = new LookupResponse();
        Document document = Jsoup.parse(html);
        parseSimpleUsage(document, response);
        parsePronunciation(document, response);
        return response;
    }

    private void parseSimpleUsage(final Document document, final LookupResponse response) {
        final Elements usage2 = document.select("div.in-sentences span.ex-sent");
        if (usage2 != null && !usage2.isEmpty()) {
            usage2.forEach(element -> {
                response.addSimpleUsage(trim(element.text()));
            });
        }
    }

    private void parsePronunciation(final Document document, final LookupResponse response) {
        final Elements e = document.select("span.prs span.pr");
        if (e != null && !e.isEmpty()) {
            response.setPronunciation(trim(e.get(0).text()));
        }
    }

    private static String trim(String s) {
        s = s.trim();
        while (s.endsWith(".") || s.endsWith("!")) {
            s = s.substring(0, s.length() - 1);
            s = s.trim();
        }
        return s;
    }

    public static void main(String[] args) {
        WebstersLooker looker = new WebstersLooker();
        log.info("{}", looker.lookup("Abscond"));
    }

}
