package com.example.rlawnsgh78.chatapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
                if(message.emoticonCheck == 1){
                    res = R.layout.itme_my_chat_emoticon;
                }else {
                    res = R.layout.item_my_chat;
                }

            }else {
                if(message.emoticonCheck == 1){
                    res = R.layout.item_chat_emoticon;
                }else {
                    res = R.layout.item_chat;
                }

            }

            convertView = layoutInflater.inflate(res, parent, false);
        

        if(myMessageCheck){
            if(message.emoticonCheck == 1){
                ImageView imgMyChatEmoticon = (ImageView) convertView.findViewById(R.id.img_my_emoticon);

                if("emoticon~!@0".equals(message.message)){
                    imgMyChatEmoticon.setImageResource(R.drawable.icb);
                }else if("emoticon~!@1".equals(message.message)){
                    imgMyChatEmoticon.setImageResource(R.drawable.icw);
                }

            }else {
                TextView txtMyChatMessage = (TextView) convertView.findViewById(R.id.txt_my_chat_message);
                txtMyChatMessage.setText(message.message);
            }

        }else {
            TextView txtChatFreindNickname = (TextView) convertView.findViewById(R.id.txt_chat_friend_nickname);
            txtChatFreindNickname.setText(message.user_nickname + " : ");
            if(message.emoticonCheck == 1){
                ImageView imgChatEmoticon = (ImageView) convertView.findViewById(R.id.img_chat_emoticon);
                if("emoticon~!@0".equals(message.message)){
                    imgChatEmoticon.setImageResource(R.drawable.icb);
                }else if("emoticon~!@1".equals(message.message)){
                    imgChatEmoticon.setImageResource(R.drawable.icw);
                }
            }else {
                TextView txtChatMessage = (TextView) convertView.findViewById(R.id.txt_chat_message);
                txtChatMessage.setText(message.message);
            }
        }

        return convertView;
    }
}
