package baby.yy.com.babyhit;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

public class BabyHitFragment extends Fragment {

    //
    boolean isInit = false;
    //
    TextView tvResult1;
    TextView tvResult2;
    CircleButton addBtn;
    Button resetBtn;
    int hitCounter;
    long startTime;
    LineChart chart;
    List<Entry> hitHis = new ArrayList<>();
    View view;
    Context ctx;

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hit);
//
//        tvResult1 = (TextView) findViewById(R.id.textView1);
//        addBtn = (CircleButton) findViewById(R.id.button1);
//        resetBtn = (Button) findViewById(R.id.button2);
//        init(this);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if(view == null) {
            view = inflater.inflate(R.layout.baby_hit, container, false);
            init();
        }
        return view;
    }

    private void init(){
        tvResult1 = (TextView) view.findViewById(R.id.textView1);
        addBtn = (CircleButton) view.findViewById(R.id.button1);
        resetBtn = (Button) view.findViewById(R.id.button2);

        hitCounter = 0;//设置初始数字
        startTime = 0;
        addBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(startTime == 0){
                    startTime = System.currentTimeMillis();
                } else {
                    hitCounter++;
                }
                if(System.currentTimeMillis() - startTime > 1000) {
                    int hit_times = (int)(hitCounter * 60000 / (System.currentTimeMillis() - startTime));
                    tvResult1.setText("" + hit_times);
                    hitHis.add(new Entry(hitHis.size(), hit_times));
                    notifyDataSetChanged(chart, hitHis, 0);
                    tvResult1.setTextSize(60);
                } else {
                    tvResult1.setText("" + hitCounter);
                    tvResult1.setTextSize(20);
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                hitCounter = 0;
                startTime = 0;
                tvResult1.setText("" + 0);
                tvResult1.setTextSize(60);
                hitHis = new ArrayList<>();
            }
        });

        chart = (LineChart) view.findViewById(R.id.chart);
        chart = initChart(chart);

    }
    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart) {
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        //xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据偏移量
        xAxis.setYOffset(-12);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        //yAxis.setDrawGridLines(false);
        //yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(30);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
        // x轴执行动画
        //chart.animateX(2000);
        chart.invalidate();
        return chart;
    }


    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            //lineDataSet.setColor(Color.parseColor("#FFFFFF"));
            lineDataSet.setColor(Color.RED);
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(false);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(false);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }


    /**
     * 更新图表
     *
     * @param chart     图表
     * @param values    数据
     * @param valueType 数据类型
     */
    public static void notifyDataSetChanged(LineChart chart, List<Entry> values,
                                            final int valueType) {
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ""+(int)value;
            }
        });

        chart.invalidate();
        setChartData(chart, values);
    }



}
