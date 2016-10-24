package com.example.jean.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivPoster = (ImageView) findViewById(R.id.iv_poster);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        TextView tvVoteAverage = (TextView) findViewById(R.id.tv_vote_average);
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);


        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra("title")) {

            Picasso.with(this)
                    .load(intent.getStringExtra("movie_poster"))
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivPoster);
            tvTitle.setText(intent.getStringExtra("title"));
            tvReleaseDate.setText(intent.getStringExtra("date"));
            tvVoteAverage.setText(intent.getStringExtra("vote_average"));
            tvOverview.setText(intent.getStringExtra("overview"));

        }

    }


}
