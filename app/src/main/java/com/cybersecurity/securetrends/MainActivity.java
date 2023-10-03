package com.cybersecurity.securetrends;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView apiResponseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiResponseTextView = findViewById(R.id.apiResponseTextView);

        // Make an API request when the activity is created
        new ApiRequestTask().execute();
    }

    private class ApiRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String apiUrl = "https://secure.qhelion.fr/feed?interval=1"; // Replace with your API URL
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

        @Override
        protected void onPostExecute(String result) {
            try {
                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // Create a StringBuilder to store the parsed data
                StringBuilder parsedData = new StringBuilder();

                // Iterate through the JSON array and extract data
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray article = jsonArray.getJSONArray(i);
                    String title = article.getString(0);
                    String source = article.getString(1);
                    String link = article.getString(2);
                    String description = article.getString(3);
                    String date = article.getString(4);

                    // Append the extracted data to the StringBuilder
                    parsedData.append("Title: ").append(title).append("\n");
                    parsedData.append("Source: ").append(source).append("\n");
                    parsedData.append("Link: ").append(link).append("\n");
                    parsedData.append("Description: ").append(description).append("\n");
                    parsedData.append("Date: ").append(date).append("\n\n");
                }

                // Display the parsed data in the TextView
                apiResponseTextView.setText(parsedData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                apiResponseTextView.setText("Error parsing JSON: " + e.getMessage());
            }
        }
    }
}
