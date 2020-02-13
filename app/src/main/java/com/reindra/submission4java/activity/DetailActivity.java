package com.reindra.submission4java.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.reindra.submission4java.R;
import com.reindra.submission4java.database.MovieHelper;
import com.reindra.submission4java.database.TVHelper;
import com.reindra.submission4java.fragment.FavoriteFragment;
import com.reindra.submission4java.model.Movie;

import static com.reindra.submission4java.R.id.navigation_favorite;

public class DetailActivity extends AppCompatActivity {
    public static String FLAG_EXTRA = "flag_extra";
    ProgressBar progressBar;
    Movie movie = new Movie();
    private static FragmentManager fragmentManager;
    private MovieHelper movieHelper;
//    private TVHelper tvHelper;
    String status;
    TextView title, overview, date, rate;
    ImageView poster,  favorite;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.tv_tittle_detail);
        overview = findViewById(R.id.tv_synopsis);
        date = findViewById(R.id.tv_year);
        rate = findViewById(R.id.tv_score_detail);
        poster = findViewById(R.id.iv_poster_detail);
        TextView tvCountry = findViewById(R.id.tv_country_detail);
        RatingBar ratingbar = findViewById(R.id.rb_score);
        favorite = findViewById(R.id.iv_heartdetail);

        movie = getIntent().getParcelableExtra(FLAG_EXTRA);
        Intent intent = getIntent();
//        status = intent.getStringExtra("status");
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        showloading(true);
        if (movie != null) {
            title.setText(movie.getTitle());
            date.setText(movie.getDate());
            Float count = Float.parseFloat(movie.getRating());
            count = Float.valueOf(count * 10);
            rate.setText(String.format("%s%%", count.intValue()));
            ratingbar.setRating(count / 20f);
            overview.setText(movie.getOverview());
            tvCountry.setText(movie.getCountry());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(poster);
            showloading(false);

            if (movieHelper.getAll(movie.getId())) {
                favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                addItemToFavorite();
            }
        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!movieHelper.getAll(movie.getId())) {
                   favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                   addItemToFavorite();
               }else{
                   favorite.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
                   deleteItem();
               }
            }
        });
    }

    private void showloading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void dialog() {
        ViewGroup viewGroup = findViewById(R.id.content);
        View DialogviView = LayoutInflater.from(this).inflate(R.layout.dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(DialogviView);
        final AlertDialog alertDialog = builder.create();
        Button btnalert = DialogviView.findViewById(R.id.btnyes);
        ImageView clear = DialogviView.findViewById(R.id.imgclear);
        notif = DialogviView.findViewById(R.id.tvnotif);

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (movieHelper.getAll(movie.getId())) {
            favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

        }
        return super.onPrepareOptionsMenu(menu);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btnalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                addItemToFavorite();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fragmentManager = getSupportFragmentManager();
//        if (item.getItemId()== R.id.menu_favorite){
            try {
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.navigation_favorite1, favoriteFragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
//            }
//            fragmentManager.beginTransaction().replace(R.).commit();
//            Intent intent = new Intent(DetailActivity.this, FavoriteActivity.class);
//            startActivity(intent);
//            finish();
       *//* if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_favorite) {*//*

           *//* Intent intent = new Intent(DetailActivity.this, FavoriteFragment.class);
            startActivity(intent);*//*
            *//*if (!movieHelper.getAll(this.movie.getId())) {
                item.setIcon(R.drawable.ic_favorite);
                addItemToFavorite();
            } else {
                item.setIcon(R.drawable.ic_favorite_2);
                deleteItem();
            }*//*
        }
       *//* if (status.equals("movie")) {
            addItemToFavorite();
        } else {
            addItemTV();
//            addItemToFavorite();
            Toast.makeText(this, "ID TV", Toast.LENGTH_SHORT).show();

        }*//*

            return super.onOptionsItemSelected(item);
        }*/

   /* private void addItemTV() {
        long result = tvHelper.insert(this.movie);
        if (result > 0)
            Toast.makeText(this, "sukseskan", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();


    }*/

        private void deleteItem () {
            int result = movieHelper.delete(movie.getId());
            if (result > 0) {
                Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
            }
        }

        private void addItemToFavorite () {
            long result = movieHelper.insert(this.movie);
            if (result > 0) {
                Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "failed add item", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            movieHelper.close();
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}

