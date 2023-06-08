package com.cybersecurity.securetrends;

import static com.cybersecurity.securetrends.Requester.getRssFeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void main(String[] args) {
        String apiUrl = "https://127.0.0.1:5000/feed"; // Replace with the actual API URL

        try {
            String rssFeed = getRssFeed(apiUrl);
            System.out.println(rssFeed);
        } catch (IOException e) {
            System.out.println("Error retrieving RSS feed: " + e.getMessage());
        }
    }
}