package com.example.rlawnsgh78.chatapp;

/**
 * Created by rlawn on 2016-11-24.
 */

public class Message {
    String message;
    String user_nickname;
    String friend_nickname;

    public Message(String message, String user_nickname, String friend_nickname) {
        this.message = message;
        this.user_nickname = user_nickname;
        this.friend_nickname = friend_nickname;
    }
}
