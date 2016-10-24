package com.example.jean.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.jean.popularmovies.adapter.MoviesAdapter;
import com.example.jean.popularmovies.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movies> mMovies = new ArrayList<>();
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchMovieTask fetchMovie = new FetchMovieTask();
        fetchMovie.execute();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movies>> {


        @Override
        protected ArrayList<Movies> doInBackground(Void... params) {

            ArrayList<Movies> mv = new ArrayList<Movies>();
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String filter = prefs.getString("key", "top_rated");

            try {
                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String API_ID = "api_key";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(filter)
                        .appendQueryParameter(API_ID, BuildConfig.MOVIES_API_KEY).build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                moviesJsonStr = buffer.toString();

                Log.e("TESTE : ", moviesJsonStr);

                try {
                    mv = getMovieDataFromJson(moviesJsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {

            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null) {

                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return mv;
        }


        public ArrayList<Movies> getMovieDataFromJson(String movieJsonStr) throws JSONException {

            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_ADULT = "adult";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_ID = "id";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_ORIGINAL_LANGUAGE = "original_language";
            final String TMDB_TITLE = "title";
            final String TMDB_BACKDROP_PATH = "backdrop_path";
            final String TMDB_POPULARITY = "popularity";
            final String TMDB_VOTE_COUNT = "vote_count";
            final String TMDB_VIDEO = "video";
            final String TMDB_VOTE_AVERSAGE = "vote_average";


            JSONObject moviesJson = new JSONObject(movieJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

            Log.e("array : ", moviesJson.getJSONArray(TMDB_RESULTS).toString());


            ArrayList<Movies> moviesList = new ArrayList<Movies>();


            for (int i = 0; i < moviesArray.length(); i++) {
                Movies moviesDb = new Movies();
                JSONObject movies = moviesArray.getJSONObject(i);

                moviesDb.setPoster_path(movies.getString(TMDB_POSTER_PATH));
                moviesDb.setAdult(movies.getBoolean(TMDB_ADULT));
                moviesDb.setOverview(movies.getString(TMDB_OVERVIEW));
                moviesDb.setRelease_date(convertReleaseDate((String) movies.get(TMDB_RELEASE_DATE)));
                moviesDb.setId(movies.getLong(TMDB_ID));
                moviesDb.setOriginal_title(movies.getString(TMDB_ORIGINAL_TITLE));
                moviesDb.setOriginal_language(movies.getString(TMDB_ORIGINAL_LANGUAGE));
                moviesDb.setTitle(movies.getString(TMDB_TITLE));
                moviesDb.setBackdrop_path(movies.getString(TMDB_BACKDROP_PATH));
                moviesDb.setPopularity(movies.getDouble(TMDB_POPULARITY));
                moviesDb.setVote_count(movies.getLong(TMDB_VOTE_COUNT));
                moviesDb.setVote_average(movies.getDouble(TMDB_VOTE_AVERSAGE));

                moviesList.add(moviesDb);

                Log.e("Title: ", moviesDb.getTitle());
                Log.e("Release date: ", moviesDb.getRelease_date().toString());
                Log.e("URL: ", moviesDb.getPoster_path());

            }


            return moviesList;
        }

        private Date convertReleaseDate(String release_date) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = new Date();
            try {
                data = dateFormat.parse(release_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(final ArrayList<Movies> movies) {
            super.onPostExecute(movies);

            gridView = (GridView) findViewById(R.id.lv);
            gridView.setAdapter(new MoviesAdapter(MainActivity.this, movies));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movies mv = movies.get(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class)
                            .putExtra("title", mv.getTitle())
                            .putExtra("date", mv.getRelease_date())
                            .putExtra("movie_poster", mv.getPoster_path())
                            .putExtra("vote_average", mv.getVote_average())
                            .putExtra("overview", mv.getOverview());
                    startActivity(intent);


                    Toast.makeText(MainActivity.this, movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}
