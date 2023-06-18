package com.cybersecurity.securetrends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Button feedBtn;
    private TextView feedTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.feedBtn = findViewById(R.id.feed_btn);
        this.feedTxt = findViewById(R.id.feed_txt);

        //ApiRequestTask apiRequestTask = new ApiRequestTask();

        this.feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //JSONObject jsonResponse = apiRequestTask.doInBackground("https://127.0.0.1:5000/feed");
                //JSONArray resultArray = null;
                ApiRequestTask apiRequestTask = new ApiRequestTask();
                apiRequestTask.execute("http://127.0.0.1:5000/feed");
                /*
                try {
                    // Parse the JSON string
                    //apiRequestTask.execute("https://127.0.0.1:5000/feed");

                    JSONArray resultArray = jsonResponse.getJSONArray("result");

                    StringBuilder displayTextBuilder = new StringBuilder();

                    // Iterate through the result array
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONArray itemArray = resultArray.getJSONArray(i);
                        String title = itemArray.getString(0);
                        String source = itemArray.getString(1);
                        String url = itemArray.getString(2);
                        String description = itemArray.getString(3);

                        // Append the information to the display text
                        displayTextBuilder.append("Title: ").append(title).append("\n")
                                .append("Source: ").append(source).append("\n")
                                .append("URL: ").append(url).append("\n")
                                .append("Description: ").append(description).append("\n\n");
                    }

                    // Set the text in the TextView
                    feedTxt.setText(displayTextBuilder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 */
            }
        });

    }
}