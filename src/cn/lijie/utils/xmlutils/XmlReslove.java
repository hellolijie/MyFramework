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
		//获得android 中的PULL解析器
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			 //设置输入流和编码方式
			xpp.setInput(context.getAssets().open(path),encoding);
			return xpp;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.d("xml", "获得解析器错误");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("xml", "流错误");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean gotoTagByTagName(XmlPullParser xpp, String tagName) {
		try {
			// 获取下一个解析事件
			int eventType = xpp.next();
			// 执行循环并依次判断事件的类型
			while (true) {
				// 读取到xml的开始标签
				if (eventType == XmlPullParser.START_TAG) {
					// 当前节点名称
					String s = xpp.getName().trim();
					if (s.equals(tagName)) {
						return true;
					}
					// 读取到xml的结束
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
	// 读取到xml的文本
	public static String getXmlText(XmlPullParser xpp) {
		try {
			// 获取下一个解析事件
			int eventType = xpp.next();
		
			while (true) {
				// 读取到xml的文本
				if (eventType == XmlPullParser.TEXT) {
					return xpp.getText();
					// 读取到xml的结束
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


