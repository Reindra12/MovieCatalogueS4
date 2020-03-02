package com.reindra.submission4java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reindra.submission4java.R;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  TextView title = findViewById(R.id.tv_titlebar);
        title.setText(R.string.title_movie);
        ImageView language = findViewById(R.id.imgsetting);
        ImageView search = findViewById(R.id.imgsearch);
        ImageView notif = findViewById(R.id.imgnotif);*/

      /*  search.setOnClickListener(this);
        notif.setOnClickListener(this);
        language.setOnClickListener(this);*/
        movie.setOnClickListener(this);
        tvshow.setOnClickListener(this);
        favorite.setOnClickListener(this);
        loadFragment(new MovieFragment());

        AppBarConfiguration appbar = new AppBarConfiguration.Builder(R.id.nav_movie, R.id.nav_tv, R.id.nav_fav)
                .build();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
       NavigationUI.setupActionBarWithNavController(this, navController,appbar);
        NavigationUI.setupWithNavController(navView, navController);

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

   /* @Override
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