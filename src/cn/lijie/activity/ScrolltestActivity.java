package cn.lijie.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lijie.R;
import cn.lijie.utils.GeneralUtils;

public class ScrolltestActivity extends Activity{
	private int lastPosition,curPosition;
	private int pageWidth;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll_test_layout);
		pageWidth=GeneralUtils.getScreenSize(getApplicationContext()).screenWidth;
		
		final LinearLayout moveContent=(LinearLayout) findViewById(R.id.moveContent);
		
		ViewPager viewPager=(ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new PgAdapter());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				curPosition=arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if(curPosition!=lastPosition&&arg1==0){
					lastPosition=curPosition;
				}
				int position=(int) (lastPosition*pageWidth+pageWidth* arg1);
				moveContent.scrollTo(position, moveContent.getTop());
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
	}

	class PgAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView textView=new TextView(getApplicationContext());
			textView.setWidth(500);
			textView.setHeight(500);
			textView.setBackgroundColor(Color.BLACK);
			textView.setText(position+"");
			textView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
//			textView.d
//			container.addView(textView);
			textView.setContentDescription("123");
			return textView;
		}

		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
		}
    }
}
