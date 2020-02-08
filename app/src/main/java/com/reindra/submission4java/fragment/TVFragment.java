package com.reindra.submission4java.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.reindra.submission4java.R;
import com.reindra.submission4java.activity.DetailActivity;
import com.reindra.submission4java.adapter.TVAdapter;
import com.reindra.submission4java.model.Movie;
import com.reindra.submission4java.model.MovieModel;
import com.reindra.submission4java.model.TVModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    private RecyclerView rvtv;
    private ArrayList<Movie> list = new ArrayList<>();
    private ProgressBar progressBar;
    private static TVAdapter tvAdapter;


    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_tv, container, false);
        progressBar = mView.findViewById(R.id.progressBartv);
        rvtv = mView.findViewById(R.id.rv_tv);
        showRecyclerView();
        return mView;
    }

    private void showRecyclerView() {
        rvtv.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvAdapter = new TVAdapter(list);
        tvAdapter.notifyDataSetChanged();
        rvtv.setAdapter(tvAdapter);

        TVModel tvModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVModel.class);
        tvModel.setTV();
        showloading(true);

        tvAdapter.setOnItemClickCallBack(new TVAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedMovie(data);
            }
        });
        if (getActivity() != null) {
            tvModel.getMovies().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> moviesItem) {
                    if (moviesItem != null) {
                        tvAdapter.setData(moviesItem);
                        showloading(false);
                    }
                }
            });
        }
    }


    private void showloading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);

        }
    }
    private void showSelectedMovie(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.FLAG_EXTRA, movie);
        startActivity(intent);
    }

}


