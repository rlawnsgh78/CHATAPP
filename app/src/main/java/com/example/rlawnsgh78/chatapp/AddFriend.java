package com.example.rlawnsgh78.chatapp;

/**
 * Created by rlawn on 2016-11-23.
 */

public class AddFriend {
    String userId;
    String friendNickname;

    public AddFriend(String friendNickname) {
        this.friendNickname = friendNickname;
        this.userId = SocketIOManager.getInstance().mLogin.getId();
    }
}
