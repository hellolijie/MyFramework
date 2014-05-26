package cn.lijie.customView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

public class ScrollCanlendar extends View{
	private Surface surface;
	private Date curDate,selectedStartDate,selectedEndDate,today;
	private Calendar calendar;
	private Map<Integer,Month> month;

	private Scroller mScroller;
	private int lastX,lastY;
	private boolean isStart;
	
	private VelocityTracker velocityTracker;
	public ScrollCanlendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public ScrollCanlendar(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		isStart=true;
		curDate = selectedStartDate = selectedEndDate = today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		setBackgroundColor(surface.bgColor);
		mScroller=new Scroller(context);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
//		Log.i("tag", "scroll");
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("tag", "onTouchEvent");
		super.onTouchEvent(event);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			
//			if(velocityTracker==null)
//				velocityTracker=VelocityTracker.obtain();
			
			lastX=(int) event.getRawX();
			lastY=(int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
//			velocityTracker.addMovement(event);
			scrollBy(0, lastY-(int)event.getRawY());
//			lastX=(int) event.getRawX();
			lastY=(int) event.getRawY();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
//			velocityTracker.computeCurrentVelocity(1000);
//			mScroller.startScroll(getScrollX(), getScrollY(), getScrollX()+60, getScrollX()+70,3000);
//			mScroller.fling(getScrollX(), getScrollY(), (int)-velocityTracker.getXVelocity(), (int)-velocityTracker.getYVelocity(), -500, 0, -700, 0);
//			scrollTo(0, 0);
			invalidate();
//			velocityTracker.recycle();
//			velocityTracker=null;
			break;
		}
		
		
		return true;
	}



	@Override
	public void computeScroll() {
		//先判断mScroller滚动是否完成  
        if (mScroller.computeScrollOffset()) {  
          
            //这里调用View的scrollTo()完成实际的滚动  
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
              
            //必须调用该方法，否则不一定能看到滚动效果  
            postInvalidate();  
        }  
		super.computeScroll();
	}
	
	//初始当前年
	private void prepareMonth(){
		month=new HashMap<Integer, ScrollCanlendar.Month>();
		Calendar calendar=Calendar.getInstance();
		calendar.set(2014, 0, 1);
		int startDayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
		for(int i=0;i<12;i++){
			Month m=new Month(2014, i+1,1,startDayOfWeek);
			if(i%2==0)
				m.setBgColor(Color.GRAY);
			else
				m.setBgColor(Color.LTGRAY);
			month.put(i, m);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		if(isStart){
			isStart=false;
			surface.init();
			prepareMonth();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for(int i=0;i<12;i++)
			month.get(i).drawMonth(canvas);
		super.onDraw(canvas);
	}

	// 获得当前应该显示的年月
	public String getYearAndmonth() {
		calendar.setTime(curDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
//		int month = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + surface.monthText[month];
	}


	private class Surface {
		public float density;
		public int width; // 整个控件的宽度
		public int height; // 整个控件的高度
		public float monthHeight; // 显示月的高度
		//public float monthChangeWidth; // 上一月、下一月按钮宽度
		public float weekHeight; // 显示星期的高度
		public float cellWidth; // 日期方框宽度
		public float cellHeight; // 日期方框高度	
		public float borderWidth;
		public int bgColor = Color.parseColor("#FFFFFF");
		private int textColor = Color.BLACK;
		//private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#666666");
		private int borderColor = Color.parseColor("#CCCCCC");
		public int todayNumberColor = Color.RED;
		public int cellDownColor = Color.parseColor("#CCFFFF");
		public int cellSelectedColor = Color.parseColor("#99CCFF");
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Path boxPath; // 边框路径
		//public Path preMonthBtnPath; // 上一月按钮三角形
		//public Path nextMonthBtnPath; // 下一月按钮三角形
		public String[] weekText = { "Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		public String[] monthText = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		   
		public void init() {
			width=getWidth();
			height=getHeight()/3;
			float temp = height / 7f;
			monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);
			//monthChangeWidth = monthHeight * 1.5f;
//			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			cellHeight = (height - monthHeight - weekHeight) / 6f;
			cellWidth = width / 7f;
			borderPaint = new Paint();
			borderPaint.setColor(borderColor);
			borderPaint.setStyle(Paint.Style.STROKE);
			borderWidth = (float) (0.5 * density);
			// Log.d(TAG, "borderwidth:" + borderWidth);
			borderWidth = borderWidth < 1 ? 1 : borderWidth;
			borderPaint.setStrokeWidth(borderWidth);
			monthPaint = new Paint();
			monthPaint.setColor(textColor);
			monthPaint.setAntiAlias(true);
			float textSize = cellHeight * 0.4f;
//			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
			weekPaint = new Paint();
			weekPaint.setColor(textColor);
			weekPaint.setAntiAlias(true);
			float weekTextSize = weekHeight * 0.6f;
			weekPaint.setTextSize(weekTextSize);
			weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
			float cellTextSize = cellHeight * 0.5f;
			datePaint.setTextSize(cellTextSize);
			datePaint.setTypeface(Typeface.DEFAULT_BOLD);
			boxPath = new Path();
			//boxPath.addRect(0, 0, width, height, Direction.CW);
			//boxPath.moveTo(0, monthHeight);
			boxPath.rLineTo(width, 0);
			boxPath.moveTo(0, monthHeight + weekHeight);
			boxPath.rLineTo(width, 0);
			for (int i = 1; i < 6; i++) {
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
				boxPath.rLineTo(width, 0);
				boxPath.moveTo(i * cellWidth, monthHeight);
				boxPath.rLineTo(0, height - monthHeight);
			}
			boxPath.moveTo(6 * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);
			//preMonthBtnPath = new Path();
			//int btnHeight = (int) (monthHeight * 0.6f);
			//preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
			//preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
			//preMonthBtnPath.rLineTo(0, btnHeight);
			//preMonthBtnPath.close();
			//nextMonthBtnPath = new Path();
			//nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
			//		monthHeight / 2f);
			//nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
			//nextMonthBtnPath.rLineTo(0, btnHeight);
			//nextMonthBtnPath.close();
			monthChangeBtnPaint = new Paint();
			monthChangeBtnPaint.setAntiAlias(true);
			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			monthChangeBtnPaint.setColor(btnColor);
			cellBgPaint = new Paint();
			cellBgPaint.setAntiAlias(true);
			cellBgPaint.setStyle(Paint.Style.FILL);
			cellBgPaint.setColor(cellSelectedColor);
		}
	}
	
	class Month{
		private Calendar mCalendar;
		private float startY;
		private int bgColor;
		private int year,month;
		private int position;
		
		Month(int year,int month,int positon,int startDayOfWeek){
			this.year=year;
			this.month=month;
			this.position=position;
			mCalendar=Calendar.getInstance();
			mCalendar.set(year, month-1, 1);
//			calendar.setTime(new Date());
			init(startDayOfWeek);
		}
		
		private void init(int startDayOfWeek){
			int y = (startDayOfWeek+mCalendar.get(Calendar.DAY_OF_YEAR)-2)/7;
			startY = y * surface.cellHeight ;
			Log.i("tag", "y--"+y+"dayy--"+mCalendar.get(Calendar.DAY_OF_YEAR)+"sh"+surface.cellHeight);
		}
		
		
		
		public int getBgColor() {
			return bgColor;
		}

		public void setBgColor(int bgColor) {
			this.bgColor = bgColor;
		}

		public void drawMonth(Canvas canvas){
			int curMonth=mCalendar.get(Calendar.MONTH);
			int startDayOfWeek=mCalendar.get(Calendar.DAY_OF_WEEK);
			Log.i("tag", startDayOfWeek+"-----"+mCalendar.get(Calendar.YEAR)+"----"+mCalendar.get(Calendar.MONTH)+"-----"+mCalendar.get(Calendar.DAY_OF_MONTH));
			while(curMonth==mCalendar.get(Calendar.MONTH)){
				drawCellBg(canvas, mCalendar, startDayOfWeek, bgColor);
				drawCellText(canvas,mCalendar,startDayOfWeek,Color.BLACK);
				mCalendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			mCalendar.set(year, month, 1);
		}
		
//		public void isClicked(){
//			
//		}
		
		private void drawCellText(Canvas canvas, Calendar calendar,int startDayOfWeek, int color) {
			int x = calendar.get(Calendar.DAY_OF_WEEK);
			int y = (startDayOfWeek+calendar.get(Calendar.DAY_OF_MONTH)-2)/7;
			String text=calendar.get(Calendar.DAY_OF_MONTH)+"";
			surface.datePaint.setColor(color);
//			float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
//					* surface.cellHeight + surface.cellHeight * 3 / 4f;
			Log.i("tag", startY+"----startY");
			float cellY = y * surface.cellHeight + surface.cellHeight * 3 / 4f+startY;
			float cellX = (surface.cellWidth * (x - 1))
					+ (surface.cellWidth - surface.datePaint.measureText(text))
					/ 2f;
			canvas.drawText(text, cellX, cellY, surface.datePaint);
		}

		private void drawCellBg(Canvas canvas, Calendar calendar,int startDayOfWeek, int color) {
			int x = calendar.get(Calendar.DAY_OF_WEEK);
			int y = (startDayOfWeek+calendar.get(Calendar.DAY_OF_MONTH)-2)/7;
			surface.cellBgPaint.setColor(color);
			float left = surface.cellWidth * (x - 1) + surface.borderWidth;
//			float top =surface.monthHeight + surface.weekHeight + (y - 1)
//					* surface.cellHeight + surface.borderWidth+startY;
			float top = y * surface.cellHeight + surface.borderWidth+startY;
			canvas.drawRect(left, top, left + surface.cellWidth
					- surface.borderWidth, top + surface.cellHeight
					- surface.borderWidth, surface.cellBgPaint);
		}
	}
}
