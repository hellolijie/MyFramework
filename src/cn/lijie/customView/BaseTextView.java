package cn.lijie.customView;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BaseTextView extends TextView{

	public BaseTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setTypeface(Typeface.SANS_SERIF);
//		AssetManager mgr=context.getAssets();
//		Typeface tf=Typeface.createFromAsset(mgr, "fonts/weiruanyahei.ttf");
//		setTypeface(tf);
	}

}
