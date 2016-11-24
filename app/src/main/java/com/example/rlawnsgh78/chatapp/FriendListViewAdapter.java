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
 * Created by rlawn on 2016-11-23.
 */

public class FriendListViewAdapter extends BaseAdapter {
    ArrayList<Friend> friendItemArrayList;
    Context context;
    LayoutInflater layoutInflater;

    public FriendListViewAdapter(ArrayList<Friend> friendItemArrayList, Context context) {
        this.friendItemArrayList = friendItemArrayList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friendItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int res = 0;
            res = R.layout.item_friend;
            convertView = layoutInflater.inflate(res, parent, false);
        }
        final Friend friend = friendItemArrayList.get(position);

        TextView txtFriend = (TextView) convertView.findViewById(R.id.txtFriend);
        txtFriend.setText(friend.friend_nickname);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(context,ChatActivity.class);
                chatIntent.putExtra("FreindNickname",friend.friend_nickname);
                context.startActivity(chatIntent);
            }
        });


//        RelativeLayout areaClockItem = (RelativeLayout)convertView.findViewById(R.id.areaClockItem);
//        areaClockItem.setBackgroundColor(ContextCompat.getColor(context,clockListViewItem.colorBackground));
//
//        ImageView imgTextClock = (ImageView) convertView.findViewById(R.id.imgTextClock);
//        imgTextClock.setImageDrawable(ContextCompat.getDrawable(context,clockListViewItem.imgTextClock));
//
//        ImageView imgToggle = (ImageView) convertView.findViewById(R.id.imgToggle);
//        imgToggle.setImageDrawable(ContextCompat.getDrawable(context,clockListViewItem.imgToggle));

        return convertView;
    }
}
