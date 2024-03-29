package com.cybersecurity.securetrends;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.cybersecurity.securetrends.Article;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);

        // Sort the articles by date in descending order (most recent first)
        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article article1, Article article2) {
                return article2.getDate().compareTo(article1.getDate());
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Article article = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_article, parent, false);
        }

        // Lookup view for data population
        TextView title = convertView.findViewById(R.id.articleTitle);
        EditText date = convertView.findViewById(R.id.articleDate);
        TextView source = convertView.findViewById(R.id.articleSource);
        TextView description = convertView.findViewById(R.id.articleDescription);

        // Populate the data into the template view using the data object
        title.setText(article.getTitle());
        date.setText(article.getDate());
        source.setText(article.getSource());
        description.setText(article.getDescription());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked article
                Article article = getItem(position);

                // Create an Intent to open a web page
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));

                // Start the browser or a WebView to display the web page
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
