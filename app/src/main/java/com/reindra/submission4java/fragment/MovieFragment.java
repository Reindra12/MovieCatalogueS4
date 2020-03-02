package com.reindra.submission4java.fragment;


import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reindra.submission4java.R;
import com.reindra.submission4java.activity.DetailActivity;
import com.reindra.submission4java.activity.NotificationActivity;
import com.reindra.submission4java.adapter.MovieAdapter;
import com.reindra.submission4java.model.Movie;
import com.reindra.submission4java.model.MovieModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> list = new ArrayList<>();
    private ProgressBar progressBar;
    LinearLayout nodata;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View mView = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = mView.findViewById(R.id.rv_category);
        progressBar = mView.findViewById(R.id.progressBarmovie);
        nodata = mView.findViewById(R.id.view_no_data);
        showRecyclerView();
        return mView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieAdapter = new MovieAdapter(list);
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        MovieModel movieModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieModel.class);
        movieModel.setMovie();
        showLoading(true);

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie movie) {

                selectedDataMovie(movie);
            }
        });

        if (getActivity() != null) {
            movieModel.getMovie().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    if (movies != null) {
                        movieAdapter.setData(movies);
                        showLoading(false);
                    } else {
                        nodata.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        search(menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (item.getItemId() == R.id.notif) {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    private void search(Menu menu) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final MovieModel movieModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieModel.class);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.searchmovie));
            searchView.setFocusable(true);
            searchView.setIconifiedByDefault(false);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.setIconified(false);
                    searchView.setQuery(query, false);
                    searchView.clearFocus();
                    movieModel.searchdatamovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals("")) {
                        movieModel.searchdatamovie(newText);
                    }
                    return true;
                }
            });
            MenuItem search = menu.findItem(R.id.search);
            search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    showRecyclerView();
                    return true;
                }
            });
        }
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void selectedDataMovie(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FLAG_EXTRA, movie);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.goup, R.anim.goup).toBundle();
        getActivity().startActivity(intent, bundle);
    }
}
