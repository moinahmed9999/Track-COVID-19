package com.moin.covid19tracker.Fragments.Stats.India;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.moin.covid19tracker.R;
import com.moin.covid19tracker.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndiaDailyConfirmedFragment extends Fragment {

    public IndiaDailyConfirmedFragment() {
        // Required empty public constructor
    }

    private View mView;
    private AnyChartView chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_india_daily_confirmed, container, false);
        chart = mView.findViewById(R.id.india_confirmed_chart);
        setCharts();
        return mView;
    }

    private void setCharts() {
        chart.setProgressBar(mView.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> dataEntries = new ArrayList<>();

        List<String[]> time_series = Util.getTimeSeries();
        int size=time_series.size();

        for (int i=size-60; i<size;i++) {
            int x = Integer.parseInt(time_series.get(i)[5]);
            if (x!=0) {
                dataEntries.add(new ValueDataEntry(time_series.get(i)[0],x));
            }
        }

        Column column = cartesian.column(dataEntries);
        column.normal().fill("#ff093c");
        column.normal().stroke("#ff093c");

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.yAxis(0).title("Number of Daily Confirmed Cases");
        cartesian.xAxis(0).title("");

        chart.setChart(cartesian);
    }
}
