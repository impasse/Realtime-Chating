package com.shyling.onlinechat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;

public class ConnectionService extends Service{
    Socket socket;
    ConnectionThread thread;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ConnectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void sendMsg(String msg) {
        try {
            JSONObject json = new JSONObject();
            json.put("type", "msg");
            json.put("content", msg);
            Log.d("DEBUG", json.toString());
            ConnectionThread.bufferedWriter.write(json.toString());
            ConnectionThread.bufferedWriter.flush();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            if(thread.isAlive()){
                thread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isNetworkAvailable(this)) {
            thread = new ConnectionThread(this);
            thread.start();
        } else {
            Toast.makeText(this, "网络连接不可用", Toast.LENGTH_LONG).show();
            stopSelf();
        }
        return Service.START_STICKY;
    }

    public Msg buildMsg(String str) {
        Log.d("DEBUG",str);
        try {
            JSONObject json = new JSONObject(str);
            String type = json.getString("type");
            if ("result".equals(type)) {
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("str",json.getString("value").equals("ok") ? "发送成功" : "发送失败");
                msg.setData(b);
                msg.what = 0x2;
                MainActivity.handler.sendMessage(msg);
            } else if ("msg".equals(type)) {
                return new Msg(json.getString("content"), json.getString("owner"), json.getString("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
