package com.laolizi.game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HostPlayingActivity extends Activity {
	private TextView playerCountText; 
	private Context mContext = null;
	private HostPlayer player = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        init(); 
    }
    
    public void init(){
    	mContext = this;
    	player = new HostPlayer();
    	playerCountText = (TextView) findViewById(R.id.player_number);  
    	playerCountText.setText("Íæ¼ÒÊý£º"+1+"/6");
    	
    }
}
