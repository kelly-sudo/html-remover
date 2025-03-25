package org.example;

import java.io.IOException;

public class HtmlRemovingApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar html-removing-application.jar <url>");
            System.exit(1);
        }

        String url = args[0];
        try {
            String cleanedText = PageDownloadingUtility.downloadAndClean(url);
            System.out.println("Content from " + url + " without HTML tags:");
            System.out.println("----------------------------------------");
            System.out.println(cleanedText);
        } catch (Exception e) {
            System.err.println("Error processing URL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
