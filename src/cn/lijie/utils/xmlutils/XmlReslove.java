package cn.lijie.utils.xmlutils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class XmlReslove {
	public static XmlPullParser getXmlPullParser(Context context,String path, String encoding) {
		//���android �е�PULL������
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			 //�����������ͱ��뷽ʽ
			xpp.setInput(context.getAssets().open(path),encoding);
			return xpp;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.d("xml", "��ý���������");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("xml", "������");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean gotoTagByTagName(XmlPullParser xpp, String tagName) {
		try {
			// ��ȡ��һ�������¼�
			int eventType = xpp.next();
			// ִ��ѭ���������ж��¼�������
			while (true) {
				// ��ȡ��xml�Ŀ�ʼ��ǩ
				if (eventType == XmlPullParser.START_TAG) {
					// ��ǰ�ڵ�����
					String s = xpp.getName().trim();
					if (s.equals(tagName)) {
						return true;
					}
					// ��ȡ��xml�Ľ���
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					break;
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	// ��ȡ��xml���ı�
	public static String getXmlText(XmlPullParser xpp) {
		try {
			// ��ȡ��һ�������¼�
			int eventType = xpp.next();
		
			while (true) {
				// ��ȡ��xml���ı�
				if (eventType == XmlPullParser.TEXT) {
					return xpp.getText();
					// ��ȡ��xml�Ľ���
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					break;
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}


