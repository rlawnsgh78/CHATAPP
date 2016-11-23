package com.example.rlawnsgh78.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import io.socket.client.Socket;

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

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.emit("GetFriendList","");
    }
}
