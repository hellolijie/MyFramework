package cn.lijie.cache.imageCache;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageCacheUtil {
	static private ImageCacheUtil instance;
	public static ImageCacheUtil getInstance(Context context){
		if(instance==null)
			instance=new ImageCacheUtil(context);
		return instance;
	}
	
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	
	private ImageCacheUtil(Context context){
		this.memoryCache=new ImageMemoryCache(context);
		this.fileCache=new ImageFileCache();
	}
	
	//将图片添加到缓存
	public void addBitmapToCache(String url, Bitmap bitmap,boolean isAddToFileCache){
		this.memoryCache.addBitmapToCache(url, bitmap);
		if(isAddToFileCache){
			this.fileCache.saveBitmap(bitmap, url);
		}
	}
	
	//从缓存中取图片
	public Bitmap getBitmapFromCache(String url){
		Bitmap bitmap;
		if((bitmap=this.memoryCache.getBitmapFromCache(url))!=null)
			return bitmap;
		return this.fileCache.getImage(url);
		
	}
	
	//获取ImageFileCache对象
	public ImageFileCache getImageFileCache(){
		return this.fileCache;
	}
	
}
