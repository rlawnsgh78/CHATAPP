package com.example.rlawnsgh78.chatapp;

import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by rlawn on 2016-11-22.
 */

public class SocketIOManager {
    static SocketIOManager instance = null;

    public Socket mSocket;

    public Login mLogin;

    public static SocketIOManager getInstance() {
            if (instance == null) {
                instance = new SocketIOManager();
            }

        return instance;
    }

    public SocketIOManager() {
        try {
            mSocket = IO.socket("http://210.119.32.172:3000");
        } catch (Exception e) {
            Log.d("Error","Socket.io connect errr URI");
        }
        mSocket.connect();

    }

}
