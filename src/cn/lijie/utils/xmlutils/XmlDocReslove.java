package cn.lijie.utils.xmlutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import android.content.Context;
import android.util.Log;

public class XmlDocReslove {
	//获取document对象
	public static Document getDocument(InputStream in){
		//得到 DocumentBuilderFactory 对象, 由该对象可以得到 DocumentBuilder 对象
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			 //得到DocumentBuilder对象
			DocumentBuilder builder=factory.newDocumentBuilder();
			//得到代表整个xml的Document对象
			Document document=builder.parse(in);
			return document;
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return null;
		}
	}
	
	public static Map<String,List> getChilds(Context context,String id,Document document){
		Map<String,List> map=new HashMap<String, List>();
		List<String> list=new ArrayList<String>();
		List<Node> nodes=new ArrayList<Node>();
		List<String> values=new ArrayList<String>();
		Element root=document.getDocumentElement();
		Element element=document.getElementById(id);
		Log.i("tag", element.getAttributeNode("desc").getValue());
		NodeList nodeList=element.getChildNodes();
		
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap nodeMap=node.getAttributes();
				Node nodeNode=nodeMap.item(1);
				list.add(nodeNode.getNodeValue());
				nodes.add(node);
				values.add(nodeMap.item(0).getNodeValue());
			}
		}
		Log.i("tag", root.getChildNodes().getLength()+"");
		map.put("desc", list);
		map.put("nodes", nodes);
		map.put("values", values);
		return map;
	}
	
	public static Map<String,List> getChildNoNode(Context context,String id,Document document){
		Map<String,List> map=new HashMap<String, List>();
		List<String> list=new ArrayList<String>();
//		List<Node> nodes=new ArrayList<Node>();
		List<String> values=new ArrayList<String>();
		Element root=document.getDocumentElement();
		Element element=document.getElementById(id);
		Log.i("tag", element.getAttributeNode("desc").getValue());
		NodeList nodeList=element.getChildNodes();
		
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				NamedNodeMap nodeMap=node.getAttributes();
				Node nodeNode=nodeMap.item(1);
				list.add(nodeNode.getNodeValue());
//					nodes.add(node);
				values.add(nodeMap.item(0).getNodeValue());
			}
		}
		Log.i("tag", root.getChildNodes().getLength()+"");
		map.put("desc", list);
//		map.put("nodes", nodes);
		map.put("values", values);
		return map;
	}
	
	//根据value获取desc值
	public static String getDescByValue(Map<String,List> map,String value){
		List values=map.get("values");
		List desc=map.get("desc");
		for(int i=0;i<values.size();i++){
			if(value.equals(values.get(i))){
				return (String) desc.get(i);
			}
		}
		return null;
	}
}
