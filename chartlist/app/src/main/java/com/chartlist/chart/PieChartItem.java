package com.chartlist.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chartlist.ChartItem;
import com.chartlist.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PieChartItem extends ChartItem {

    private Typeface mTf;
    private SpannableString mCenterText;

    public PieChartItem(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        mCenterText = generateCenterText();
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @Override
    public View getView(int position, View convertView,final Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);
            holder.chart = (PieChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setDescription("");
        holder.chart.setHoleRadius(52f);
        holder.chart.setTransparentCircleRadius(57f);
        holder.chart.setCenterText(mCenterText);
        holder.chart.setCenterTextTypeface(mTf);
        holder.chart.setCenterTextSize(9f);
        holder.chart.setUsePercentValues(true);
        holder.chart.setExtraOffsets(5, 10, 50, 10);

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.WHITE);
        // set data
        holder.chart.setData((PieData) mChartData);

        Legend l = holder.chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateY(900);
        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Toast.makeText(c, "第一" + e + "\n第二个" + dataSetIndex + "\n第三个" + h, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return convertView;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("MPAndroidChart\ncreated by\nPhilipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        s.setSpan(new RelativeSizeSpan(.9f), 14, 25, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, 25, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 25, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 25, s.length(), 0);
        return s;
    }

    private static class ViewHolder {
        PieChart chart;
    }
}
