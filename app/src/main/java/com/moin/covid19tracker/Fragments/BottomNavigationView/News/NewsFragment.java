package com.moin.covid19tracker.Fragments.BottomNavigationView.News;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moin.covid19tracker.Adapters.IndiaNewsAdapter;
import com.moin.covid19tracker.Models.IndiaNews;
import com.moin.covid19tracker.Models.NewsResult;
import com.moin.covid19tracker.Network.IndiaNewsApiService;
import com.moin.covid19tracker.Network.IndiaNewsApiServiceProvider;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NewsFragment extends Fragment {

    public NewsFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndiaNewsAdapter mAdapter;

    private List<NewsResult> results;
    private IndiaNewsApiService mApiService;

    private ProgressBar progressBar;
    private TextView emptyTextView;
    private RelativeLayout rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        initUI();
        getIndiaNews();
        return mView;
    }

    private void initUI() {
        mRecyclerView = mView.findViewById(R.id.news_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mApiService = IndiaNewsApiServiceProvider.getIndiaNewsApiService();

        progressBar = mView.findViewById(R.id.loading_spinner);
        emptyTextView = mView.findViewById(R.id.empty_view);
        emptyTextView.setText("Loading...");
        rl = mView.findViewById(R.id.rl);
        rl.setVisibility(View.INVISIBLE);
    }

    private void getIndiaNews() {
        if (Util.isAppOnLine(getContext())) {
            mApiService.getIndiaNews().enqueue(new Callback<IndiaNews>() {
                @Override
                public void onResponse(Call<IndiaNews> call, Response<IndiaNews> response) {
                    results = response.body().getResults();

                    mAdapter = new IndiaNewsAdapter(results,getContext());
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    setOnNewsClickListener();

                    rl.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    emptyTextView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<IndiaNews> call, Throwable t) {
                    Toast.makeText(getContext(),"Request Failed !!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"No Internet Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setOnNewsClickListener() {
        mAdapter.setOnNewsClickListener(position-> {
                NewsResult result = results.get(position);
                NavDirections navDirections = NewsFragmentDirections.actionNewsToNewsWebView(
                        result.getNews_link());
                Navigation.findNavController(mView).navigate(navDirections);
        });
    }

}
