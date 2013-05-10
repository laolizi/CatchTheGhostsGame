package com.laolizi.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.os.Message;
import android.util.Log;

public class HostPlayer extends Player {
	
	List<Player> playerList;
	
	public HostPlayer() {
		super();
		// TODO Auto-generated constructor stub
		startService();
	}
	
	public void startService(){
		   new Thread(new Runnable(){
               public void run(){
            	   DatagramSocket socket = null;
           		try {
           			socket = new DatagramSocket(4567);
           		} catch (SocketException e1) {
           			// TODO Auto-generated catch block
           			e1.printStackTrace();
           		}
                    byte data [] = new byte[1024];
                    //创建一个空的DatagramPacket对象
                     DatagramPacket packet = 
           		new DatagramPacket(data,data.length);
           		         //使用receive方法接收客户端所发送的数据，
           		         //如果客户端没有发送数据，该进程就停滞在这里
           		         try {
           					socket.receive(packet);
           				} catch (IOException e) {
           					// TODO Auto-generated catch block
           					e.printStackTrace();
           				}
           		         String result = new 
           		String(packet.getData(),packet.getOffset(),
           		packet.getLength());
           		   Log.v("get", result+packet.getAddress());
               }
           }).start();   
	}

	public List<Player>getGhosts(){
		List<Player> ghostList = new ArrayList<Player>();
		for (Player player : playerList) {
            if(player.role == Player.GHOST){
            	ghostList.add(player);
            }
        }
		return ghostList;
	}
	
	public List<Player>getOrdinaries(){
		List<Player> ordinaryList = new ArrayList<Player>();
		for (Player player : playerList) {
            if(player.role == Player.ORDINARY){
            	ordinaryList.add(player);
            }
        }
		return ordinaryList;
	}
	
	public Player getIdiot(){
		for (Player player : playerList) {
            if(player.role == Player.IDIOT){
            	return player;
            }
        }
		return null;
	}

}
