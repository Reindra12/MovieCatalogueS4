package com.reindra.submission4java;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.tv_titlebar);
        title.setText(R.string.title_movie);
        ImageView language = findViewById(R.id.imgsetting);
        language.setOnClickListener(this);


        BottomNavigationView navView = findViewById(R.id.nav_view);
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie, R.id.nav_tv, R.id.nav_fav)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgsetting:
                Intent intent = new Intent (Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
    }
}