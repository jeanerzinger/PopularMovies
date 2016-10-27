package com.example.jean.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jean.popularmovies.model.Movies;
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
        Movies mv = intent.getParcelableExtra("movie");


        getSupportActionBar().setTitle(mv.getTitle()); // set the top title


            Picasso.with(this)
                    .load(mv.getPoster_path())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivPoster);

            tvTitle.setText(mv.getTitle());
            tvReleaseDate.setText(mv.getRelease_date());
            tvVoteAverage.setText(mv.getVote_average());
            tvOverview.setText(mv.getOverview());

        }

    }



