package twpvsystem.tongwei.com.twpvsystem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import twpvsystem.tongwei.com.twpvsystem.R;
import twpvsystem.tongwei.com.twpvsystem.activity.ChartActivity;

public class ChartOneFragment extends Fragment {

	private LineChartView chart;
	private LineChartData data;
	private int numberOfLines = 1;
	private int maxNumberOfLines = 4;
	private int numberOfPoints = 12;
	float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLines = true;
	private boolean hasPoints = true;
	private ValueShape shape = ValueShape.CIRCLE;
	private boolean isFilled = false;
	private boolean hasLabels = false;
	private boolean isCubic = false;
	private boolean hasLabelForSelected = false;
	private boolean pointsHaveDifferentColor;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.chart_one, container, false);
		init(v);
		return v;
	}

	private void init(View v) {
		chart = (LineChartView) v.findViewById(R.id.chart);
		chart.setOnValueTouchListener(new ValueTouchListener());

		// Generate some random values.
		generateValues();

		generateData();

		// Disable viewport recalculations, see toggleCubic() method for more info.
		chart.setViewportCalculationEnabled(false);

		resetViewport();
	}

	private void generateValues() {
		for (int i = 0; i < maxNumberOfLines; ++i) {
			for (int j = 0; j < numberOfPoints; ++j) {
				randomNumbersTab[i][j] = (float) Math.random() * 100f;
			}
		}
	}

	private void generateData() {

		List<Line> lines = new ArrayList<Line>();
		for (int i = 0; i < numberOfLines; ++i) {

			List<PointValue> values = new ArrayList<PointValue>();
			for (int j = 0; j < numberOfPoints; ++j) {
				values.add(new PointValue(j, randomNumbersTab[i][j]));
			}

			Line line = new Line(values);
			line.setColor(ChartUtils.COLORS[i]);
			line.setShape(shape);
			line.setCubic(isCubic);
			line.setFilled(isFilled);
			line.setHasLabels(hasLabels);
			line.setHasLabelsOnlyForSelected(hasLabelForSelected);
			line.setHasLines(hasLines);
			line.setHasPoints(hasPoints);
			if (pointsHaveDifferentColor){
				line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
			}
			lines.add(line);
		}

		data = new LineChartData(lines);

		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("Axis X");
				axisY.setName("Axis Y");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}

		data.setBaseValue(Float.NEGATIVE_INFINITY);
		chart.setLineChartData(data);

	}

	private void resetViewport() {
		// Reset viewport height range to (0,100)
		final Viewport v = new Viewport(chart.getMaximumViewport());
		v.bottom = 0;
		v.top = 100;
		v.left = 0;
		v.right = numberOfPoints - 1;
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}

	private class ValueTouchListener implements LineChartOnValueSelectListener {

		@Override
		public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
			Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub

		}

	}
	
}
