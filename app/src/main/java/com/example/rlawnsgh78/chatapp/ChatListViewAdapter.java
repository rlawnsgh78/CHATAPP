package com.example.rlawnsgh78.chatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rlawn on 2016-11-24.
 */

public class ChatListViewAdapter extends BaseAdapter {
    ArrayList<Message> messageArrayList;
    Context context;
    LayoutInflater layoutInflater;

    public ChatListViewAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messageArrayList.get(position);
        boolean myMessageCheck = message.user_nickname.equals(SocketIOManager.getInstance().mLogin.getNickname());
        int res = 0;

            if(myMessageCheck){
                res = R.layout.item_my_chat;
            }else {
                res = R.layout.item_chat;
            }

            convertView = layoutInflater.inflate(res, parent, false);
        

        if(myMessageCheck){
            TextView txtMyChatMessage = (TextView) convertView.findViewById(R.id.txt_my_chat_message);
            txtMyChatMessage.setText(message.message);
        }else {
            TextView txtChatFreindNickname = (TextView) convertView.findViewById(R.id.txt_chat_friend_nickname);
            txtChatFreindNickname.setText(message.user_nickname);

            TextView txtChatMessage = (TextView) convertView.findViewById(R.id.txt_chat_message);
            txtChatMessage.setText(message.message);
        }

        return convertView;
    }
}
