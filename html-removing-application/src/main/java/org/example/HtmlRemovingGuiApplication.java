package org.example;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import javax.swing.*;
import java.awt.*;

public class HtmlRemovingGuiApplication {
    public static void main(String[] args) {
        // Disable SSL verification for testing
        disableSSLVerification();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HTML Remover");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField urlField = new JTextField("https://");
            JButton fetchButton = new JButton("Pobierz i usuń HTML");

            // Configure text area for better readability
            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            resultArea.setLineWrap(true);
            resultArea.setWrapStyleWord(true);
            resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(resultArea);

            JPanel topPanel = new JPanel(new BorderLayout(5, 0));
            topPanel.add(new JLabel("URL: "), BorderLayout.WEST);
            topPanel.add(urlField, BorderLayout.CENTER);
            topPanel.add(fetchButton, BorderLayout.EAST);

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            fetchButton.addActionListener(e -> {
                String url = urlField.getText();
                try {
                    resultArea.setText("Pobieranie...");
                    String cleanedText = PageDownloadingUtility.downloadAndClean(url);

                    // Format text with paragraphs for better readability
                    cleanedText = formatTextWithParagraphs(cleanedText);

                    resultArea.setText(cleanedText);
                    resultArea.setCaretPosition(0); // Scroll to top
                } catch (Exception ex) {
                    resultArea.setText("Błąd: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * Formats text to have better paragraph structure and readability
     */
    private static String formatTextWithParagraphs(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // Replace multiple spaces with single space
        String formatted = text.replaceAll("\\s+", " ");

        // Add line breaks for better paragraph structure
        formatted = formatted.replaceAll("\\. ", ".\n\n");
        formatted = formatted.replaceAll("\\? ", "?\n\n");
        formatted = formatted.replaceAll("! ", "!\n\n");

        // Remove excessive newlines
        formatted = formatted.replaceAll("\n{3,}", "\n\n");

        return formatted;
    }

    private static void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}