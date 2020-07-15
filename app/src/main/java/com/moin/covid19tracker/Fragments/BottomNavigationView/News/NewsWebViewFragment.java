package com.moin.covid19tracker.Fragments.BottomNavigationView.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moin.covid19tracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsWebViewFragment extends Fragment {

    private View mView;
    private WebView mWebView;

    public NewsWebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news_web_view, container, false);
        initUI();
        setWebView();
        return mView;
    }

    private void initUI() {
        mWebView = mView.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
    }

    private void setWebView() {
        mWebView.loadUrl(NewsWebViewFragmentArgs.fromBundle(getArguments()).getNewsLink());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
