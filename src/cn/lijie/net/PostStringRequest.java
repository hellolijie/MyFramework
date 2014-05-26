package cn.lijie.net;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

/*
 * POSTÕ¯¬Á÷¥––¿‡
 */

public class PostStringRequest extends StringRequest{
	static private int TIMEOUT=20*1000;
	
	private String param;
	
	public PostStringRequest(int method, String url,String param,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 1, 1.0f));
		this.param=param;
	}
	
	@Override
	public byte[] getBody() throws AuthFailureError {
		return param==null?super.getBody():param.getBytes();
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();  
		headers.put("Charsert", "UTF-8");  
		headers.put("Accept", "text/plain");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
		headers.put("Accept-Encoding", "gzip,deflate");  
		return super.getHeaders();
	}
}
