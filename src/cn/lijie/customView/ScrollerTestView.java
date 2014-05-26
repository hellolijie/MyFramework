package cn.lijie.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

public class ScrollerTestView extends View{
	private Scroller mScroller;
	private int lastX,lastY;
	
	private VelocityTracker velocityTracker;
	
	public ScrollerTestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i("tag", "st");
		mScroller=new Scroller(context);
		
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("tag", "onclick");
			}
		});
		
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("tag", "ontouch");
				return false;
			}
		});
	}

	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(0, 0, 40, 40, new Paint());
		super.onDraw(canvas);
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
			
			if(velocityTracker==null)
				velocityTracker=VelocityTracker.obtain();
			
			lastX=(int) event.getRawX();
			lastY=(int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			velocityTracker.addMovement(event);
			scrollBy(lastX-(int)event.getRawX(), lastY-(int)event.getRawY());
			lastX=(int) event.getRawX();
			lastY=(int) event.getRawY();
//			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			velocityTracker.computeCurrentVelocity(1000);
//			mScroller.startScroll(getScrollX(), getScrollY(), getScrollX()+60, getScrollX()+70,3000);
			mScroller.fling(getScrollX(), getScrollY(), (int)-velocityTracker.getXVelocity(), (int)-velocityTracker.getYVelocity(), -500, 0, -700, 0);
//			scrollTo(0, 0);
			invalidate();
			velocityTracker.recycle();
			velocityTracker=null;
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
	
	
}
