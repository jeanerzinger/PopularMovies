package com.example.jean.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jean.popularmovies.R;
import com.example.jean.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jean on 18/10/2016.
 */

public class MoviesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movies> movies;

    public MoviesAdapter(Context context, ArrayList<Movies> movies) {
        this.context = context;
        this.movies = movies;

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movies moviesView = movies.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.movies_list_layout, null);

        ImageView iv = (ImageView) layout.findViewById(R.id.iv_movies);
        Picasso.with(context)
                .load(moviesView.getPoster_path())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fit()
                .into(iv);

        return layout;
    }
}
