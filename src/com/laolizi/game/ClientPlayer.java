package com.laolizi.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.os.Message;
import android.util.Log;

public class ClientPlayer extends Player {
	String hostIp;
	public ClientPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void joinGame(){
		new Thread(new Runnable(){
            public void run(){
            	DatagramSocket socket = null;
        		try {
        			socket = new  DatagramSocket (4567);
        		} catch (SocketException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		InetAddress serverAddress = null;
        		try {
        			serverAddress = InetAddress.getByName(hostIp);
        		} catch (UnknownHostException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		} 
        		String str = "joinGame";
        		byte data[] = str.getBytes();
        		DatagramPacket  packet = new DatagramPacket (data , data.length , serverAddress , 4567);
        		 try {
        			socket.send(packet);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
        }).start();   
		
	}
	
}
