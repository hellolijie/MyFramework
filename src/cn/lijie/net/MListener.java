package cn.lijie.net;

import com.android.volley.Response.Listener;

import android.app.ProgressDialog;

/*
 * ÍøÂç³É¹¦·µ»Ø
 */

public class MListener implements Listener<String>{
	
	private RequestListener requestListener;
	private ProgressDialog progressDialog;
	MListener(RequestListener requestListener,ProgressDialog progressDialog){
		this.requestListener=requestListener;
		this.progressDialog=progressDialog;
	}

	@Override
	public void onResponse(String arg0) {
		this.requestListener.onSuccess(arg0);
		if(this.progressDialog!=null)
			this.progressDialog.dismiss();
	}
}
