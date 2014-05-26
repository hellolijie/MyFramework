package cn.lijie.customView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.lijie.R;

import com.actionbarsherlock.view.Window;

public class DialogFactory {
	
	public static interface ClickListener{
		void onClick(int viewId,DialogInterface dialog);
		void onCancle(DialogInterface dialog);
	}
	
	//自定义布局
	public static Dialog createDialog(Context context,int[] ids,View layout,final ClickListener clickListener){
		AlertDialog.Builder builder=new Builder(context);
		builder.setView(layout);
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if(clickListener!=null)
					clickListener.onCancle(dialog);
			}
			
		});
		final AlertDialog dialog=builder.create();
		for(int id:ids){
			layout.findViewById(id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(clickListener!=null)
						clickListener.onClick(v.getId(),dialog);
				}
			});
		}
		return dialog;
	}
	
	//自定义确定弹出框
	public static Dialog createConfirmDialog(Context context,String title,final ClickListener clickListener){
		View layout=LinearLayout.inflate(context, R.layout.confirm_alertdialog_layout, null);
		((TextView)layout.findViewById(R.id.dialog_title)).setText(title);
		return createDialog(context, new int[]{R.id.dialog_confirm_button}, layout, clickListener);
	}
	
	//自定义确定取消弹出框
	public static Dialog createConfirmWithCancelDialog(Context context,String title,final ClickListener clickListener){
		View layout=LinearLayout.inflate(context, R.layout.confirmcancel_alertdialog_layout, null);
		((TextView)layout.findViewById(R.id.dialog_title)).setText(title);
		return createDialog(context, new int[]{R.id.dialog_confirm_button,R.id.dialog_cancel_button}, layout, clickListener);
	}
	
	//自定义进度条
	public static ProgressDialog createProgressDialog(Context context){
		ProgressDialog progressDialog=new ProgressDialog(context);
//		progressDialog.setView(new ProgressBar(context),0,0,0,0);
		return progressDialog;
//		return createDialog(context,new int[]{},new ProgressBar(context),null);
	}
	
}




















