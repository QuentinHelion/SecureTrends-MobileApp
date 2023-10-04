package com.cybersecurity.securetrends;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Article {
    private String title;
    private String source;
    private String link;
    private String description;
    private String date;

    public Article(String title, String source, String link, String description, String date) {
        this.title = title;
        this.source = source;
        this.link = link;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        // Parse the date from the current format
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        try {
            Date parsedDate = currentDateFormat.parse(date);

            // Format the date into the desired format
            SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            return desiredDateFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date; // Return the original date if parsing fails
        }
    }
}
