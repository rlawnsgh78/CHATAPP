package com.example.rlawnsgh78.chatapp;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FriendSerchActivity extends AppCompatActivity {
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_serch);
        mSocket = SocketIOManager.getInstance().mSocket;

        Button btnAddFriend = (Button) findViewById(R.id.btn_add_friend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSocket.on("AddFriendRes", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        if(args[0].equals(1)){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"친구추가 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"친구추가 실패 \n 사유 : 해당 닉네임이 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                MaterialEditText editFriendNickname = (MaterialEditText) findViewById(R.id.edit_friendNickname);
                String friendNickname = editFriendNickname.getText().toString();
                AddFriend addFriend = new AddFriend(friendNickname);

                Gson gson = new Gson();
                String json = gson.toJson(addFriend);
                mSocket.emit("AddFriend",json);
            }
        });
    }
}
