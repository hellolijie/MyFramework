package cn.lijie.net;
/*
 * ����ص��ӿ�
 */
public interface RequestListener {
	void onSuccess(String result);
	void onError(String printMe);
}
