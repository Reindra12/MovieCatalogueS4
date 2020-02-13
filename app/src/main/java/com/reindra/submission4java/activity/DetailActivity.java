package com.reindra.submission4java.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.reindra.submission4java.R;
import com.reindra.submission4java.database.MovieHelper;
import com.reindra.submission4java.model.Movie;

public class DetailActivity extends AppCompatActivity {
    public static String FLAG_EXTRA = "flag_extra";
    ProgressBar progressBar;
    Movie movie = new Movie();
    private MovieHelper movieHelper;
    TextView title, overview, date, rate, notif, toolbarText;
    ImageView poster, favorite;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarText = findViewById(R.id.toolbar_text);
        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.tv_tittle_detail);
        overview = findViewById(R.id.tv_synopsis);
        date = findViewById(R.id.tv_date_movie);
        rate = findViewById(R.id.tv_score_detail);
        poster = findViewById(R.id.iv_poster_detail);
        TextView tvCountry = findViewById(R.id.tv_country_detail);
        RatingBar ratingbar = findViewById(R.id.rb_score);
        favorite = findViewById(R.id.iv_heartdetail);

        movie = getIntent().getParcelableExtra(FLAG_EXTRA);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        showloading(true);
        if (movie != null) {
            toolbarText.setText(movie.getTitle());
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
                    dialog();
                } else {
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

    private void dialog() {
        ViewGroup viewGroup = findViewById(R.id.content);
        View DialogviView = LayoutInflater.from(this).inflate(R.layout.dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(DialogviView);
        final AlertDialog alertDialog = builder.create();
        Button btnalert = DialogviView.findViewById(R.id.btnyes);
        ImageView clear = DialogviView.findViewById(R.id.imgclear);
        notif = DialogviView.findViewById(R.id.tvnotif);

        notif.setText(R.string.notifadd);

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

    private void deleteItem() {
        int result = movieHelper.delete(movie.getId());
        if (result > 0) {
            Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
        } else {
        }
    }

    private void addItemToFavorite() {
        long result = movieHelper.insert(this.movie);
        if (result > 0) {
            Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
        } else {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }

}

