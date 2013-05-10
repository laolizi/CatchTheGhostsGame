package com.laolizi.game;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class ClientPlayingActivity extends Activity {
	private TextView playerCountText; 
	private Context mContext = null;
	private ClientPlayer player = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        init(); 
    }
    
    public void init(){
    	mContext = this;
    	player = new ClientPlayer();
    	playerCountText = (TextView) findViewById(R.id.player_number); 
    	final WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	  	DhcpInfo info=wifiManager.getDhcpInfo();
	  	player.hostIpAddress = ipAddressToString(info.serverAddress);
	  	player.ipAddress = ipAddressToString(info.ipAddress);
	  	player.hostIp = player.hostIpAddress;
	  	player.joinGame();
    }
    
    
    
	public static String ipAddressToString(int pIPAddress) {
		final StringBuilder sb = new StringBuilder();
		sb.append(pIPAddress  & 0xff).append('.')
			.append((pIPAddress >>>= 8) & 0xff).append('.')
			.append((pIPAddress >>>= 8) & 0xff).append('.')
			.append((pIPAddress >>>= 8) & 0xff);
		return sb.toString();
	}
}
