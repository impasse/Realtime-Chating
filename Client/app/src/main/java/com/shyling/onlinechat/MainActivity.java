package com.shyling.onlinechat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    EditText editText;
    Button btn;
    public static ChatListAdapter chatListAdapter;
    ConnectionService connectionService;
    long timeBack;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        editText = (EditText)findViewById(R.id.edittext);
        btn = (Button)findViewById(R.id.send);
        chatListAdapter = new ChatListAdapter(this);
        connectionService = new ConnectionService();
        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0x1){
                    chatListAdapter.notifyDataSetChanged();
                }else if(msg.what==0x2){
                    Toast.makeText(MainActivity.this,msg.getData().getString("str"),Toast.LENGTH_SHORT).show();
                }
            }
        };


        btn.setOnClickListener(this);
        listView.setAdapter(chatListAdapter);
        startService(new Intent(this, ConnectionService.class));
    }

    @Override
    public void onClick(View v) {
        if(v==btn){
            if(editText.getText()==null || editText.getText().length()==0){
                Toast.makeText(this,"输入为空",Toast.LENGTH_SHORT).show();
            }else {
                connectionService.sendMsg(editText.getText().toString());
                editText.getText().clear();
            }
        }
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if(now-timeBack<2000){
            super.onBackPressed();
            finish();
        }else {
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();
            timeBack = now;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,ConnectionService.class));
    }
}
