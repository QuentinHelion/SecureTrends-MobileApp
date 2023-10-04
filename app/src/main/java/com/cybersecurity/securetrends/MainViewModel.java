package com.cybersecurity.securetrends;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<List<Article>> getArticles() {
        return articlesLiveData;
    }

    public void fetchArticles(String interval, String platform) {
        executorService.execute(() -> {
            try {
                String apiUrl = "https://secure.qhelion.fr/feed?interval=" + interval + "&platform=" + platform;
                String response = fetchDataFromApi(apiUrl);
//                Log.d("dev", response);
                if (response != null) {
                    List<Article> articles = parseJsonResponse(response);
                    articlesLiveData.postValue(articles);
                } else {
                    articlesLiveData.postValue(null); // Handle error when response is null
                }
            } catch (Exception e) {
                e.printStackTrace();
                articlesLiveData.postValue(null); // Handle other exceptions
            }
        });
    }

    private String fetchDataFromApi(String apiUrl) throws IOException {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Handle the response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

        return response.toString();
    }

    private List<Article> parseJsonResponse(String response) {
        List<Article> articleList = new ArrayList<>();

        try {
            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            // Iterate through the JSON array and extract data
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray articleData = jsonArray.getJSONArray(i);
                String title = articleData.getString(0);
                String source = articleData.getString(1);
                String link = articleData.getString(2);
                String description = articleData.getString(3);
                String date = articleData.getString(4);

                // Create an Article object and add it to the list
                Article article = new Article(title, source, link, description, date);
                articleList.add(article);
            }
        } catch (JSONException e) {
            // throw new RuntimeException(e);
            return null;
        }

        return articleList;
    }
}
