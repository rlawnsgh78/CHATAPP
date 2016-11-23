package com.example.rlawnsgh78.chatapp;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket = SocketIOManager.getInstance().mSocket;


        Button btn_Register = (Button) findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        Button btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.on("LoginRes", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        if (args[0].equals(1)) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(getApplication(), FriendListActivity.class);
                                    startActivity(loginIntent);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                MaterialEditText editId = (MaterialEditText) findViewById(R.id.edit_Id);
                MaterialEditText editPassword = (MaterialEditText) findViewById(R.id.edit_Password);

                String id = editId.getText().toString();
                String password = editPassword.getText().toString();

                Login login = new Login(id, password);
                SocketIOManager.getInstance().mLogin = login;
                Gson gson = new Gson();
                String json = gson.toJson(login);
                mSocket.emit("Login", json);



            }
        });
    }
}