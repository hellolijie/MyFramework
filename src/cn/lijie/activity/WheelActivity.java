package cn.lijie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.wheel.test.WheelView;
import android.wheel.test.adapter.ArrayWheelAdapter;
import cn.lijie.R;

public class WheelActivity extends Activity{
	private String[] hours={"00","01","02","04","05","06","07","08","09","10","11","12","13"
			,"14","15","16","17","18","19","20","21","22","23"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel_layout);
		
		WheelView hourChooser=(WheelView) findViewById(R.id.hourChooser);
		WheelView minuChooser=(WheelView) findViewById(R.id.minuChooser);
		
		ArrayWheelAdapter<String> adapter =
            new ArrayWheelAdapter<String>(this, new String[]{"2","33","dd","dd","dd","jj"});
		hourChooser.setViewAdapter(adapter);
		minuChooser.setViewAdapter(adapter);
		hourChooser.setCyclic(true);
		minuChooser.setCyclic(true);
		
		
	}

}
