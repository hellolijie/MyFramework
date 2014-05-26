package cn.lijie.customView.vCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * �����ؼ� ���ܣ���õ�ѡ����������
 * 
 */
public class CalendarView extends View implements View.OnTouchListener {
	private static HashMap<String,int[]> selectDates;
	
	
	private final static String TAG = "anCalendar";
	private Date selectedStartDate;
	private Date selectedEndDate;
	private Date curDate; // ��ǰ������ʾ����
	private Date today; // ���������������ʾ��ɫ
	private Date downDate; // ��ָ����״̬ʱ��ʱ����
	private Date showFirstDate, showLastDate; // ������ʾ�ĵ�һ�����ں����һ������
	private int downIndex; // ���µĸ�������
	private Calendar calendar;
	private Surface surface;
	private int[] date = new int[42]; // ������ʾ����
	private int curStartIndex, curEndIndex; // ��ǰ��ʾ��������ʼ������
	
	private int dayNum;
	private int viewHeight;
	private int[] downIndexes= new int[42];
	private int todayIndex = -1;
	
	private long todayTime;
	//private boolean completed = false; // Ϊfalse��ʾֻѡ���˿�ʼ���ڣ�true��ʾ��������Ҳѡ����
	//���ؼ����ü����¼�
	private OnItemClickListener onItemClickListener;
	
	public static void initSelectDates(){
		selectDates=new HashMap<String, int[]>();
	}
	
	public CalendarView(Context context) {
		super(context);
		init();
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		curDate = selectedStartDate = selectedEndDate = today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		todayTime=today.getTime();
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		
		setBackgroundColor(surface.bgColor);
		setOnTouchListener(this);
		measureHeight();
//		for(int i=0;i<downIndexes.length;i++)
//			downIndexes[i]=0;
	}

	public int measureHeight(){
		calendar.setTime(curDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		int monthDay=dayInWeek+calendar.get(Calendar.DAY_OF_MONTH)-1;
		
		if(monthDay<35){
			dayNum=28;
			viewHeight=360;
		}
		else{
			dayNum=35;
			viewHeight=450;
		}
		return viewHeight;
	}
	
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////		surface.width = getResources().getDisplayMetrics().widthPixels;
////		surface.height = (int) (getResources().getDisplayMetrics().heightPixels*2/5);
////		surface.height = 350;
////		if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
////			surface.width = View.MeasureSpec.getSize(widthMeasureSpec);
////		}
////		if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
////			surface.height = View.MeasureSpec.getSize(heightMeasureSpec);
////		}
//		
//		calendar.setTime(curDate);
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		
////		calculateDate();
//		
////		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
////				View.MeasureSpec.EXACTLY);
////		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(viewHeight,
////				View.MeasureSpec.EXACTLY);
////		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, "[onLayout] changed:"
				+ (changed ? "new size" : "not change") + " left:" + left
				+ " top:" + top + " right:" + right + " bottom:" + bottom);
		surface.width = getWidth();
		surface.height = 450;
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		// ����
//		canvas.drawPath(surface.boxPath, surface.borderPaint);
		// ����
		//String monthText = getYearAndmonth();
		//float textWidth = surface.monthPaint.measureText(monthText);
		//canvas.drawText(monthText, (surface.width - textWidth) / 2f,
		//		surface.monthHeight * 3 / 4f, surface.monthPaint);
		// ��һ��/��һ��
		//canvas.drawPath(surface.preMonthBtnPath, surface.monthChangeBtnPaint);
		//canvas.drawPath(surface.nextMonthBtnPath, surface.monthChangeBtnPaint);
		// ����
//		float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
		// ���ڱ���
//		surface.cellBgPaint.setColor(surface.textColor);
//		canvas.drawRect(surface.weekHeight, surface.width, surface.weekHeight, surface.width, surface.cellBgPaint);
//		for (int i = 0; i < surface.weekText.length; i++) {
//			float weekTextX = i
//					* surface.cellWidth
//					+ (surface.cellWidth - surface.weekPaint
//							.measureText(surface.weekText[i])) / 2f;
//			canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
//					surface.weekPaint);
//		}
		
		// ��������
		calculateDate();
		// write date number
		// today index
		todayIndex = -1;
		calendar.setTime(curDate);
		String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
				+ calendar.get(Calendar.MONTH);
		calendar.setTime(today);
		String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
				+ calendar.get(Calendar.MONTH);
		if (curYearAndMonth.equals(todayYearAndMonth)) {
			int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
			todayIndex = curStartIndex + todayNumber - 1;
		}
		// ����״̬��ѡ��״̬����ɫ
		drawDownOrSelectedBg(canvas);
//		for (int i = 0; i < 42; i++) {
		for (int i = 0; i < dayNum; i++) {
			int color = surface.textColor;
//			if (isLastMonth(i)) {
//				color = surface.lastMonthTextColor;
//			} else if (isNextMonth(i)) {
//				color = surface.borderColor;
//			}
//			if (todayIndex != -1 && i == todayIndex) {
//				color = surface.todayNumberColor;
//			}
			color=surface.textColor;
			drawCellText(canvas, i, date[i] + "", color);
		}
		super.onDraw(canvas);
	}

	private void calculateDate() {
		calendar.setTime(curDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "day in week:" + dayInWeek);
		int monthStart = dayInWeek;
//		if (monthStart == 1) {
//			monthStart = 8;
//		}
		monthStart -= 1;  //����Ϊ��ͷ-1��������һΪ��ͷ-2
		curStartIndex = monthStart;
		date[monthStart] = 1;
		// last month
		if (monthStart > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
			for (int i = monthStart - 1; i >= 0; i--) {
				date[i] = dayInmonth;
				dayInmonth--;
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[0]);
		}
		showFirstDate = calendar.getTime();
		// this month
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		// Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
		// calendar.get(Calendar.DAY_OF_MONTH));
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < monthDay; i++) {
			date[monthStart + i] = i + 1;
		}
		curEndIndex = monthStart + monthDay;
//		if(curEndIndex<35){
//			dayNum=28;
//			viewHeight=(int) (surface.height-surface.height*0.2f);
//		}
//		else{
//			dayNum=35;
//			viewHeight=surface.height;
//		}
			
		// next month
		for (int i = monthStart + monthDay; i < 42; i++) {
			date[i] = i - (monthStart + monthDay) + 1;
		}
		if (curEndIndex < 42) {
			// ��ʾ����һ�µ�
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
		showLastDate = calendar.getTime();
	}

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param text
	 */
	private void drawCellText(Canvas canvas, int index, String text, int color) {
		if(downIndexes[index]==1){
			color=Color.WHITE;
			surface.monthTextPaint.setColor(Color.WHITE);
		}
		else{
			surface.monthTextPaint.setColor(surface.textColor);
		}
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.datePaint.setColor(color);
		
		if(text.length()==1)
			text="0"+text;
		
		float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.cellHeight * 4 / 6f;
		float cellX = (surface.cellWidth * (x - 1))
				+ (surface.cellWidth - surface.datePaint.measureText(text))
				/ 2f;
		if(!text.equals("01"))
			canvas.drawText(text, cellX, cellY, surface.datePaint);
		else{
			calendar.setTime(curDate);
			String monthText=surface.monthText[calendar.get(Calendar.MONTH)];
//			float mCellX=(surface.cellWidth * (x - 1)) + (surface.cellWidth - surface.datePaint.measureText(monthText))/ 2f;
			canvas.drawText(monthText, cellX, cellY-surface.textSize, surface.monthTextPaint);
			canvas.drawText(text, cellX, cellY, surface.datePaint);
		}
	}

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellBg(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.borderWidth;
		canvas.drawRect(left, top, left + surface.cellWidth
				- surface.borderWidth, top + surface.cellHeight
				- surface.borderWidth, surface.cellBgPaint);
	}

	private void drawDownOrSelectedBg(Canvas canvas) {
		// down and not up
//		if (downDate != null) {
//			drawCellBg(canvas, downIndex, surface.cellDownColor);
			for(int i=0;i<downIndexes.length;i++){
				if(downIndexes[i]==1)
					drawCellBg(canvas, i, surface.cellSelectedColor);
				else{
					if(isLastMonth(i)){
						drawCellBg(canvas, i,surface.lastMonthCellbgColor);
					}
					else if(todayIndex != -1 && i == todayIndex){
						drawCellBg(canvas, i, surface.cellDownColor);
					}
					else{
						drawCellBg(canvas, i,surface.curMonthCellbgColor);
					}
				}
			}
//		}
		// selected bg color
//		if (!selectedEndDate.before(showFirstDate)
//				&& !selectedStartDate.after(showLastDate)) {
//			int[] section = new int[] { -1, -1 };
//			calendar.setTime(curDate);
//			calendar.add(Calendar.MONTH, -1);
//			findSelectedIndex(0, curStartIndex, calendar, section);
//			if (section[1] == -1) {
//				calendar.setTime(curDate);
//				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
//			}
//			if (section[1] == -1) {
//				calendar.setTime(curDate);
//				calendar.add(Calendar.MONTH, 1);
//				findSelectedIndex(curEndIndex, 42, calendar, section);
//			}
//			if (section[0] == -1) {
//				section[0] = 0;
//			}
//			if (section[1] == -1) {
//				section[1] = 41;
//			}
//			for (int i = section[0]; i <= section[1]; i++) {
//				drawCellBg(canvas, i, surface.cellSelectedColor);
//			}
//		}
	}

	private void findSelectedIndex(int startIndex, int endIndex,
			Calendar calendar, int[] section) {
		for (int i = startIndex; i < endIndex; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, date[i]);
			Date temp = calendar.getTime();
			// Log.d(TAG, "temp:" + temp.toLocaleString());
			if (temp.compareTo(selectedStartDate) == 0) {
				section[0] = i;
			}
			if (temp.compareTo(selectedEndDate) == 0) {
				section[1] = i;
				return;
			}
		}
	}

	public Date getSelectedStartDate() {
		return selectedStartDate;
	}

	public Date getSelectedEndDate() {
		return selectedEndDate;
	}

	private boolean isLastMonth(int i) {
		if (i < curStartIndex) {
			return true;
		}
		return false;
	}

	private boolean isNextMonth(int i) {
		if (i >= curEndIndex) {
			return true;
		}
		return false;
	}

	private int getXByIndex(int i) {
		return i % 7 + 1; // 1 2 3 4 5 6 7
	}

	private int getYByIndex(int i) {
		return i / 7 + 1; // 1 2 3 4 5 6
	}

	// ��õ�ǰӦ����ʾ������
	public String getYearAndmonth() {
		calendar.setTime(curDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
//		int month = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + surface.monthText[month];
	}
	
	//��һ��
	public String clickLeftMonth(){
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		curDate = calendar.getTime();
		invalidate();
		return getYearAndmonth();
	}
	//��һ��
	public String clickRightMonth(){
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		curDate = calendar.getTime();
		invalidate();
		return getYearAndmonth();
	}

	//��n����
	public String jumpNMonth(int n){
//		init();
		calendar.setTime(today);
		calendar.add(Calendar.MONTH, n);
		curDate = calendar.getTime();
		if(selectDates.get(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH))!=null){
			downIndexes=selectDates.get(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH));
			Log.i("tag", calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH));
		}
		else{
			downIndexes=new int[42];
			for(int i=0;i<downIndexes.length;i++)
				downIndexes[i]=0;
			selectDates.put(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH), downIndexes);
		}
		measureHeight();
		invalidate();
		return getYearAndmonth();
	}
	
	private void setSelectedDateByCoor(float x, float y) {
		// change month
//		if (y < surface.monthHeight) {
//			// pre month
//			if (x < surface.monthChangeWidth) {
//				calendar.setTime(curDate);
//				calendar.add(Calendar.MONTH, -1);
//				curDate = calendar.getTime();
//			}
//			// next month
//			else if (x > surface.width - surface.monthChangeWidth) {
//				calendar.setTime(curDate);
//				calendar.add(Calendar.MONTH, 1);
//				curDate = calendar.getTime();
//			}
//		}
		// cell click down
		if (y > surface.monthHeight + surface.weekHeight) {
			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
			int n = (int) (Math
					.floor((y - (surface.monthHeight + surface.weekHeight))
							/ Float.valueOf(surface.cellHeight)) + 1);
			downIndex = (n - 1) * 7 + m - 1;
			
			if(downIndexes[downIndex]==0)
				downIndexes[downIndex]=1;
			else
				downIndexes[downIndex]=0;
//			selectDates.put(calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.DATE), new int[](downIndexes));
			Log.i("tag", calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.DATE)+"100000");
			
			Log.d(TAG, "downIndex:" + downIndex);
			calendar.setTime(curDate);
			if (isLastMonth(downIndex)) {
				calendar.add(Calendar.MONTH, -1);
			} else if (isNextMonth(downIndex)) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
			downDate = calendar.getTime();
		}
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			setSelectedDateByCoor(event.getX(), event.getY());
			if (downDate != null) {
//				if (!completed) {
//					if (downDate.before(selectedStartDate)) {
//						selectedEndDate = selectedStartDate;
//						selectedStartDate = downDate;
//					} else {
//						selectedEndDate = downDate;
//					}
//					completed = true;
//				} else {
//					selectedStartDate = selectedEndDate = downDate;
//					completed = false;
//				}
				selectedStartDate = selectedEndDate = downDate;
				//��Ӧ�����¼�
				if(onItemClickListener!=null)
					onItemClickListener.OnItemClick(selectedStartDate);
				// Log.d(TAG, "downdate:" + downDate.toLocaleString());
				//Log.d(TAG, "start:" + selectedStartDate.toLocaleString());
				//Log.d(TAG, "end:" + selectedEndDate.toLocaleString());
				Log.i("tag", downDate.getDate()+"_"+downDate.getMonth());
				downDate = null;
				invalidate();
			}
			break;
		}
		return true;
	}
	
	//���ؼ����ü����¼�
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener =  onItemClickListener;
	}
	//�����ӿ�
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}

	/**
	 * 
	 * 1. ���ֳߴ� 2. ������ɫ����С 3. ��ǰ���ڵ���ɫ��ѡ���������ɫ
	 */
	private class Surface {
		public float density;
		public int width; // �����ؼ��Ŀ��
		public int height; // �����ؼ��ĸ߶�
		public float monthHeight; // ��ʾ�µĸ߶�
		//public float monthChangeWidth; // ��һ�¡���һ�°�ť���
		public float weekHeight; // ��ʾ���ڵĸ߶�
		public float cellWidth; // ���ڷ�����
		public float cellHeight; // ���ڷ���߶�	
		public float borderWidth;
		public int bgColor = Color.parseColor("#FFFFFF");
		private int textColor = Color.parseColor("#5F5F5F");
		//private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#666666");
		private int borderColor = Color.parseColor("#CCCCCC");
		public int todayNumberColor = Color.RED;
		public int cellDownColor = Color.parseColor("#CCFFFF");
		public int cellSelectedColor = Color.parseColor("#99CCFF");
		public int curMonthCellbgColor = Color.WHITE;
		public int lastMonthCellbgColor = Color.GRAY;
		public int lastMonthTextColor = Color.GRAY;
		public int enableTextColor = Color.parseColor("#999999");
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Paint monthTextPaint;
		public Path boxPath; // �߿�·��
		//public Path preMonthBtnPath; // ��һ�°�ť������
		//public Path nextMonthBtnPath; // ��һ�°�ť������
		public String[] weekText = { "Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		public String[] monthText = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		
		public float textSize;
		
		public void init() {
			float temp = height / 7f;
			monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);
			//monthChangeWidth = monthHeight * 1.5f;
//			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			weekHeight = 0;
			cellHeight = (height - monthHeight - weekHeight) / 5f;
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
			textSize = cellHeight * 0.4f;
			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
			float monthTeextSize = cellHeight * 0.25f;
			monthTextPaint=new Paint();
			monthTextPaint.setTextSize(monthTeextSize);
			monthTextPaint.setAntiAlias(true);
			monthTextPaint.setTypeface(Typeface.DEFAULT);
			monthTextPaint.setColor(textColor);
			weekPaint = new Paint();
			weekPaint.setColor(textColor);
			weekPaint.setAntiAlias(true);
			float weekTextSize = weekHeight * 0.6f;
			weekPaint.setTextSize(weekTextSize);
			weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
//			float cellTextSize = cellHeight * 0.3f;
			datePaint.setTextSize(textSize);
			datePaint.setTypeface(Typeface.MONOSPACE);
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
	public void setTextColor(int thisMonth,int lastMonth){
		surface.textColor=thisMonth;
		surface.lastMonthTextColor=lastMonth;
	}
	
	public void setCellBgColor(int thisMonth,int lastMonth){
		surface.curMonthCellbgColor=thisMonth;
		surface.lastMonthCellbgColor=lastMonth;
	}
	
	public int getCalendarHeight(){
		return surface.height;
	}
}
