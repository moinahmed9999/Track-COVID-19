package com.moin.covid19tracker.Fragments.Stats.Country;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryActiveStatsFragment extends Fragment {

    public CountryActiveStatsFragment() {
        // Required empty public constructor
    }

    private View mView;
    private AnyChartView chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_country_active_stats, container, false);
        chart = mView.findViewById(R.id.country_confirmed_chart);
        setCharts();
        return mView;
    }

    private void setCharts() {
        chart.setProgressBar(mView.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.yAxis(0).title("Number of Active Cases");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> dataEntries = new ArrayList<>();

        ArrayList<String[]> list1 = Util.getCountryConfirmedList();
        ArrayList<String[]> list2 = Util.getCountryRecoveredList();
        ArrayList<String[]> list3 = Util.getCountryDeathsList();

        if (list1!=null) {
            for (int i=0; i<list1.size();i++) {
                int confirmed = Integer.parseInt(list1.get(i)[1]);
                int recovered = Integer.parseInt(list2.get(i)[1]);
                int deaths = Integer.parseInt(list3.get(i)[1]);
                int active = confirmed - recovered - deaths;
                if (i<list1.size()-2 && active!=0) {
                    dataEntries.add(new ValueDataEntry(list1.get(i)[0],active));
                }
                if (i>=list1.size()-2) {
                    dataEntries.add(new ValueDataEntry(list1.get(i)[0],active));
                }
            }
        }

        Set set = com.anychart.data.Set.instantiate();
        set.data(dataEntries);

        Mapping mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line line = cartesian.line(mapping);
        line.name("Active Cases");
        line.hovered().markers().enabled(true);
        line.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        line.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        line.stroke("3 DeepSkyBlue");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        chart.setChart(cartesian);
    }
}
