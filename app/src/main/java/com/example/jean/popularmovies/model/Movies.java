package com.example.jean.popularmovies.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jean on 18/10/2016.
 */

public class Movies {

    public long id;
    public String poster_path;
    public boolean adult;
    public String overview;
    public Date release_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185"+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(release_date);
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return Double.toString(vote_average);
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public double popularity;
    public long vote_count;
    public double vote_average;
}
