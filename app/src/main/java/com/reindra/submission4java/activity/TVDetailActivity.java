package com.reindra.submission4java.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.reindra.submission4java.R;
import com.reindra.submission4java.database.TVHelper;
import com.reindra.submission4java.model.Movie;

public class TVDetailActivity extends AppCompatActivity {

    public static String FLAG_EXTATV = "flag_extra";
    ProgressBar progressBar;
    Movie movie = new Movie();
    private TVHelper tvHelper;
    String status;
    TextView title, overview, date, rate;
    ImageView poster, favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);

        progressBar = findViewById(R.id.progressBartv);
        title = findViewById(R.id.tv_tittle_detailtv);
        overview = findViewById(R.id.tv_synopsistv);
        date = findViewById(R.id.tv_yeartv);
        rate = findViewById(R.id.tv_score_detailtv);
        poster = findViewById(R.id.iv_poster_detailtv);
        TextView tvCountry = findViewById(R.id.tv_country_detailtv);
        RatingBar ratingbar = findViewById(R.id.rb_scoretv);
        favorite = findViewById(R.id.iv_hearttv);

        movie = getIntent().getParcelableExtra(FLAG_EXTATV);
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();

        tvHelper = TVHelper.getInstance(getApplicationContext());
        tvHelper.open();

        showloading(true);
        if (movie != null) {
            title.setText(movie.getTitle());
            date.setText(movie.getDate());
            Float count = Float.parseFloat(movie.getRating());
            count = Float.valueOf(count * 10);
            rate.setText(String.format("%s%%", count.intValue()));
            ratingbar.setRating(count / 20f);
            overview.setText(movie.getOverview());
//                tvCountry.setText(movie.getCountry());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(poster);
            showloading(false);
            if (tvHelper.getAllTV(movie.getId())) {
                favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                addItemTV();
            }

        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvHelper.getAllTV(movie.getId())) {
                    favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    addItemTV();
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (tvHelper.getAllTV(movie.getId())) {
            favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_favorite) {
            if (!tvHelper.getAllTV(this.movie.getId())) {
                item.setIcon(R.drawable.ic_favorite);
                addItemTV();
            } else {
                item.setIcon(R.drawable.ic_favorite_2);
                deleteItem();
            }
        }
        return super.onOptionsItemSelected(item);
    }*/


    private void addItemTV() {
        long result = tvHelper.insert(this.movie);
        if (result > 0)
            Toast.makeText(this, "sukseskan", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();


    }

    private void deleteItem() {
        int result = tvHelper.delete(movie.getId());
        if (result > 0) {
            Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}

