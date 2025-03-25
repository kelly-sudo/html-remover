package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class StringHtmlCleaner {
    public String clean(String html) {
        String textToClean = html;
        Document document = Jsoup.parse(textToClean);
        String textWithoutHtml = document.text();
        return textWithoutHtml;
    }
}

