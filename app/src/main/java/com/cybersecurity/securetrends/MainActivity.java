package com.cybersecurity.securetrends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView apiResponseTextView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        apiResponseTextView = findViewById(R.id.apiResponseTextView);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Observe the LiveData and update the UI when data changes
        viewModel.getArticles().observe(this, articles -> {
            if (articles != null) {
//                StringBuilder parsedData = new StringBuilder();
//                for (Article article : articles) {
//
//
//                    parsedData.append("Title: ").append(article.getTitle()).append("\n");
//                    parsedData.append("Source: ").append(article.getSource()).append("\n");
//                    parsedData.append("Link: ").append(article.getLink()).append("\n");
//                    parsedData.append("Description: ").append(article.getDescription()).append("\n");
//                    parsedData.append("Date: ").append(article.getDate()).append("\n\n");
//                }
//                apiResponseTextView.setText(parsedData.toString());
                ArticleAdapter adapter = new ArticleAdapter(this, articles);
                ListView scrollView = findViewById(R.id.scrollView);
                scrollView.setAdapter(adapter);

            } else {
//                apiResponseTextView.setText("Error fetching data.");
            }
        });

        // Make an API request when the activity is created
        viewModel.fetchArticles();
    }
}
