package cn.lijie.net;
/*
 * 网络回调接口
 */
public interface RequestListener {
	void onSuccess(String result);
	void onError(String printMe);
}
