package com.example.rlawnsgh78.chatapp;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Type;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class RegisterActivity extends AppCompatActivity {
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSocket = SocketIOManager.getInstance().mSocket;

        final MaterialEditText editRegisterId = (MaterialEditText) findViewById(R.id.edit_Register_Id);
        editRegisterId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final MaterialEditText editRegisterPassword = (MaterialEditText) findViewById(R.id.edit_Register_Password);


        final MaterialEditText editRegisterNickname = (MaterialEditText) findViewById(R.id.edit_Register_Nickname);
        editRegisterNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button btnRegisterDone = (Button) findViewById(R.id.btn_register_done);
        btnRegisterDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSocket.on("RegisterUserRes", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        if (args[0].equals(1)) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplication(), "회원가입에 성공 했습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplication(), "Error : 회원가입에 실패 했습니다.", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });

                String userId = editRegisterId.getText().toString();
                String userPassword = editRegisterPassword.getText().toString();
                String userNickname = editRegisterNickname.getText().toString();

                User user = new User(userId, userPassword, userNickname);

                Gson gson = new Gson();
                String json = gson.toJson(user);

                if(!mSocket.connected()){
                    mSocket.connect();
                    try {
                        Thread.sleep(300);
                    }catch (Exception e){

                    }

                }
                mSocket.emit("RegisterUser", json);
            }
        });

    }
}

