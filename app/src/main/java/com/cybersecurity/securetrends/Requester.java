package com.cybersecurity.securetrends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    public static String getRssFeed(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set request method and headers if needed
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/rss+xml");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder rssFeedBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                rssFeedBuilder.append(line);
                rssFeedBuilder.append(System.lineSeparator());
            }

            reader.close();
            return rssFeedBuilder.toString();
        } else {
            throw new IOException("API request failed with response code: " + responseCode);
        }
    }
}
