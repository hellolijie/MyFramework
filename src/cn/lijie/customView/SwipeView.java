package cn.lijie.customView;

import com.actionbarsherlock.internal.nineoldandroids.animation.ValueAnimator;
import com.actionbarsherlock.internal.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SwipeView extends RelativeLayout {
	private int leftSwipeWidth,rightSwipeWidth;
	private float startX,startY;
	private float startLeftPadding,startRightPadding;
	
	private float startLeftMargin,startRightMargin;
	private int state=0;
	private View frontView,backView;
	private LinearLayout frontViewDisplay,backViewDisplay;
	
	public SwipeView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public SwipeView(Context context){
		super(context);
		init();
	}

	private void init(){
		leftSwipeWidth=0;
		rightSwipeWidth=0;
		backViewDisplay=new LinearLayout(getContext());
		backViewDisplay.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(backViewDisplay);
		frontViewDisplay=new LinearLayout(getContext());
		frontViewDisplay.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(frontViewDisplay);
//		View view=new View(getContext());
//		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//		view.setBackgroundColor(Color.GREEN);
//		frontView=view;
//		frontViewDisplay.addView(view);
	}
	
	public void setFrontAndBackView(View frontView,View backView){
//		backView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.backView=backView;
		backViewDisplay.addView(backView,-1);
		
//		frontView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.frontView=frontView;
		frontViewDisplay.addView(frontView);
	}

	public void close(){
		swipeAnimation(frontViewDisplay.getPaddingLeft(), 0);
	}
	
	public void setLeftSwipeWidth(int leftSwipeWidth) {
		this.leftSwipeWidth = leftSwipeWidth;
	}

	public void setRightSwipeWidth(int rightSwipeWidth) {
		this.rightSwipeWidth = rightSwipeWidth;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX=event.getRawX();
			startY=event.getRawY();
			startLeftPadding=frontViewDisplay.getPaddingLeft();
			startRightPadding=frontViewDisplay.getPaddingRight();
			
			break;
		case MotionEvent.ACTION_MOVE:
			if(Math.abs(event.getRawX()-startX)>5){
				if(state==0){
					if(event.getRawX()-startX>0){
						state=1;	//Õ˘”“ª¨
					}
					else if(event.getRawX()-startX<0){
						state=2;	//Õ˘◊Ûª¨
					}
				}
				if(state==1){
					float leftPadding=startLeftPadding+(event.getRawX()-startX);
					int endPadding;
					if(startLeftPadding<0)
						endPadding=0;
					else
						endPadding=leftSwipeWidth;
					if(leftPadding<endPadding)
						frontViewDisplay.setPadding((int)leftPadding, 0, (int)-leftPadding, 0);
	//				if(leftPadding<leftSwipeWidth&&leftPadding>0){
//					if(startLeftPadding<0){
//						if(leftPadding<0){
//							frontViewDisplay.setPadding((int)leftPadding, 0, (int)-leftPadding, 0);
//						}
//					}
//					else{
//						if(leftPadding<leftSwipeWidth){
//							frontViewDisplay.setPadding((int)leftPadding, 0, (int)-leftPadding, 0);
//						}
//					}
//					if(leftPadding<rightSwipeWidth+startLeftPadding){
//						frontViewDisplay.setPadding((int)leftPadding, 0, (int)-leftPadding, 0);
//					}
				}
				else if(state==2){
					float rightPadding=startRightPadding-(event.getRawX()-startX);
					int endPadding;
					if(startRightPadding<0)
						endPadding=0;
					else
						endPadding=rightSwipeWidth;
					if(rightPadding<endPadding)
						frontViewDisplay.setPadding((int)-rightPadding, 0, (int)rightPadding, 0);
	//				if(rightPadding<rightSwipeWidth&&rightPadding>0){
//					if(startRightPadding<0){
//						if(rightPadding<0){
//							frontViewDisplay.setPadding((int)-rightPadding, 0, (int)rightPadding, 0);
//						}
//					}
//					else{
//						if(rightPadding<0){
//							frontViewDisplay.setPadding((int)-rightPadding, 0, (int)rightPadding, 0);
//						}
//					}
//					if(rightPadding<leftSwipeWidth+startRightPadding){
//						frontViewDisplay.setPadding((int)-rightPadding, 0, (int)rightPadding, 0);
//					}
				}
				event.setAction(MotionEvent.ACTION_CANCEL);
				invalidate();
			}
			
			break;
		case MotionEvent.ACTION_UP:
			if(state==1){
				if(frontViewDisplay.getPaddingLeft()<leftSwipeWidth/2){
					swipeAnimation((int)frontViewDisplay.getPaddingLeft(),0);
				}
				else{
					swipeAnimation((int)frontViewDisplay.getPaddingLeft(),leftSwipeWidth);
				}
			}
			else if(state==2){
				if(frontViewDisplay.getPaddingRight()<rightSwipeWidth/2){
					swipeAnimation((int)-frontViewDisplay.getPaddingRight(),0);
				}
				else{
					swipeAnimation((int)-frontViewDisplay.getPaddingRight(),-rightSwipeWidth);
				}
			}
			invalidate();
			state=0;
			break;
		case MotionEvent.ACTION_CANCEL:
			if(state==1){
				if(frontViewDisplay.getPaddingLeft()<leftSwipeWidth/2){
					swipeAnimation((int)frontViewDisplay.getPaddingLeft(),0);
				}
				else{
					swipeAnimation((int)frontViewDisplay.getPaddingLeft(),leftSwipeWidth);
				}
			}
			else if(state==2){
				if(frontViewDisplay.getPaddingRight()<rightSwipeWidth/2){
					swipeAnimation((int)-frontViewDisplay.getPaddingRight(),0);
				}
				else{
					swipeAnimation((int)-frontViewDisplay.getPaddingRight(),-rightSwipeWidth);
				}
			}
			invalidate();
			state=0;
			break;
		}
//		MotionEvent event=ev;
//		Log.i("tag", "dispatch--");
//		switch(event.getAction()){c  
//		case MotionEvent.ACTION_DOWN:
//			startX=event.getRawX();
//			startY=event.getRawY();
//			RelativeLayout.LayoutParams params=(LayoutParams) frontView.getLayoutParams();
//			startLeftMargin=params.leftMargin;
//			startRightMargin=params.rightMargin;
//			Log.i("tag", "down--");
//			break;
//		case MotionEvent.ACTION_MOVE:
//			RelativeLayout.LayoutParams layoutParams=(LayoutParams) frontView.getLayoutParams();
//			if(state==0){
//				if(event.getRawX()-startX>0){
//					state=1;	//Õ˘”“ª¨
//				}
//				else if(event.getRawX()-startX<0){
//					state=2;	//Õ˘◊Ûª¨
//				}
//			}
//			if(state==1){
//				float leftMargin=startLeftMargin+(event.getRawX()-startX);
//				if(leftMargin<leftSwipeWidth&&leftMargin>0){
//					layoutParams.leftMargin=(int) leftMargin;
//					layoutParams.rightMargin=(int) -leftMargin;
//					frontView.setLayoutParams(layoutParams);
//					frontView.invalidate();
//				}
//			}
//			else if(state==2){
//				float rightMargin=startRightMargin-(event.getRawX()-startX);
//				if(rightMargin<rightSwipeWidth&&rightMargin>0){
//					layoutParams.leftMargin=(int) -rightMargin;
//					layoutParams.rightMargin=(int) rightMargin;
//					frontView.setLayoutParams(layoutParams);
//					frontView.invalidate();
//				}
//			}
//			Log.i("tag", "move--");
//			break;
//		case MotionEvent.ACTION_UP:
//			if(state==1){
//				RelativeLayout.LayoutParams lp=(LayoutParams) frontView.getLayoutParams();
//				float leftMargin=lp.leftMargin;
//				if(lp.leftMargin<leftSwipeWidth/2){
//					swipeAnimation((int)leftMargin,0);
//				}
//				else{
//					swipeAnimation((int)leftMargin,leftSwipeWidth);
//				}
//			}
//			else if(state==2){
//				RelativeLayout.LayoutParams lp=(LayoutParams) frontView.getLayoutParams();
//				float rightMargin=lp.rightMargin;
//				if(lp.rightMargin<rightSwipeWidth/2){
//					swipeAnimation((int)-rightMargin,0);
//				}
//				else{
//					swipeAnimation((int)-rightMargin,-rightSwipeWidth);
//				}
//			}
//			state=0;
//			Log.i("tag", "up--");
//			break;
//		}
		return super.dispatchTouchEvent(event);
	}

	private void swipeAnimation(int start,int end){
		ValueAnimator animation = ValueAnimator.ofInt(start,end);
		
		animation.addUpdateListener(new AnimatorUpdateListener() {
		    @Override
		    public void onAnimationUpdate(ValueAnimator animation) {
		    	frontViewDisplay.setPadding((Integer) animation.getAnimatedValue(), 0, -(Integer) animation.getAnimatedValue(), 0);
		    	invalidate();
		    	
		    }
		});
		animation.setInterpolator(new DecelerateInterpolator());
		animation.start();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i("tag", "intercept--");
		return super.onInterceptTouchEvent(ev);
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
	
	
}
