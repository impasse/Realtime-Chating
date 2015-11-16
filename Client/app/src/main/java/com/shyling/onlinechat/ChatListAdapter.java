package com.shyling.onlinechat;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;


public class ChatListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Msg> msgs;

    class ViewHolder {
        ImageView user, robot;
        TextView time, content;
    }


    public ChatListAdapter(Context context) {
        this.context = context;
        msgs = new ArrayList<>();
    }

    public void onReceive(Msg msg) {
        msgs.add(msg);
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.robot = (ImageView) convertView.findViewById(R.id.robot);
            viewHolder.user = (ImageView) convertView.findViewById(R.id.user);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Msg msg = (Msg) this.getItem(position);
        Log.d("DEBUG",msg.toString());
        if ("user".equals(msg.getOwner())) {
            viewHolder.user.setVisibility(View.VISIBLE);
            viewHolder.robot.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.user.setVisibility(View.INVISIBLE);
            viewHolder.robot.setVisibility(View.VISIBLE);
        }
        viewHolder.time.setText(msg.getTime());
        viewHolder.content.setText(msg.getContent());
        return convertView;
    }
}
