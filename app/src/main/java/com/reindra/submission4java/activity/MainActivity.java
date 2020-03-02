package com.reindra.submission4java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reindra.submission4java.R;
import com.reindra.submission4java.fragment.MovieFragment;
import com.reindra.submission4java.fragment.TVFragment;
import com.reindra.submission4java.fragment.favorite.FavFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView movie, tvshow;
    FloatingActionButton favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.tab_text_1));

        movie = findViewById(R.id.nav_movie);
        tvshow = findViewById(R.id.nav_tv);
        favorite = findViewById(R.id.fbfavorite);

        movie.setOnClickListener(this);
        tvshow.setOnClickListener(this);
        favorite.setOnClickListener(this);
        loadFragment(new MovieFragment());

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.nav_movie:
                Toast.makeText(this, "movie", Toast.LENGTH_SHORT).show();
                fragment = new MovieFragment();
                setTitle(getString(R.string.tab_text_1));
                loadFragment(fragment);
                break;
            case R.id.nav_tv:
                Toast.makeText(this, "tv", Toast.LENGTH_SHORT).show();
                fragment = new TVFragment();
                loadFragment(fragment);
                setTitle(getString(R.string.tab_text_2));
                break;
            case R.id.fbfavorite:
                fragment = new FavFragment();
                setTitle(getString(R.string.tab_text_3));
                loadFragment(fragment);
                break;

            default:
                break;
        }
    }
}