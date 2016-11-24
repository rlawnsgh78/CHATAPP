package com.example.rlawnsgh78.chatapp;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {
    Socket mSocket;
    ListView listChat;
    ArrayList<Message> messageArrayList = new ArrayList<Message>();
    ChatListViewAdapter chatListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSocket = SocketIOManager.getInstance().mSocket;
        listChat = (ListView) findViewById(R.id.list_chat);

        chatListViewAdapter = new ChatListViewAdapter(messageArrayList, getApplicationContext());
        listChat.setAdapter(chatListViewAdapter);

        final Gson gson = new Gson();

        mSocket.on("GetMessageListRes", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Type type = new TypeToken<ArrayList<Message>>() {
                }.getType();
                chatListViewAdapter.messageArrayList = gson.fromJson(args[0].toString(), type);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        chatListViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        GetMessage getMessage = new GetMessage(SocketIOManager.getInstance().mLogin.nickname, getIntent().getStringExtra("FreindNickname"));
        String json = gson.toJson(getMessage);
        mSocket.emit("GetMessageList", json);

        mSocket.on("SendMessageRes", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Type type = new TypeToken<Message>() {
                }.getType();
                Message message = gson.fromJson(args[0].toString(), type);

                chatListViewAdapter.messageArrayList.add(message);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        chatListViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        mSocket.on("SendMessageRes" + SocketIOManager.getInstance().mLogin.nickname, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Type type = new TypeToken<Message>() {
                }.getType();
                Message message = gson.fromJson(args[0].toString(), type);

                messageArrayList.add(message);
                chatListViewAdapter.notifyDataSetChanged();
            }
        });

        Button btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editChat = (EditText) findViewById(R.id.edit_chat);
                String messageStr = editChat.getText().toString();
                String userNickname = SocketIOManager.getInstance().mLogin.getNickname();
                String friendNickname = getIntent().getStringExtra("FreindNickname");

                Message message = new Message(messageStr, userNickname, friendNickname);

                Gson gson = new Gson();
                String json = gson.toJson(message);

                mSocket.emit("SendMessage", json);

                editChat.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.emit("");

    }
}
