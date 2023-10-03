package com.cybersecurity.securetrends;

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
        return date;
    }
}
