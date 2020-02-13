package com.reindra.submission4java.fragment.favorite;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reindra.submission4java.R;
import com.reindra.submission4java.activity.DetailActivity;
import com.reindra.submission4java.adapter.MovieAdapter;
import com.reindra.submission4java.database.MovieHelper;
import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieHelper movieHelper;
    private ArrayList<Movie>ListMovie;
    private MovieAdapter movieAdapter;
    private LinearLayout nodata;


    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nodata = view.findViewById(R.id.view_no_data);
        recyclerView = view.findViewById(R.id.rv_category);
        movieHelper = MovieHelper.getInstance(getContext());
        ListMovie = new ArrayList<>();

        movieAdapter = new MovieAdapter();

    }

    @Override
    public void onStart() {
        super.onStart();
        movieHelper.open();
        ListMovie.clear();
        ListMovie.addAll(movieHelper.getAllMovies());
        movieAdapter.setData(ListMovie);
        movieAdapter.notifyDataSetChanged();


        if (ListMovie.size() > 0){
            movieAdapter.setData(ListMovie);
            movieAdapter.notifyDataSetChanged();
        }else{
            nodata.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                SelectedDataMovie(data);
            }
        });
        movieHelper.close();
    }



    private void SelectedDataMovie(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.FLAG_EXTRA, movie);
        startActivity(intent);
    }
}
