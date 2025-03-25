package org.example;
import org.jsoup.Jsoup;
import java.io.IOException;

public class PageDownloadingUtility {
    private PageDownloadingUtility() {
    }
    public static String download(String url) {
        try {
            return Jsoup.connect(url).get().html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String downloadAndClean(String url) {
        String htmlContent = download(url);
        StringHtmlCleaner cleaner = new StringHtmlCleaner();
        return cleaner.clean(htmlContent);
    }
}
