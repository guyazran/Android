package com.example.guyazran.usingtablelayout;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;

/**
 * Created by guyazran on 7/27/15.
 */
public class LoginThread extends Thread {

    private String username, password;
    private HashSet<User> users;
    private LoginListener listener;

    public LoginThread(String username, String password, HashSet<User> users, LoginListener listener){
        this.username = username;
        this.password = password;
        this.users = users;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User(username, password);
        if(users.contains(user)){
            listener.loginSuccessful();
        }else {
            listener.invalidUserOrPassword();
        }
    }

    public static interface LoginListener{
        void invalidUserOrPassword();
        void loginSuccessful();
    }
}
