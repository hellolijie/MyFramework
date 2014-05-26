package cn.lijie.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpUtil {
	
	//静态常量
	static private final String NETERROECOKD1="网络堵车";
	static private final String NETERROECOKD2="连接服务器失败";
	static private int TIMEOUT=20*1000;
	
	private RequestQueue queue;
	private Context context;
	
	public HttpUtil(Context context){
		queue=Volley.newRequestQueue(context);
		this.context=context;
	}
	

	//显示进度条
	public ProgressDialog getAndShowProgress(Context context,String message){
		ProgressDialog progress=new ProgressDialog(context);
		if(message!=null&&!"".equals(message))
			progress.setMessage(message);
		progress.setCancelable(true);
		progress.setCanceledOnTouchOutside(true);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
		return progress;
	}
	
	//检测网络状态
	public boolean checkNetState(Context context){
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(connManager.getActiveNetworkInfo() != null) {  
	        return connManager.getActiveNetworkInfo().isAvailable();  
	    }  
	  
	    return false;  
	}
	//检测网络类型  1:流量  0：wifi -1：没有连接到网络
	public int checkNetType(Context context){
		if(checkNetState(context)){
			ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
			if(connManager.getActiveNetworkInfo().getType()==connManager.TYPE_MOBILE)
				return 1;
			else if(connManager.getActiveNetworkInfo().getType()==connManager.TYPE_WIFI)
				return 0;
		}
		return -1;
	}
	
	//获取RequestQueue
	public RequestQueue getRequestQueue(){
		return queue;
	}
	
	//get方式
	public Request<String> doGet(String url,RequestListener requestListener,boolean isShowProgressDialog){
		ProgressDialog progressDialog=null;
		if(isShowProgressDialog)
			progressDialog=getAndShowProgress(context, "");
		return queue.add(new StringRequest(Method.GET, url, new MListener(requestListener,progressDialog), new MErroListener(requestListener,progressDialog)));
	}
	
	//post方式
	public Request<String> doPost(String url,Map<String,String> params,RequestListener requestListener,boolean isShowProgressDialog){
		if(!checkNetState(context)){
			Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
			return null;
		}
		String param="";
		Set set =params.entrySet();
	    Iterator it=set.iterator();
	    while(it.hasNext()){
	    	if(!param.equals(""))
	    		param+="&";
	        Map.Entry<String, String>  entry=(Entry<String, String>) it.next();
	        param+=entry.getKey()+"="+entry.getValue();
	        System.out.println(entry.getKey()+":"+entry.getValue());
	    }
	    ProgressDialog progressDialog=null;
		if(isShowProgressDialog)
			progressDialog=getAndShowProgress(context, "");
		return queue.add(new PostStringRequest(Method.POST, url,param, new MListener(requestListener,progressDialog), new MErroListener(requestListener,progressDialog)));
	}
	
	
	//上传--基于httpclient
	public void upLoadData(final String strUrl,final File file,final String prameterName, final RequestListener listener){
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();  
					HttpPost httppost = new HttpPost(strUrl);  
//					httppost.setHeader("Content-Type", "multipart/form-data;boundary='+BOUNDARY'");
					MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
					FileBody  fileBody= new FileBody(file);
					mpEntity.addPart(prameterName,fileBody);
					httppost.setEntity(mpEntity);
					HttpResponse httpResponse = httpClient.execute(httppost); 
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						String strResult = EntityUtils.toString(httpResponse.getEntity());
						Log.i("Tag", strResult);
//		                JSONObject json=new JSONObject(strResult);
		                listener.onSuccess(strResult);
					}
					else{
						listener.onError(httpResponse.getStatusLine().getStatusCode()+"");
					}
				} catch (Exception e) {
					e.printStackTrace();
					listener.onError(e.getMessage());
				}
			}
			
		}).start();
	}
	
	//上传
	public void upLoad(String url,Map<String, String> params,Map<String, File> files,RequestListener requesntListener){
		//要创建线程
		try {
			String strRequest=doUpLoad(url, params, files);
			requesntListener.onSuccess(strRequest);
		} catch (IOException e) {
			requesntListener.onError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//以表单的方式上传
	private String doUpLoad(String actionUrl, Map<String, String> params,  
	        Map<String, File> files) throws IOException {  
	  
	    String BOUNDARY = java.util.UUID.randomUUID().toString();  
	    String PREFIX = "--", LINEND = "\r\n";  
	    String MULTIPART_FROM_DATA = "multipart/form-data";  
	    String CHARSET = "UTF-8";  
	    URL uri = new URL(actionUrl);  
	    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
	    conn.setReadTimeout(5 * 1000);  
	    conn.setDoInput(true);// 允许输入  
	    conn.setDoOutput(true);// 允许输出  
	    conn.setUseCaches(false);  
	    conn.setRequestMethod("POST"); // Post方式  
	    conn.setRequestProperty("connection", "keep-alive");  
	    conn.setRequestProperty("Charsert", "UTF-8");  
	    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
	            + ";boundary=" + BOUNDARY);  
	  
	    // 首先组拼文本类型的参数  
	    StringBuilder sb = new StringBuilder();  
	    for (Map.Entry<String, String> entry : params.entrySet()) {  
	        sb.append(PREFIX);  
	        sb.append(BOUNDARY);  
	        sb.append(LINEND);  
	        sb.append("Content-Disposition: form-data; name=\""  
	                + entry.getKey() + "\"" + LINEND);  
	        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
	        sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
	        sb.append(LINEND);  
	        sb.append(entry.getValue());  
	        sb.append(LINEND);  
	    }  
	    
	  
	    DataOutputStream outStream = new DataOutputStream(conn  
	            .getOutputStream());  
	    outStream.write(sb.toString().getBytes());  
	  
	    // 发送文件数据  
	    if (files != null)  
	        for (Map.Entry<String, File> file : files.entrySet()) {  
	            StringBuilder sb1 = new StringBuilder();  
	            sb1.append(PREFIX);  
	            sb1.append(BOUNDARY);  
	            sb1.append(LINEND);  
	            sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""  
	                            + file.getKey() + "\"" + LINEND);  
	            sb1.append("Content-Type: application/octet-stream; charset="  
	                    + CHARSET + LINEND);  
	            sb1.append(LINEND);  
	            outStream.write(sb1.toString().getBytes());  
	            InputStream is = new FileInputStream(file.getValue());  
	            byte[] buffer = new byte[1024];  
	            int len = 0;  
	            while ((len = is.read(buffer)) != -1) {  
	                outStream.write(buffer, 0, len);  
	            }  
	  
	            is.close();  
	            outStream.write(LINEND.getBytes());  
	        }  
	  
	    // 请求结束标志  
	    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
	    outStream.write(end_data);  
	    outStream.flush();  
	  
	    // 得到响应码  
	    int res = conn.getResponseCode();  
	    InputStream in = conn.getInputStream();  
	    InputStreamReader isReader = new InputStreamReader(in);  
	    BufferedReader bufReader = new BufferedReader(isReader);  
	    String line = null;  
	    String data = "OK";  
	  
	    while ((line = bufReader.readLine()) == null)  
	        data += line;  
	  
	    if (res == 200) {  
	        int ch;  
	        StringBuilder sb2 = new StringBuilder();  
	        while ((ch = in.read()) != -1) {  
	            sb2.append((char) ch);  
	        }  
	    }  
	    outStream.close();  
	    conn.disconnect();  
	    return in.toString();  
	}  
	
	//使用httpclient框架做post请求
	public void doPost(final String url, final List<NameValuePair> params,final RequestListener listener){
		if(!checkNetState(context)){
			Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
			return ;
		}
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				HttpEntity httpentity;
				try {
					httpentity = new UrlEncodedFormEntity(params, "utf-8");
					httpRequest.setEntity(httpentity);
					HttpResponse httpResponse = httpclient.execute(httpRequest); 
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						//取得返回的字符串  
		                String strResult = EntityUtils.toString(httpResponse.getEntity()); 
		                listener.onSuccess(strResult);
					}
					else{
//						listener.onError(httpResponse.getStatusLine().getStatusCode()+"");
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_GATEWAY_TIMEOUT){
							listener.onError(NETERROECOKD1);
						}
						else{
//							listener.onError(httpResponse.getStatusLine().getStatusCode()+"");
							listener.onError(NETERROECOKD2);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					listener.onError(NETERROECOKD2);
				}
			}
		}).start();
	}
	
	//使用httpclient框架做get请求
	public static void doGet(String url,final List<NameValuePair> params,final RequestListener listener){
		if(params!=null&&params.size()!=0){
			url+="?";
			for(int i=0;i<params.size();i++){
				if(i!=0)
					url+="&";
				url+=params.get(i).getName();
				url+="=";
				url+=params.get(i).getValue();
			}
		}
		final String URL=url;
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpClient httpclient = new DefaultHttpClient(); 
				HttpGet httpRequest = new HttpGet(URL);
				try {
					HttpResponse httpResponse = httpclient.execute(httpRequest);
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						//取得返回的字符串  
		                String strResult = EntityUtils.toString(httpResponse.getEntity()); 
//		                JSONObject json=new JSONObject(strResult);
		                listener.onSuccess(strResult);
					}
					else{
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_GATEWAY_TIMEOUT){
							listener.onError(NETERROECOKD1);
						}
						else{
//							listener.onError(httpResponse.getStatusLine().getStatusCode()+"");
							listener.onError(NETERROECOKD2);
						}
					}
				} catch (Exception e) {
					listener.onError(NETERROECOKD2);
				}
			}
		}).start();
	}
	
}
