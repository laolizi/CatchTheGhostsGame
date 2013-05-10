package com.laolizi.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class JoinGameActivity extends Activity {
	
    private WifiAdmin mWifiAdmin; 
    private TextView allNetWork; 
    private ListView listView;
    // 扫描结果列表  
    private List<ScanResult> list;  
    private List<ScanResult> roomList; 
    private ScanResult mScanResult;  
    private StringBuffer sb=new StringBuffer();  
    private Context mContext = null;
    
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO : Handle the msg
            // Usually we update UI here.
        	startActivity(new Intent(mContext,ClientPlayingActivity.class));
        }
    };
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        allNetWork = (TextView) findViewById(R.id.allNetWork);
        listView = (ListView) findViewById(R.id.room_listView);
        mContext = this;
        mWifiAdmin = new WifiAdmin(mContext) {
			
			@Override
			public void myUnregisterReceiver(BroadcastReceiver receiver) {
				// TODO Auto-generated method stub
				JoinGameActivity.this.unregisterReceiver(receiver);
			}
			
			@Override
			public Intent myRegisterReceiver(BroadcastReceiver receiver,
					IntentFilter filter) {
				// TODO Auto-generated method stub
				JoinGameActivity.this.registerReceiver(receiver, filter);
				return null;
			}
			
			@Override
			public void onNotifyWifiConnected() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "加入房间成功",
					     Toast.LENGTH_SHORT).show();
				Log.v("tag", "have connected success!");
				Log.v("tag", "###############################");
				Message msg = new Message();
				handler.sendMessage(msg);
			}
			
			@Override
			public void onNotifyWifiConnectFailed() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "加入房间失败",
					     Toast.LENGTH_SHORT).show();
				Log.v("tag", "have connected failed!");
				Log.v("tag", "###############################");
			}
		}; 
		
//        getAllNetWorkList();
        getGameRoomList();
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.room_item, new String[]{"title","info"}, new int[]{R.id.title,R.id.info});
        listView.setAdapter(adapter);
      //添加点击
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub.
				
				//get selected items
			    String selectedValue = roomList.get(arg2).SSID;
			    setTitle("点击第"+selectedValue+"个项目");
                mWifiAdmin.openWifi();  
                mWifiAdmin.getConfiguration();
                mWifiAdmin.addNetwork(mWifiAdmin.createWifiInfo("\"LaoliziGameRoom\"", "123123123", WifiAdmin.TYPE_WPA)); 
			}
        });
    }
	
        private List<Map<String, Object>> getData() {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0;i<roomList.size();i++){
            	ScanResult mRoom = roomList.get(i);
            	 map.put("title", mRoom.SSID);
                 map.put("info", ipAddressToString(mWifiAdmin.getIPAddress()));
                 list.add(map);
            }
           
            return list;
        }

	   public void getAllNetWorkList(){
	    	  // 每次点击扫描之前清空上一次的扫描结果  
	    	if(sb!=null){
	    		sb=new StringBuffer();
	    	}
	    	//开始扫描网络
	    	mWifiAdmin.startScan();
	    	list=mWifiAdmin.getWifiList();
	    	if(list!=null){
	    		for(int i=0;i<list.size();i++){
	    			//得到扫描结果
	    			mScanResult=list.get(i);
	    			sb=sb.append(mScanResult.BSSID+"  ").append(mScanResult.SSID+"   ")
	    			.append(mScanResult.capabilities+"   ").append(mScanResult.frequency+"   ")
	    			.append(mScanResult.level+"\n\n").append(mWifiAdmin.getIPAddress()+"\n\n");
	    		}
	    		allNetWork.setText("扫描到的wifi网络：\n"+sb.toString());
	    	}
	    }
	   
		public static String ipAddressToString(int pIPAddress) {
			final StringBuilder sb = new StringBuilder();
			sb.append(pIPAddress  & 0xff).append('.')
				.append((pIPAddress >>>= 8) & 0xff).append('.')
				.append((pIPAddress >>>= 8) & 0xff).append('.')
				.append((pIPAddress >>>= 8) & 0xff);
			return sb.toString();
		}
	   
	   public void getGameRoomList(){
	    	  // 每次点击扫描之前清空上一次的扫描结果  
	    	if(sb!=null){
	    		sb=new StringBuffer();
	    	}
	    	//开始扫描网络
	    	mWifiAdmin.startScan();
	    	list=mWifiAdmin.getWifiList();
	    	roomList = new ArrayList<ScanResult>();
	    	if(list!=null){
	    		for(int i=0;i<list.size();i++){
	    			//得到扫描结果
	    			mScanResult=list.get(i);
	    			if(mScanResult.SSID.startsWith("\"LaoliziGameRoom\"")){
	    			roomList.add(mScanResult);
	    			}
	    		}
	    		allNetWork.setText("扫描到的房间：\n"+sb.toString());
	    	}
	    }
}
