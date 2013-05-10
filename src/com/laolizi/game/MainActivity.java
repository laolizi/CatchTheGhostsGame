package com.laolizi.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private Button createGameButtom;  
    private Button joinGameButtom;  
    private Context mContext = null;  
    private WifiManager wifiManager = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); 
    }
    
    public void init(){
    	mContext = this;
    	createGameButtom = (Button) findViewById(R.id.create_game_btn);  
    	joinGameButtom = (Button) findViewById(R.id.join_game_btn);  
    	createGameButtom.setOnClickListener(this);
    	joinGameButtom.setOnClickListener(this);
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.create_game_btn:
			createGame();
			break;
       case R.id.join_game_btn:
    	   startActivity(new Intent(this,JoinGameActivity.class));
			break;
		default:
			break;
		}
		
	}
	
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO : Handle the msg
            // Usually we update UI here.
        	Toast.makeText(MainActivity.this, "OK",Toast.LENGTH_SHORT).show();
        	startActivity(new Intent(mContext,HostPlayingActivity.class));
        }
    };
	
    public void createGame(){
    	//如果是打开状态就关闭，如果是关闭就打开
    	WifiApAdmin wifiAp = new WifiApAdmin(mContext);  
    	wifiAp.uiUpdateHandler = handler;
    	Toast.makeText(MainActivity.this, "建立游戏中，请稍等。。。",Toast.LENGTH_LONG).show();
        wifiAp.startWifiAp("\"LaoliziGameRoom\"", "123123123");
    	
    }

    
}
