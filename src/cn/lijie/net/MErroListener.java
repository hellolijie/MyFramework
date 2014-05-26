package cn.lijie.net;

import android.app.ProgressDialog;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/*
 * 网络返回失败
 */


public class MErroListener implements ErrorListener{
	private RequestListener requestListener;
	private ProgressDialog progressDialog;
	MErroListener(RequestListener requestListener,ProgressDialog progressDialog){
		this.requestListener=requestListener;
		this.progressDialog=progressDialog;
	}
	
	@Override
	public void onErrorResponse(VolleyError arg0) {
		if(this.progressDialog!=null)
			this.progressDialog.dismiss();
		if(!(arg0.getMessage()==null)){
			this.requestListener.onError(arg0.getMessage());
		}
		else{
			this.requestListener.onError("连接服务器失败");
		}
	}
}
