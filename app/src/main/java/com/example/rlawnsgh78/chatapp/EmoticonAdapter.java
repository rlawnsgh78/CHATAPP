package com.example.rlawnsgh78.chatapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;

import io.socket.client.Socket;

/**
 * Created by rlawn on 2016-11-24.
 */

public class EmoticonAdapter extends BaseAdapter {
    Socket mSocket;
    private LayoutInflater layoutInflater;
    private String friendNickname;
    private DialogPlus dialogPlus;

    public EmoticonAdapter(Context context, String friendNickname) {
        layoutInflater = LayoutInflater.from(context);
        mSocket = SocketIOManager.getInstance().mSocket;
        this.friendNickname = friendNickname;
    }
    public void setDialogPlus(DialogPlus dialogPlus){
        this.dialogPlus = dialogPlus;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {

            view = layoutInflater.inflate(R.layout.item_emoticon, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_emoticon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Context context = parent.getContext();
        switch (position) {
            case 0:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_box_close);
                break;
            case 1:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_box_open);
                break;
            case 2:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_box_semi);
                break;
            case 3:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_corcodile);
                break;
            case 4:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_peterpen);
                break;
            case 5:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_pirate);
                break;
            case 6:
                viewHolder.imageView.setImageResource(R.drawable.emoticon_sleep);
                break;
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message("emoticon~!@" + position, SocketIOManager.getInstance().mLogin.nickname, friendNickname, 1);
                Gson gson = new Gson();
                String json = gson.toJson(message);
                if(!mSocket.connected()){
                    mSocket.connect();
                    try {
                        Thread.sleep(300);
                    }catch (Exception e){

                    }
                }
                mSocket.emit("SendMessage", json);
                dialogPlus.dismiss();
            }
        });

        return view;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
