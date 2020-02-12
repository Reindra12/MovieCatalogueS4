package com.reindra.submission4java.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.reindra.submission4java.R;
import com.reindra.submission4java.activity.DetailActivity;
import com.reindra.submission4java.adapter.MovieAdapter;
import com.reindra.submission4java.model.Movie;
import com.reindra.submission4java.model.MovieModel;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.reindra.submission4java.activity.DetailActivity.FLAG_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> list = new ArrayList<>();
    private ProgressBar progressBar;
    private CardView cardView;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = mView.findViewById(R.id.rv_category);
        progressBar = mView.findViewById(R.id.progressBarmovie);
        showRecyclerView();
        return mView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        movieAdapter = new MovieAdapter(list);
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        MovieModel movieModel =  new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieModel.class);
        movieModel.setMovie();
        showLoading(true);



        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie movie) {

                selectedDataMovie(movie);

            }
        });

        if (getActivity()!=null){
            movieModel.getMovie().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                   if (movies != null) {
                       movieAdapter.setData(movies);
                       showLoading(false);

                   }
                }
            });
        }

    }


    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
    private void selectedDataMovie(Movie movie) {

//        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.FLAG_EXTRA, movie);
        intent.putExtra("status","movie");
        startActivity(intent);
    }
}
