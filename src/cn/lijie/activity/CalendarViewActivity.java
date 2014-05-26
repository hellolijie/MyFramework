package cn.lijie.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.lijie.R;
import cn.lijie.customView.vCalendarView.CalendarContent;
import cn.lijie.customView.vCalendarView.CalendarView;
import cn.lijie.customView.vCalendarView.CalendarView.OnItemClickListener;

public class CalendarViewActivity extends Activity{
	private static int NUM=7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_view_layout);
		CalendarContent calendarContent=(CalendarContent) findViewById(R.id.calendarContent);
		calendarContent.setOnDateSelectedListsener(new OnItemClickListener() {
			
			@Override
			public void OnItemClick(Date date) {
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(date);
				Toast.makeText(getApplicationContext(), calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
			}
		});
//		calendarContent.setMonthNum(10, 11);
//		ListView calendarList=(ListView) findViewById(R.id.calendar_list);
//		CalendarView.initSelectDates();
//		calendarList.setAdapter(new CalendarAdapter());
//		calendarList.setSelection(NUM/2);
	}

	class CalendarAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=new CalendarView(getApplicationContext());
			}
			((CalendarView)convertView).jumpNMonth(position);
			if(position%2==0)
				((CalendarView)convertView).setCellBgColor(Color.parseColor("#EBEBEB"), Color.WHITE);
			else
				((CalendarView)convertView).setCellBgColor(Color.WHITE, Color.parseColor("#EBEBEB"));
			convertView.setLayoutParams(new ListView.LayoutParams(LayoutParams.FILL_PARENT, ((CalendarView)convertView).measureHeight()));
			return convertView;
		}
		
	}
}
