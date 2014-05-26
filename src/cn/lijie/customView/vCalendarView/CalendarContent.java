package cn.lijie.customView.vCalendarView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class CalendarContent extends ListView{
	private int monthNum=7;
	private int preMonthNum,proMonthNum;
	private CalendarView.OnItemClickListener cOnItemClickListener;
	
	public CalendarContent(Context context) {
		super(context);
		init(context);
	}

	public CalendarContent(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		CalendarView.initSelectDates();
		setAdapter(new CalendarAdapter(context));
		setDivider(null);
		setFadingEdgeLength(0);
		setVerticalScrollBarEnabled(false);
//		setSelection(preMonthNum);
	}
	
	public void setMonthNum(int preMonthNum,int proMonthNum){
		this.preMonthNum=preMonthNum;
		this.proMonthNum=proMonthNum;
		this.monthNum=preMonthNum+proMonthNum;
		setAdapter(new CalendarAdapter(getContext()));
		setSelection(preMonthNum);
	}
	
	public void setOnDateSelectedListsener(CalendarView.OnItemClickListener cOnItemClickListener){
		this.cOnItemClickListener=cOnItemClickListener;
	}
	
	class CalendarAdapter extends BaseAdapter{
		private Context context;
		
		CalendarAdapter(Context context){
			this.context=context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return monthNum;
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
				convertView=new CalendarView(context);
				((CalendarView)convertView).setOnItemClickListener(cOnItemClickListener);
			}
			((CalendarView)convertView).jumpNMonth(position-preMonthNum);
			if(position%2==0)
				((CalendarView)convertView).setCellBgColor(Color.parseColor("#EBEBEB"), Color.WHITE);
			else
				((CalendarView)convertView).setCellBgColor(Color.WHITE, Color.parseColor("#EBEBEB"));
			convertView.setLayoutParams(new ListView.LayoutParams(LayoutParams.FILL_PARENT, ((CalendarView)convertView).measureHeight()));
			return convertView;
		}
		
	}
}
