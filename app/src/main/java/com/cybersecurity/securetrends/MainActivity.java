package com.cybersecurity.securetrends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView apiResponseTextView;
    private MainViewModel viewModel;
    private Spinner intervalSpinner;
    private Spinner platformSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        intervalSpinner = findViewById(R.id.intervalSpinner);
        platformSpinner = findViewById(R.id.platformSpinner);

        // Populate the interval spinner with options
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(
                this, R.array.interval_options, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);

        // Populate the platform spinner with options
        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(
                this, R.array.platform_options, android.R.layout.simple_spinner_item);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platformSpinner.setAdapter(platformAdapter);


        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle interval selection change here
                String selectedInterval = parentView.getItemAtPosition(position).toString();
                String selectedPlatform = platformSpinner.getSelectedItem().toString();

                viewModel.fetchArticles(selectedInterval,selectedPlatform);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle when nothing is selected (if needed)
            }
        });

        platformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle platform selection change here
                String selectedPlatform = parentView.getItemAtPosition(position).toString();
                String selectedInterval = intervalSpinner.getSelectedItem().toString();
                // Call your fetchArticle method with the selected parameters
                viewModel.fetchArticles(selectedInterval,selectedPlatform);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle when nothing is selected (if needed)
            }
        });



        // Observe the LiveData and update the UI when data changes
        viewModel.getArticles().observe(this, articles -> {
            if (articles != null) {
                ArticleAdapter adapter = new ArticleAdapter(this, articles);
                ListView scrollView = findViewById(R.id.scrollView);
                scrollView.setAdapter(adapter);
            }
        });

        // Make an API request when the activity is created
        viewModel.fetchArticles("3","%");
    }
}
