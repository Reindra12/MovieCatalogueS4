package com.reindra.favoritemovieapps.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reindra.favoritemovieapps.R;
import com.reindra.favoritemovieapps.fragment.favorite.FavMovieFragment;
import com.reindra.favoritemovieapps.fragment.favorite.FavTVFragment;

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
        loadFragment(new FavMovieFragment());

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
                fragment = new FavMovieFragment();
                setTitle(getString(R.string.tab_text_1));
                loadFragment(fragment);
                break;
            case R.id.nav_tv:
                fragment = new FavTVFragment();
                loadFragment(fragment);
                setTitle(getString(R.string.tab_text_2));
                break;

            default:
                break;
        }
    }

}