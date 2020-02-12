package com.reindra.submission4java.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reindra.submission4java.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
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
