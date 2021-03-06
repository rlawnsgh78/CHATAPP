package com.example.rlawnsgh78.chatapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melnykov.fab.FloatingActionButton;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FriendListActivity extends AppCompatActivity {
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mSocket = SocketIOManager.getInstance().mSocket;

        FloatingActionButton fabFriend = (FloatingActionButton) findViewById(R.id.fab_friend);
        fabFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent friendSercgIntent = new Intent(getApplication(), FriendSerchActivity.class);
                startActivity(friendSercgIntent);
            }
        });


    }

    FriendListViewAdapter friendListViewAdapter;
    @Override
    protected void onResume() {
        super.onResume();
        final ListView friendList = (ListView) findViewById(R.id.list_friend);

        mSocket.on("GetFriendListRes", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if(args[0]!= null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Friend>>(){}.getType();

                    ArrayList<Friend> friendArrayList = gson.fromJson(args[0].toString(),type);
                    friendListViewAdapter = new FriendListViewAdapter(friendArrayList,getApplicationContext());

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            friendList.setAdapter(friendListViewAdapter);
                            friendListViewAdapter.notifyDataSetChanged();
                        }
                    });

                }else {

                }

            }
        });

        if(!mSocket.connected()){
            mSocket.connect();
            try {
                Thread.sleep(300);
            }catch (Exception e){

            }
        }
        mSocket.emit("GetFriendList",SocketIOManager.getInstance().mLogin.getId());

        mSocket.on("GetFriendListReq", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("GetFriendList",SocketIOManager.getInstance().mLogin.getId());
            }
        });
      //  friendList.setAdapter();

    }
}
