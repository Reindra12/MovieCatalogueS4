package com.reindra.favoritemovieapps.fragment.favorite;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reindra.favoritemovieapps.R;
import com.reindra.favoritemovieapps.adapter.TVAdapter;
import com.reindra.favoritemovieapps.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.CONTENT_MOVIE;
import static com.reindra.favoritemovieapps.database.DatabaseContract.MoviesColumns.CONTENT_TV;
import static com.reindra.favoritemovieapps.database.MappingHelper.mapCursorTV;

/**
 * A simple {@link Fragment} subclass.
 */


public class FavTVFragment extends Fragment implements LoadDataTvCallBack {
    private RecyclerView recyclerView;
    private TVAdapter tvAdapter;
    private LinearLayout nodata;
    private static final String EXTRA_STATE = "extra_state";


    public FavTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAdapter = new TVAdapter(getContext());
        nodata = view.findViewById(R.id.view_no_data);
        recyclerView = view.findViewById(R.id.rv_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        FavTVFragment.DataObserver tvObserver = new FavTVFragment.DataObserver(handler, getContext());

        if (getActivity() != null) {
            getActivity().getContentResolver().registerContentObserver(CONTENT_MOVIE, true, tvObserver);
        }
        recyclerView.setAdapter(tvAdapter);
        tvAdapter.setOnItemClickCallBack(new TVAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {


            }
        });
        if (savedInstanceState == null) {
            new FavTVFragment.LoadDataAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movies != null) {
                tvAdapter.setData(movies);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, tvAdapter.getFavoritetv());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsync(getContext(), this).execute();
    }


    @Override
    public void preExecute() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<Movie> movies = mapCursorTV(cursor);
        if (movies.size() > 0) {
            tvAdapter.setData(movies);
        } else {
            nodata.setVisibility(View.VISIBLE);
        }
    }

    /*public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }*/

    private static class LoadDataAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakReference;
        private final WeakReference<LoadDataTvCallBack> weakcallback;

        private LoadDataAsync(Context context, LoadDataTvCallBack callback) {
            weakReference = new WeakReference<>(context);
            weakcallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakcallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakcallback.get().postExecute(cursor);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakReference.get();
            return context.getContentResolver().query(CONTENT_TV, null, null, null, null);
        }
    }

    public class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}

interface LoadDataTvCallBack {
    void preExecute();

    void postExecute(Cursor cursor);
}


