package com.cybersecurity.securetrends;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequestTask extends AsyncTask<String, Void, JSONObject> {

    private static final String TAG = ApiRequestTask.class.getSimpleName();

    @Override
    protected JSONObject doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        JSONObject jsonResponse = null;

        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            // Read the response
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            // Parse JSON response
            jsonResponse = new JSONObject(builder.toString());

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error occurred during API request: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing reader: " + e.getMessage());
                }
            }
        }

        return jsonResponse;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResponse) {
        if (jsonResponse != null) {
            // Process the JSON response here
            try {
                String data = jsonResponse.getString("data");
                Log.d(TAG, "Received data: " + data);

                // You can extract more values or perform other operations on the JSON response

            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
            }
        }
    }
}

