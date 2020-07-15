package com.moin.covid19tracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

public class CountryStatsActivity extends AppCompatActivity {

    private TextView country_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_stats);
        country_name = findViewById(R.id.country_name);
        country_name.setText(Util.getCountryName());
    }
}
