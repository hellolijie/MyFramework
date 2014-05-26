package cn.lijie;

import java.lang.reflect.Field;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.wheel.test.WheelView;
import android.wheel.test.adapter.ArrayWheelAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.lijie.activity.CalendarViewActivity;
import cn.lijie.activity.QRCodeActivity;
import cn.lijie.activity.ScrollCalendarActivity;
import cn.lijie.activity.ScrollerTestViewActivity;
import cn.lijie.activity.ScrolltestActivity;
import cn.lijie.activity.SwipeViewtestActivity;
import cn.lijie.activity.WheelActivity;
import cn.lijie.customView.CalendarView;
import cn.lijie.customView.DialogFactory;
import cn.lijie.specialEffect.RotateAnimation;
import cn.lijie.utils.IntentUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingSerlockActivity;

public class MyFrameworkActivity extends SlidingSerlockActivity implements SensorEventListener{
    /** Called when the activity is first created. */
	protected Object mActionMode; 
	private MenuItem shareMenu;
	
	
	private SensorManager mSensorManager; 
	private Vibrator vibrator;
	
	
	private SlidingMenu mSlidingMenu;
	
//	private PullToRefreshView pullToRefreshView;
	
	private ScrollView scrollView;
	
	private GestureDetector detector;
	private ClickListener clickListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame_content);
        setBehindContentView(R.layout.main_frame_menu);
        clickListener=new ClickListener();
        findViewById(R.id.scrolltestActivity).setOnClickListener(clickListener);
        findViewById(R.id.scrollCalendarActivity).setOnClickListener(clickListener);
        findViewById(R.id.swipeViewtestActivity).setOnClickListener(clickListener);
        findViewById(R.id.scrollerTestViewActivity).setOnClickListener(clickListener);
        findViewById(R.id.calendarViewActivity).setOnClickListener(clickListener);
        findViewById(R.id.QRCodeActivity).setOnClickListener(clickListener);
        findViewById(R.id.wheelActivity).setOnClickListener(clickListener);
        
        
        detector=new GestureDetector(new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.i("tag", "singletapup");
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.i("tag", "onShowPress");
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				Log.i("tag", "onScroll");
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				Log.i("tag", "onLongPress");
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				Log.i("tag", "onFling");
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				Log.i("tag", "onDown");
				return false;
			}
		});
        
        detector.setIsLongpressEnabled(true);
        
        final TextView test =(TextView) findViewById(R.id.test);
        
        final CalendarView calendar=(CalendarView) findViewById(R.id.calendar);
        test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("tag", "click");
				RotateAnimation rotateAnim=new RotateAnimation(50, 100, RotateAnimation.ROTATE_DECREASE);
				test.startAnimation(rotateAnim);
//				calendar.clickLeftMonth();
//				calendar.jumpNMonth(-(int)(Math.random()*10));
//				test.setText(calendar.getYearAndmonth());
				IntentUtils.sendMailByIntent(MyFrameworkActivity.this,"ni hao","hello","353733664@qq.com");
				Log.i("tag", "click");
			}
		});
        
        WheelView hourChooser=(WheelView) findViewById(R.id.hourChooser);
		WheelView minuChooser=(WheelView) findViewById(R.id.minuChooser);
		
		ArrayWheelAdapter<String> adapter =
            new ArrayWheelAdapter<String>(this, new String[]{"33","33","dd","dd","dd","jj","33","dd","dd","dd","jj","33","dd","dd","dd","jj"});
		hourChooser.setViewAdapter(adapter);
		minuChooser.setViewAdapter(adapter);
		hourChooser.setCyclic(true);
		minuChooser.setCyclic(true);
		
		
        mSlidingMenu = getSlidingMenu();
        
        mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);//设置阴影图片
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); //设置阴影图片的宽度
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); //SlidingMenu划出时主页面显示的剩余宽度
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        
//        pullToRefreshView=(PullToRefreshView) findViewById(R.id.pullToRefresh);
//        pullToRefreshView.setOnHeaderRefreshListener(null);
//        pullToRefreshView.setHeaderRefresh(false);        
        
        
        //获取传感器管理服务  
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        //震动  
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        
        
//        findViewById(R.id.hello).setOnClickListener(new OnClickListener() {
//        	@Override
//			public void onClick(View v) {
////        		if (mActionMode != null) {  
////                    return ;  
////                }  
////                // Start the Contextual Action Bar using the ActionMode.Callback defined above  
////        		//设置contextual actionbar
////                mActionMode = MyFrameworkActivity.this.startActionMode(mActionModeCallback);  
//			}
//		});
        
        
        //需要判断版本 3.0以前不能使用actionbar google没有发布兼容包
		ActionBar actionBar = getSupportActionBar();  
		
		actionBar.hide();  
		actionBar.show(); 
		
//		actionBar.setDisplayHomeAsUpEnabled(true);
		
		getOverflowMenu();
//		
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionBarBg));
		actionBar.setIcon(R.drawable.ic_pulltorefresh_arrow_up);
		
		actionBar.setLogo(R.drawable.ic_pulltorefresh_arrow);
		
		
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setCustomView(R.layout.main);
//		setNavigation();
		
//		invalidateOptionsMenu();
		
    }

//    private void addShortcutToDesktop(){
//    	 
//		Intent shortcut = new Intent(ACTION_INSTALL);
// 
//		BitmapDrawable iconBitmapDrawabel = null;
// 
//		// 获取应用基本信息
//		String label = this.getPackageName();
//		PackageManager packageManager = getPackageManager();
//		try {
//			iconBitmapDrawabel = (BitmapDrawable) packageManager.getApplicationIcon(label);
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
// 
//		// 设置属性
//		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);
//		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmapDrawabel.getBitmap());
// 
//		// 是否允许重复创建 -- fase-->否
//		shortcut.putExtra("duplicate", false); 
// 
//		// 设置启动程序
//		ComponentName comp = new ComponentName(label,"." + this.getLocalClassName());
//		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
// 
//		sendBroadcast(shortcut);
//	}

    
    
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//invalidateOptionsMenu()
		return super.onPrepareOptionsMenu(menu);
	}

	


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		Log.i("tag", "activity");
		return super.onTouchEvent(event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Toast.makeText(this, "change", Toast.LENGTH_LONG).show();
		super.onConfigurationChanged(newConfig);
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//加速度传感器  
//		mSensorManager.registerListener(this,  
//		mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),  
//		//还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，  
//		//根据不同应用，需要的反应速率不同，具体根据实际情况设定  
//		SensorManager.SENSOR_DELAY_NORMAL);
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				HttpDownloadUtil.downFile("http://c.hiphotos.baidu.com/image/w%3D2048/sign=5d2c45279c2f07085f052d00dd1cb999/472309f790529822a8f46eabd5ca7bcb0b46d497.jpg", 
//						"", "image");
//				
//			}
//		}).start();
		super.onResume();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
//		mSensorManager.unregisterListener(this); 
		super.onPause();
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.action_menu, menu);
//		SearchView serach=(SearchView) menu.findItem(R.id.action_menu_search);
		shareMenu=(MenuItem) menu.findItem(R.id.action_menu_share);
		return super.onCreateOptionsMenu(menu);
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		getMenuInflater().inflate(R.menu.action_menu, menu);
////		SearchView serach=(SearchView) menu.findItem(R.id.action_menu_search);
//		shareMenu=menu.findItem(R.id.action_menu_share);
//		return super.onCreateOptionsMenu(menu);
//	}

	
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch(item.getItemId()){
//		case R.id.action_menu_set:
//			Toast.makeText(getApplication(), "aaa", Toast.LENGTH_SHORT).show();
//			shareMenu.setActionView(null);
//			break;
//		case R.id.action_menu_share:
//			Toast.makeText(getApplication(), "bbb", Toast.LENGTH_SHORT).show();
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
    


	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_menu_set:
//			
//			ValueAnimator animation = ValueAnimator.ofInt(10,100);
//			animation.setDuration(1000);
//			animation.addUpdateListener(new AnimatorUpdateListener() {
//			    @Override
//			    public void onAnimationUpdate(ValueAnimator animation) {
//			        Log.i("tag", ((Integer) animation.getAnimatedValue()).toString());
////			        Toast.makeText(MyFrameworkActivity.this, ""+animation.getAnimatedValue(), Toast.LENGTH_SHORT).show();
//			    }
//			});
//			animation.setInterpolator(new DecelerateInterpolator());
//			animation.start();
//			
//			
//			shareMenu.setActionView(null);
			
//			DialogFactory.createConfirmWithCancelDialog(this, "text2", new DialogFactory.ClickListener() {
//				
//				@Override
//				public void onClick(int viewId, DialogInterface dialog) {
//					switch(viewId){
//					case R.id.dialog_confirm_button:
//						Toast.makeText(getApplication(), "confirm", Toast.LENGTH_SHORT).show();
//						break;
//					case R.id.dialog_cancel_button:
//						Toast.makeText(getApplication(), "cancel", Toast.LENGTH_SHORT).show();
//						break;
//					}
//					dialog.dismiss();
//				}
//				
//				@Override
//				public void onCancle(DialogInterface dialog) {
//					// TODO Auto-generated method stub
//					
//				}
//			}).show();
//			DialogFactory.createProgressDialog(this).show();
//			AlertDialog.Builder builder=new Builder(this);
//			builder.setTitle("aaa");
//			builder.setPositiveButton("hahha", null);
//			builder.setNegativeButton("fggaga", null);
//			builder.create().show();
			
			ProgressDialog.show(this, null, "看，我转圈是不是很厉害",false,true);
			TextView textView=new TextView(this);
			textView.setText("我去");
			shareMenu.setActionView(textView);
			break;
		case R.id.action_menu_share:
			DialogFactory.createConfirmDialog(this, "text", new DialogFactory.ClickListener() {
				
				@Override
				public void onClick(int viewId, DialogInterface dialog) {
					switch(viewId){
					case R.id.dialog_confirm_button:
						Toast.makeText(getApplication(), "bbb", Toast.LENGTH_SHORT).show();
					break;
					}
					dialog.dismiss();
				}
				
				@Override
				public void onCancle(DialogInterface dialog) {
					Toast.makeText(getApplication(), "cancel", Toast.LENGTH_SHORT).show();
				}
			}).show();
			break;
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}


	//现实overflow选项
	private void getOverflowMenu() {
	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//设置导航模式
//	private void setNavigation(){
//		final String[] actions = new String[] { "Bookmark", "Subscribe", "Share" };
//		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
//	                getBaseContext(),  
//	                android.R.layout.simple_spinner_dropdown_item, actions);  
//	  
//	        /** Enabling dropdown list navigation for the action bar */  
////	        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);  
//	        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
////	        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//	  
//	        /** Defining Navigation listener */  
//	        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {  
//	  
//	            @Override  
//	            public boolean onNavigationItemSelected(int itemPosition,  
//	                    long itemId) {  
//	                Toast.makeText(getBaseContext(),  
//	                        "You selected : " + actions[itemPosition],  
//	                        Toast.LENGTH_SHORT).show();  
//	                return false;  
//	            }  
//	        };  
//	  
//	        /** 
//	         * Setting dropdown items and item navigation listener for the actionbar 
//	         */  
////	        getActionBar().setListNavigationCallbacks(adapter, navigationListener);
//	        
//	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {  
		  
        // Called when the action mode is created; startActionMode() was called  
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {  
            // Inflate a menu resource providing context menu items  
            MenuInflater inflater = mode.getMenuInflater();  
            // R.menu.contextual 是 contextual action bar 的布局文件， 在 /res/menu/ 文件夹下  
            inflater.inflate(R.menu.contextual, menu);  
            return true;  
        }  
  
        // Called each time the action mode is shown. Always called after  
        // onCreateActionMode, but  
        // may be called multiple times if the mode is invalidated.  
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {  
            return false; // Return false if nothing is done  
        }  
  
        // 当用户点击 contextual action bar 的 menu item 的时候产生点击事件  
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {  
            switch (item.getItemId()) {  
            case R.id.toast:  
                Toast.makeText(MyFrameworkActivity.this, "Selected menu",  
                        Toast.LENGTH_LONG).show();  
                mode.finish(); // 关闭 contextual action bar  
                return true;  
            default:  
                return false;  
            }  
        }  
  
        // Called when the user exits the action mode  
        public void onDestroyActionMode(ActionMode mode) {  
            mActionMode = null;  
        }  
    };

    
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();  
		  
		  //values[0]:X轴，values[1]：Y轴，values[2]：Z轴  
		  float[] values = event.values;  
		  
		  if(sensorType == Sensor.TYPE_ACCELEROMETER){  
		  
		  /*因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机 
		  *的时候，瞬时加速度才会突然增大或减少。 
		  *所以，经过实际测试，只需监听任一轴的加速度大于14的时候，改变你需要的设置 
		  *就OK了~~~ 
		  */  
		   if((Math.abs(values[0])>14||Math.abs(values[1])>14||Math.abs(values[2])>14)){  
		  
		  
			    //摇动手机后，再伴随震动提示~~  
			    vibrator.vibrate(500);  
			    Toast.makeText(this, event.values+"", Toast.LENGTH_SHORT).show();
		  
		   }  
		  }
	}  

	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.scrolltestActivity:
				Intent intent=new Intent(getApplicationContext(), ScrolltestActivity.class);
				startActivity(intent);
				break;
			case R.id.scrollCalendarActivity:
				Intent intent2=new Intent(getApplicationContext(), ScrollCalendarActivity.class);
				startActivity(intent2);
				break;
			case R.id.swipeViewtestActivity:
				Intent intent3=new Intent(getApplicationContext(), SwipeViewtestActivity.class);
				startActivity(intent3);
				break;
			case R.id.scrollerTestViewActivity:
				Intent intent4=new Intent(getApplicationContext(), ScrollerTestViewActivity.class);
				startActivity(intent4);
				break;
			case R.id.calendarViewActivity:
				Intent intent5=new Intent(getApplicationContext(), CalendarViewActivity.class);
				startActivity(intent5);
				break;
			case R.id.QRCodeActivity:
				Intent intent6=new Intent(getApplicationContext(), QRCodeActivity.class);
				startActivity(intent6);
				break;
			case R.id.wheelActivity:
				Intent intent7=new Intent(getApplicationContext(), WheelActivity.class);
				startActivity(intent7);
				break;
			}
		}
		
	}
}