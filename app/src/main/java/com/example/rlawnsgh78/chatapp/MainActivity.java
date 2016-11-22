package com.example.rlawnsgh78.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent loginIntent = new Intent(getApplication(), FriendListActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}