package com.example.example;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CommonAPI {
	private final static String TAG = "CommonAPI";
	private final static String LOG_TAG = "com.intretech.app.smarttouch";
	private static CommonAPI commonAPI=null;
	private final static boolean DEBUG = true;
    protected CommonAPI(){
    	
    }
    public synchronized static CommonAPI getInstance()
	{
		if(commonAPI==null)
		{
			commonAPI=new CommonAPI();
		}
		return commonAPI;
		
	}
    public static int getHeight(View view) //获得某组件的高度
    {
     int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
     int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
     view.measure(w, h);
     return view.getMeasuredHeight();
    }
    
    /**
     * 获取某组件的坐标数组
     * @param widget
     * @return location[0] = X-Coordinate, location[1] = Y-Coordinate
     */
    public static int[] getWidgetPosArray(View widget){
    	int[] location = new int[2];
    	widget.getLocationOnScreen(location);
		return location;
    }
    
    /**
     * 获取手机顶部状�?�栏的高�?
     * @param activity
     * @return int statusBarHeight
     */
    public static int getStatusBarHeight(Activity activity){
    	Rect frame = new Rect();
    	activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {  
        // 获取ListView对应的Adapter  
        ListAdapter listAdapter = listView.getAdapter();  
        if(listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for(int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目  
            View listItem = listAdapter.getView(i, null, listView);
            if(listItem!=null){
            	listItem.measure(0, 0); // 计算子项View 的宽�?  
            	totalHeight += listItem.getMeasuredHeight(); // 统计�?有子项的总高�?  
            }
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
        // listView.getDividerHeight()获取子项间分隔符占用的高�?  
        // params.height�?后得到整个ListView完整显示�?要的高度  
        listView.setLayoutParams(params);  
    }  
    /**
     * 获取屏幕的宽度和高度
     * @param context
     * @return screenDimen[0] = width, screenDimen[1] = height
     */
    public static int[] getScreenDimenArray(Context context){
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	int width = wm.getDefaultDisplay().getWidth();
    	int height = wm.getDefaultDisplay().getHeight();
    	int[] screenDimen = new int[]{width, height};
		return screenDimen;
    }
    
    public int getVersionCode(Context context) {
        int verCode = -1;
        try {
                verCode = context.getPackageManager().getPackageInfo(
                                "com.intretech.app.smarttouch", 0).versionCode;
        } catch (NameNotFoundException e) {
                CommonAPI.PrintLog(TAG, e.getMessage());
        }
        return verCode;
}  
 
    public String getVersionName(Context context) {
    String versionName = null;
    try {
    	versionName = context.getPackageManager().getPackageInfo(
                            "com.intretech.app.smarttouch", 0).versionName;
    } catch (NameNotFoundException e) {
            CommonAPI.PrintLog(TAG, e.getMessage());
    }
    return versionName;
}
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
	/**
	 * bitmap转为base64
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		try{
			
			byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
			return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * BASE64 编码
	 * 
	 * @param s
	 * @return
	 */
	public static String base64EncodeBuffer(byte[] base64Data)
	{
		try{
			return base64Data == null?null:Base64.encodeToString(base64Data, Base64.DEFAULT);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	
	public static byte[] base64DecodeBufferToByte(String base64String)
	{
		if(base64String==null)
		{
			return null;
		}
		byte[] result;
		try{
			return result =Base64.decode(base64String, Base64.DEFAULT);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String base64DecodeBuffer(String base64String)
	{
		if(base64String==null)
		{
			return null;
		}
		byte[] result;
		try{
			result =Base64.decode(base64String, Base64.DEFAULT);
			return new String(result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,  
                    int idUnable) {  
            StateListDrawable bg = new StateListDrawable();  
            Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);  
            Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);  
            Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);  
            Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);  
            bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);  
            bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);  
            bg.addState(new int[] { android.R.attr.state_enabled }, normal);  
            bg.addState(new int[] { android.R.attr.state_focused }, focused);  
            bg.addState(new int[] { android.R.attr.state_window_focused }, unable);  
            bg.addState(new int[] {}, normal);  
            return bg;  
    }  
/*	public int AnalyzeVoice(ArrayList<String> matches) {
		for (int i = 0; i < matches.size(); i++) {
			if (matches.get(i).contains("�?�?") == true) {
				return 1;
			} else if (matches.get(i).contains("关灯") == true) {
				return 2;
			} else if (matches.get(i).contains("�?�?") == true) {
				return 3;
			} else if (matches.get(i).contains("关窗") == true) {
				return 4;
			}
		}
		return 0;
	}*/
	public static void PrintLog(String content) {
		if(content!=null&& DEBUG == true)
		{
			Log.i(LOG_TAG,content);
		}
		 
	}
	public static void PrintLog(String content,int logType) {
		if(content!= null && DEBUG == true)
		{
			if(logType==0)
			{
			    Log.i(LOG_TAG,content);
			}else
			{
				  Log.e(LOG_TAG,content);
			}
		}
		 
	}
	public static void PrintLog(String type, String content) {
		if(content!=null && DEBUG == true)
		{
			Log.i(LOG_TAG,type+content);
		}
	}
/*	public String getMacAddress(Context ct)
    {
    	
		WifiManager wifiManager = (WifiManager) ct.getSystemService(Context.WIFI_SERVICE);
		if(wifiManager.isWifiEnabled()==false){
			WifiAdmin.GetInstance(ct).setWifiApEnabled(false);
			wifiManager.setWifiEnabled(true); 
		}
		return WifiAdmin.GetInstance(ct).GetMacAddress();
    }*/
	public String getLocalIpAddress() {
		String ipaddress = "";

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = ipaddress + ";"
								+ inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			CommonAPI.PrintLog("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}
    public String analyzeVoice(ArrayList<String> matches)
    {
    	String temp="";
    	for(int i=0;i<matches.size();i++)
    	{
    		temp +=matches.get(i);
    		if(i==4)
    		{
    			break;
    		}
    		temp +="-";
    	}
    	return temp;
    }
    // 写数据到SD中的文件
		public void writeFileSdcardFile(String fileName, String write_str) {

			try {
				FileOutputStream fout = new FileOutputStream(fileName,false);//加true参数表示追加而不是覆�?
				byte[] bytes = write_str.getBytes();

				fout.write(bytes);
				
				fout.close();
			} catch (Exception e) {
				CommonAPI.PrintLog(TAG, "写文件出�?");
				e.printStackTrace();
			}
		}
	public void addEventToCalander(Context context) {

		String calanderURL = ""; 
	      String calanderEventURL = "";  
	      String calanderRemiderURL = "";  
	    //为了兼容不同版本的日�?,2.2以后url发生改变  
	        if(Integer.parseInt(Build.VERSION.SDK) >= 8){  
	            calanderURL = "content://com.android.calendar/calendars";  
	            calanderEventURL = "content://com.android.calendar/events";  
	            calanderRemiderURL = "content://com.android.calendar/reminders";  
	  
	        }else 
	        {
	            calanderURL = "content://calendar/calendars";  
	            calanderEventURL = "content://calendar/events";  
	            calanderRemiderURL = "content://calendar/reminders";   
	        }
		// 获取要出入的gmail账户的id
		String calId = "ums";
		Cursor userCursor = context.getContentResolver().query(Uri.parse(calanderURL),
				null, null, null, null);
		if (userCursor.getCount() > 0) {
			userCursor.moveToFirst();
			calId = userCursor.getString(userCursor.getColumnIndex("_id"));
		}

       
        ContentValues event = new ContentValues();  
        event.put("title", "发布会测试标�?");  
        event.put("description", "测试添加事件的详细信�?");  
        //插入hoohbood@gmail.com这个账户  
        event.put("calendar_id",calId);  
          
        Calendar mCalendar = Calendar.getInstance();  
        mCalendar.set(Calendar.HOUR_OF_DAY,16);  
        long start = mCalendar.getTime().getTime();  
        mCalendar.set(Calendar.HOUR_OF_DAY,17);  
        long end = mCalendar.getTime().getTime();  
          
        event.put("dtstart", start);  
        event.put("dtend", end);  
        event.put("hasAlarm",1);  
          
        Uri newEvent = context.getContentResolver().insert(Uri.parse(calanderEventURL), event);  
        long id = Long.parseLong( newEvent.getLastPathSegment() );  
        ContentValues values = new ContentValues();  
        values.put( "event_id", id );  
        //提前10分钟有提�?  
        values.put( "minutes", 10 );  
        context.getContentResolver().insert(Uri.parse(calanderRemiderURL), values);  
        showTextBox(context,"插入事件成功!!!");
        //Toast.makeText(CalendarDemo.this, "插入事件成功!!!", Toast.LENGTH_LONG).show();  
		
	}
	 public void setSmartCoreIP(Context context, String smartCoreIP) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
		   
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putString("smartCoreIP", smartCoreIP);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}
	 public String getSmartCoreIP(Context context) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getString("smartCoreIP", "0.0.0.0"));
		}
	 public void setSmartCoreName(Context context, String scMac,String smartCoreName) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
		 if(smartCoreName==null)
		 {
			 return;
		 }
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putString(scMac, smartCoreName);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}
	 public String getSmartCoreName(Context context,String scMac) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getString(scMac, "智能家庭中心"));
		}
	 public void setSmartCoreSWVersion(Context context, int smartCoreSWVersion) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putInt("smartCoreSWVersion", smartCoreSWVersion);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}
	 public int getSmartCoreSWVersion(Context context) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getInt("smartCoreSWVersion", 0));
		}
	 public void setRouterMac(Context context, String routerMac) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putString("routerMac", routerMac);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}
	 public String getRouterMac(Context context) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getString("routerMac", "0.0.0.0.0.0"));
		}
	
	 public void setSmartCoreMacAddress(Context context, String smartCoreMacAddress) {
			// 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putString("smartCoreMacAddress", smartCoreMacAddress);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
	}
	 
	 public String getUserID(Context context) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		return (share.getString("userID", "0"));
	}
	 public void setUserID(Context context, String userID) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putString("userID", userID);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}

	 public void setUserName(Context context, String userName) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		Editor editor = share.edit();// 取得编辑�?
		editor.putString("userName", userName);// 存储配置 参数1 是key 参数2 是�??
		editor.commit();// 提交刷新数据
	}
	 
	 public boolean getKeepOneCardOpen(Context context) {
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getBoolean("keepOneCardOpen", false));
		}

		public void setKeepOneCardOpen(Context context, boolean enable) {
	
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			Editor editor = share.edit();// 取得编辑�?
			editor.putBoolean("keepOneCardOpen", enable);// 存储配置 参数1 是key 参数2 是�??
			editor.commit();// 提交刷新数据
		}
public void logoutAndCleanData(Context context)
{
	SharedPreferences share = context.getSharedPreferences("perference",
			Activity.MODE_PRIVATE);
	Editor editor = share.edit();// 取得编辑�?
	editor.putString("passwd", "null");// 存储配置 参数1 是key 参数2 是�??
	editor.putString("userName", "null");
	editor.putString("userID", "0");// 存储配置 参数1 是key 参数2 是�??
//	editor.putString("smartCoreMacAddress", AppConfig.DEFAULT_MAC);// 存储配置 参数1 是key 参数2 是�??
	editor.commit();// 提交刷新数据
	

   
	
	/*DeviceRenameDBManage deviceRename;
	deviceRename=new DeviceRenameDBManage(context);
	deviceRename.open();
	if(deviceRename.tableIsExist(DeviceRenameDBManage.DB_TABLE_DeviceRename)){//判断表是否存�?
		deviceRename.deleteAllData();
	}
	deviceRename.close();
	
	RoomDeviceDBManage mRoomDeviceDBManage;
	
	mRoomDeviceDBManage=new RoomDeviceDBManage(context);
	mRoomDeviceDBManage.open();
	if(mRoomDeviceDBManage.tableIsExist(RoomDeviceDBManage.DB_TABLE_Room))
	{
		mRoomDeviceDBManage.deleteRoomAllData();
	}
	if(mRoomDeviceDBManage.tableIsExist(RoomDeviceDBManage.DB_TABLE_RoomDevice))
	{
		mRoomDeviceDBManage.deleteRoomDeviceTableAllData();
	}
	mRoomDeviceDBManage.close();*/
	}
	 public String getUserName(Context context) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		return (share.getString("userName", "null"));
	}
	 public String getPwd(Context context) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getString("passwd", "null"));
		}

	 public void setPwd(Context context, String strPwd) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		Editor editor = share.edit();// 取得编辑�?
		editor.putString("passwd", strPwd);// 存储配置 参数1 是key 参数2 是�??
		editor.commit();// 提交刷新数据
	}
	 public String getFirstUse(Context context,String key) {
			/**
			 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
			 */
			SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
			return (share.getString("firstUse_"+key, null));
	}

	 public void setFirstUse(Context context,String key, String isFirstUse) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		Editor editor = share.edit();// 取得编辑�?
		editor.putString("firstUse_"+key, isFirstUse);// 存储配置 参数1 是key 参数2 是�??
		editor.commit();// 提交刷新数据
	} 

	 //获取本地的时间戳
	 public String getTimestamp(Context context,String key) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
					Activity.MODE_PRIVATE);
		return (share.getString("timestamp_"+key, null));
	}

	 public void setTimestamp(Context context,String key, String timestamp) {
		/**
		 * 得到配置参数的类 参数1 配置参数文件的名�?,没有后缀�? 参数2 文件访问模式 只能是生成这个文件的应用访问
		 */
		SharedPreferences share = context.getSharedPreferences("perference",
				Activity.MODE_PRIVATE);
		Editor editor = share.edit();// 取得编辑�?
		editor.putString("timestamp_"+key, timestamp);// 存储配置 参数1 是key 参数2 是�??
		editor.commit();// 提交刷新数据
	} 
	
	/**
	 * 根据手机的分辨率�? dp 的单�? 转成�? px(像素)
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率�? px(像素) 的单�? 转成�? dp
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	

	

	public void setBackgroudByDrawable(Resources res, int drawableId,
			View view) {
		Bitmap bitmap = BitmapFactory.decodeResource(res, drawableId);
		// bitmap = Bitmap.createBitmap(100, 20, Config.ARGB_8888);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		drawable.setDither(true);
		view.setBackgroundDrawable(drawable);
	}

	/**
	 * 通过URL获取页面内容，保存到�?个字符串种，可以通过这个方法获取XML文档�?
	 */
	public void clearCookies(Context context) {
		// Edge case: an illegal deviceState exception is thrown if an instance of
		// CookieSyncManager has not be created. CookieSyncManager is normally
		// created by a WebKit view, but this might happen if you start the
		// app, restore saved deviceState, and click logout before running a UI
		// dialog in a WebView -- in which case the app crashes
		@SuppressWarnings("unused")
		CookieSyncManager cookieSyncMngr = CookieSyncManager
				.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		/*
		 * File file = CacheManager.getCacheFileBaseDir(); if (file != null &&
		 * file.exists() && file.isDirectory()) { for (File item :
		 * file.listFiles()) { item.delete(); } file.delete(); }
		 * context.deleteDatabase("webview.db");
		 * context.deleteDatabase("webviewCache.db");
		 */
	}
/*	public Boolean HasNFC(Context context) 
	{
		PackageManager pm =context.getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_NFC);
	}*/
	
	public Boolean checkIsIntretechNet(String ssid) 
	{
		if(ssid==null)
			return false;
		if(ssid.toLowerCase().contains("intretech")==true||ssid.toLowerCase().contains("robamxm")==true)
		{
			CommonAPI.PrintLog(TAG,"ssid=" + ssid);
			return true;
		}  
		else{
			return false;
		}
	}
	public void openSysSettings(Context context)
	{
		 clickVibrate(context);
		  Intent intent=null;
          //判断手机系统的版�?  即API大于10 就是3.0或以上版�? 
          if(android.os.Build.VERSION.SDK_INT>10){
        	  intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             // intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
          }else{
              intent = new Intent();
              ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
              intent.setComponent(component);
              intent.setAction("android.intent.action.VIEW");
          }
          context.startActivity(intent);
	}
	
	
	
	

	public void callPhone(Context context, String mobileNumber) {
		// �?要添加打电话权限�?
		// <uses-permission android:deviceName="android.permission.CALL_PHONE" />
		//Intent intent = new Intent();
		
		//intent.setAction("Android.intent.action.CALL");
		//intent.setData(Uri.parse("tel:" + mobileNumber));// mobile为你要拨打的电话号码，模拟器中为模拟器编号也�?
		//context.startActivity(intent);
		Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mobileNumber));
		context.startActivity(intent);
	}
	 private static String readStream(InputStream is) throws IOException {
	        StringBuilder sb = new StringBuilder();  
	        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);  
	        for (String line = r.readLine(); line != null; line =r.readLine()){  
	            sb.append(line);  
	        }  
	        is.close();  
	        return sb.toString();
	    }
	
     public String readFileFromAssets(String fileName,Context cx)
     {
    	 //String fileName = "test.txt"; //文件名字 
    	 String res=""; 
    	 try{ 

    	    //得到资源中的asset数据�?
    	    InputStream in = cx.getResources().getAssets().open(fileName); 

    	    int length = in.available();         
    	    byte [] buffer = new byte[length];        

    	    in.read(buffer);            
    	    in.close();
    	    res = EncodingUtils.getString(buffer, "UTF-8");     
 
    	   }catch(Exception e){ 
    	       e.printStackTrace();         
               return null;
    	    } 
    	 return res;
     }
	   
	 //读SD中的文件
	 public String readFileFromSdcard(String fileName) throws IOException{ 
	   String res=""; 
	   try{ 
	          FileInputStream fin = new FileInputStream(fileName); 

	          int length = fin.available(); 

	          byte [] buffer = new byte[length]; 
	          fin.read(buffer);     

	          res = EncodingUtils.getString(buffer, "UTF-8"); 

	          fin.close();     
	         } 

	         catch(Exception e){ 
	          e.printStackTrace(); 
	         } 
	         return res; 
	 } 
	public static String getStringByUrl(String urlStr) {
		CommonAPI.PrintLog("Deejan getStringByUrl url=", urlStr);
		HttpURLConnection urlConnection = null;
		String result = "";
		
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(30000);
			urlConnection.setReadTimeout(30000);
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			//CommonAPI.PrintLog("Deejan", "before readStream");
			result = readStream(in);
			//CommonAPI.PrintLog("Deejan", "获取到的内容="+result);
		}catch(Exception e)
		{
			CommonAPI.PrintLog("Deejan", "获取网络数据出现异常！以下是具体的异常信息：");
			e.printStackTrace();
		}
		finally {
			if(urlConnection!=null){
			   urlConnection.disconnect();
			   //CommonAPI.PrintLog("Deejan", "urlConnection.disconnect();");
			}
		}

		return result;

	}

	
	
	public void setContactPhoto(ContentResolver c, byte[] bytes,
            long personId) {
        ContentValues values = new ContentValues();
        int photoRow = -1;
        String where = ContactsContract.Data.RAW_CONTACT_ID + " = " + personId
                + " AND " + ContactsContract.Data.MIMETYPE + "=='"
                + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                + "'";
        Cursor cursor = c.query(ContactsContract.Data.CONTENT_URI, null, where,
                null, null);
        int idIdx = cursor.getColumnIndexOrThrow(ContactsContract.Data._ID);
        if (cursor.moveToFirst()) {
            photoRow = cursor.getInt(idIdx);
        }
        cursor.close();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, personId);
        values.put(ContactsContract.Data.IS_SUPER_PRIMARY, 1);
        values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, bytes);
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);

        if (photoRow >= 0) {
            c.update(ContactsContract.Data.CONTENT_URI, values,
                    ContactsContract.Data._ID + " = " + photoRow, null);
        } else {
            c.insert(ContactsContract.Data.CONTENT_URI, values);
        }
    }
	
	public void getAllContacts(Context context) throws Throwable  
    {  
        //获取联系人信息的Uri  
        Uri uri =  ContactsContract.Contacts.CONTENT_URI;  
        //获取ContentResolver  
        ContentResolver contentResolver = context.getContentResolver();  
        //查询数据，返回Cursor  
        Cursor cursor = contentResolver.query(uri, null, null, null, null);  
        while(cursor.moveToNext())  
        {  
            StringBuilder sb = new StringBuilder();  
            //获取联系人的ID  
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));    
            //获取联系人的姓名  
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));   
            //构�?�联系人信息  
            sb.append("contactId=").append(contactId).append(",Name=").append(name);  
            //查询电话类型的数据操�?  
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,    
                    null,    
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,    
                    null, null);    
            while(phones.moveToNext())  
            {  
                String phoneNumber = phones.getString(phones.getColumnIndex(  
                        ContactsContract.CommonDataKinds.Phone.NUMBER));  
                //添加Phone的信�?  
                sb.append(",Phone=").append(phoneNumber);  
            }  
            phones.close();  
              
            //查询Email类型的数据操�?  
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,    
                       null,    
                       ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,    
                       null, null);    
            while (emails.moveToNext())   
            {    
                String emailAddress = emails.getString(emails.getColumnIndex(  
                        ContactsContract.CommonDataKinds.Email.DATA));  
                //添加Email的信�?  
                sb.append(",Email=").append(emailAddress);  
            }   
            emails.close();  
           // Log.i(TAG, sb.toString());  
        }  
        cursor.close();  
    }  
	
	
	public void saveImage(Bitmap bitmap,String savePath)
	{
		File file=new File(savePath);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public void saveByteToFile(byte[] byteIn,String savePath)
	{
		File file=new File(savePath);
        try {
            FileOutputStream out=new FileOutputStream(file);
            out.write(byteIn);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public void saveImageByJPG(Bitmap bitmap,String savePath)
	{
		File file=new File(savePath);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public boolean deleteImageByImagePath(String imagePath)
	{
		File file = new File(imagePath);
	    return file.delete();
	}
	public Bitmap compressImage(String imagePath) {
		int inSampleSize;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 设为true那么将不返回实际的bitmap�?
		// 不给其分配内存空间�?�里面只包括�?些解码边界信息即图片大小描述等信�?,防止图片过大，内存溢�?
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回bm为空"/sdcard/test.jpg"
		options.inJustDecodeBounds = false;
		// 缩放比�?�由于是固定比例缩放，只用高或�?�宽其中�?个数据进行计算即�?
		//if(options.outWidth>options.outHeight)
			inSampleSize = (int) (options.outWidth /200);
		/*else
			inSampleSize = (int) (options.outHeight /300);*/
		if (inSampleSize <= 0)
			inSampleSize = 1;
		options.inSampleSize = inSampleSize;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?
		bitmap = BitmapFactory.decodeFile(imagePath, options);
//		int w = bitmap.getWidth();
//		int h = bitmap.getHeight();
//		CommonAPI.PrintLog("压缩后图片大�?=", w + "*" + h);
		return bitmap;
	}
	//图片亮色变灰�?
	@SuppressWarnings("deprecation")
	public Drawable imageToGrey(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(faceIconGreyBitmap);
		Drawable drawableGrey = bitmapDrawable;
		return drawableGrey;
	}

	// Bitmap to byte[]
	public  byte[] bmpToByteArray(Bitmap bmp) {
		// Default size is 32 bytes
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
	public  byte[] bmpToJPGByteArray(Bitmap bmp) {
		// Default size is 32 bytes
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
   
	// Cursor to bitmap
	public  Bitmap cursorToBmp(Cursor cursor, int columnIndex) {

		byte[] data = cursor.getBlob(columnIndex);
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		} catch (Exception e) {
			return null;
		}
	}

	// 获得圆角图片的方�?
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// 获得带�?�影的图片方�?
	public Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	
	public Bitmap compressImage(Bitmap image) 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这�?100表示不压缩，把压缩后的数据存放到baos�?
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) 
		{	//循环判断如果压缩后图片是否大�?100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos�?
			options -= 10;//每次都减�?10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream�?
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	

	/*
	 * MD5加密返回16位从�?9位到25�?
	 */
	public String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		// 16位加密，从第9位到25�?
		return md5StrBuff.substring(8, 24).toString().toUpperCase();
	}
 public void showToastMsg(Context context, String Msg)
	{
		Toast.makeText(context.getApplicationContext(), Msg, 1).show();
	}
	 public void showTextBox(Context context, String text) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(text);
		AlertDialog.Builder builer = new Builder(context);
		builer.setTitle("提示信息");
		builer.setMessage(sBuffer);
		// 当点取消按钮时进行登�?
		builer.setNegativeButton("关闭提示�?", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}
	 public String getLocalMacAddress(Context cx) {
			WifiManager wifi = (WifiManager) cx
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			
			return info.getMacAddress();
		}
	 public String getRouterMacAddress(Context cx)
		{
			WifiManager mWifi = (WifiManager) cx.getSystemService(Context.WIFI_SERVICE);
			if (mWifi.isWifiEnabled()) {
			WifiInfo wifiInfo = mWifi.getConnectionInfo();
			//String netName = wifiInfo.getSSID(); //获取被连接网络的名称
			return wifiInfo.getBSSID(); //获取被连接网络的mac地址
			}
			return null;
		}

	 //�?测网络是否可�?
 public boolean IsNetworkAvailable(Context context) {
		// CommonAPI.PrintLog("Deejan", "IsNetworkAvailable");
		NetworkInfo networkInfo;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService("connectivity");
		networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			CommonAPI.PrintLog("Deejan", "network available");
			return true;
		} else {
			CommonAPI.PrintLog("Deejan", "network not available");
			return false;
		}
	}

	 public String getWebLinkDateTime(Context context) {
		SharedPreferences share = context.getSharedPreferences("perference",
				Context.MODE_PRIVATE);
		return (share.getString("webLinkDateTime", "2012/1/1 00:00:00"));
	}

	 public void setWebLinkDateTime(Context context, String webLinkVersion) {
		SharedPreferences share = context.getSharedPreferences("perference",
				Context.MODE_PRIVATE);
		Editor editor = share.edit();// 取得编辑�?
		editor.putString("webLinkDateTime", webLinkVersion);// 存储配置 参数1 是key
															// 参数2是�??
		editor.commit();// 提交刷新数据
	}
	public void clickVibrate(Context context)
	{
		Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");//VIBRATOR_SERVICE="vibrator"
		//Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); 
		//long[] pattern = {800, 50, 400, 30}; // OFF/ON/OFF/ON...   
		//vibrator.vibrate(pattern, -1);//-1不重复，�?-1为从pattern的指定下标开始重�?
		//if(vibrator.hasVibrator())
		if(vibrator!=null)
		   vibrator.vibrate(100);//震动100毫秒
	}
	
}


class WatchThread extends Thread {
	private static final String TAG = null;
	Process p;
	boolean over;
	ArrayList<String> stream;

	public WatchThread(Process p) {
		this.p = p;
		over = false;
		stream = new ArrayList<String>();
	}

	public void run() {
		try {
			if (p == null)
				return;
			Scanner br = new Scanner(p.getInputStream());
			while (true) {
				if (p == null || over)
					break;
				while (br.hasNextLine()) {
					String tempStream = br.nextLine();
					if (tempStream.trim() == null
							|| tempStream.trim().equals(""))
						continue;
					stream.add(tempStream);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public ArrayList<String> getStream() {
		return stream;
	}
	
	
}

