package com.shyling.onlinechat;

import android.os.Looper;

import com.shyling.onlinechat.MainActivity;
import com.shyling.onlinechat.Msg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by shy on 2015/11/16.
 */
public class ConnectionThread extends Thread {
    Socket socket;
    InetSocketAddress address;
    BufferedReader bufferedReader;
    public static BufferedWriter bufferedWriter;
    ConnectionService service;

    public ConnectionThread(ConnectionService service){
        this.service = service;
    }

    @Override
    public void run() {
            try {
                socket = new Socket();
                address = new InetSocketAddress("v.shyling.com", 8088);
                socket.connect(address);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
                while (true) {
                    Msg msg = service.buildMsg(bufferedReader.readLine());
                    if (msg != null && MainActivity.chatListAdapter!=null){
                        MainActivity.chatListAdapter.onReceive(msg);
                        MainActivity.handler.sendEmptyMessage(0x1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
