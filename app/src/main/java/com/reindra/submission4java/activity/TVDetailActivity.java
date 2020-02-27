package com.reindra.submission4java.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.graphics.PorterDuff;
import android.net.Uri;
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
import com.reindra.submission4java.database.TVHelper;
import com.reindra.submission4java.model.Movie;

import static android.provider.BaseColumns._ID;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.CONTENT_MOVIE;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.CONTENT_TV;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.COUNTRY;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.OVERVIEW;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.POSTER;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.RATING;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.TITLE;
import static com.reindra.submission4java.database.DatabaseContract.MoviesColumns.YEAR;

public class TVDetailActivity extends AppCompatActivity {

    public static final String FLAG_EXTATV = "flag_extra";
    ProgressBar progressBar;
    Movie movie = new Movie();
    private TVHelper tvHelper;
    TextView title, overview, date, rate;
    ImageView poster, favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbarText = findViewById(R.id.toolbar_text);
        progressBar = findViewById(R.id.progressBartv);
        title = findViewById(R.id.tv_tittle_detailtv);
        overview = findViewById(R.id.tv_synopsistv);
        date = findViewById(R.id.tv_datetv);
        rate = findViewById(R.id.tv_score_detailtv);
        poster = findViewById(R.id.iv_poster_detailtv);
        RatingBar ratingbar = findViewById(R.id.rb_scoretv);
        favorite = findViewById(R.id.iv_hearttv);

        movie = getIntent().getParcelableExtra(FLAG_EXTATV);
        tvHelper = TVHelper.getInstance(getApplicationContext());

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
//                tvCountry.setText(movie.getCountry());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(poster);
            showloading(false);

            tvHelper.open();
            if (tvHelper.getAllTV(movie.getId())) {
                favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
            }

        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvHelper.getAllTV(movie.getId())) {
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
        TextView notif = DialogviView.findViewById(R.id.tvnotif);
        notif.setText(R.string.notifadd);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TVDetailActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btnalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHelper.open();
                favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                addItemTV();
                alertDialog.dismiss();
            }
        });
        tvHelper.close();
        alertDialog.show();
    }

    private void addItemTV() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movie.getId());
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(YEAR, movie.getDate());
        contentValues.put(OVERVIEW, movie.getOverview());
        contentValues.put(POSTER, movie.getPhoto());
        contentValues.put(RATING, movie.getRating());
        contentValues.put(COUNTRY, movie.getCountry());

        getContentResolver().insert(CONTENT_TV, contentValues);
        if (contentValues != null) {
            Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
            favorite.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        } else {
            Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            favorite.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void deleteItem() {
        Uri uri;
        uri = Uri.parse(CONTENT_TV + "/" + movie.getId());
        getContentResolver().delete(uri, null, null);
        Toast.makeText(this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }

}

