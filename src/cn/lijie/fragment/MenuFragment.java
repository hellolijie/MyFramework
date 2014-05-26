package cn.lijie.fragment;


import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.lijie.R;

public class MenuFragment extends BaseFragment{
	private ListView mDrawerList;
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIconsTypeArray;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.slidingmenu_layout, null);
		
		initView(rootView);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void initView(View view){
		mDrawerList=(ListView) view.findViewById(R.id.left_menu);
		mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);  
        mNavMenuIconsTypeArray = getResources().obtainTypedArray(R.array.nav_drawer_icons); 
        
        
	}

	//菜单选择回调接口
	public interface SLMenuListOnItemClickListener{
    	
    	public void selectItem(int position,String title);
    }
	
}
