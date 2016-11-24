package com.example.rlawnsgh78.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.socket.client.Socket;

/**
 * Created by rlawn on 2016-11-24.
 */

public class EmoticonAdapter extends BaseAdapter {
    Socket mSocket;
    private LayoutInflater layoutInflater;
    private boolean isGrid;

    public EmoticonAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mSocket = SocketIOManager.getInstance().mSocket;
    }

    @Override
    public int getCount() {
        return 6;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {

            view = layoutInflater.inflate(R.layout.item_emoticon, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_emoticon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();
        switch (position) {
            case 0:
                viewHolder.imageView.setImageResource(R.drawable.icb);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSocket.emit("Emoticon");
                    }
                });
                break;
            case 1:
                viewHolder.imageView.setImageResource(R.drawable.icw);
                break;
            default:
                viewHolder.imageView.setImageResource(R.drawable.icw);
                break;
        }

        return view;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
