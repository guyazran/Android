package com.example.guyazran.whatsappclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by guyazran on 8/24/15.
 */
public class CheckForMessagesThread extends Thread {

    private String userName, password;
    private boolean go;
    private NewMessagesListener listener;

    public CheckForMessagesThread(String userName, String password, NewMessagesListener listener){
        this.userName = userName;
        this.password = password;
        this.listener = listener;
        go = true;
    }

    public void stopRunning(){
        go = false;
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            while (go) {
                Thread.sleep(1000);
                clientSocket = new Socket(MainActivity.SERVER_IP, MainActivity.PORT);
                //send action (send message)
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                outputStream.write(MainActivity.CHECK_FOR_MESSAGES);
                //send username
                byte[] userNameBytes = userName.getBytes();
                outputStream.write(userNameBytes.length);
                outputStream.write(userNameBytes);
                //send password
                byte[] passwordBytes = password.getBytes();
                outputStream.write(passwordBytes.length);
                outputStream.write(passwordBytes);
                int messageCount = inputStream.read();
                if (messageCount > 0){
                    Message[] newMessages = new Message[messageCount];
                    for (int i = 0; i < messageCount; i++) {
                        int contentLength = inputStream.read();
                        if (contentLength<1)
                            throw new IOException("illegal content length");
                        byte[] contentBytes = new byte[contentLength];
                        int actuallyRead = inputStream.read(contentBytes);
                        if (actuallyRead != contentLength)
                            throw new IOException("illegal content actual length");
                        String content = new String(contentBytes);
                        int senderLength = inputStream.read();
                        if (senderLength < 1)
                            throw new IOException("illegal sender length");
                        byte[] senderBytes = new byte[senderLength];
                        actuallyRead = inputStream.read(senderBytes);
                        if (actuallyRead != senderLength)
                            throw new IOException("illegal sender actual length");
                        String sender = new String(senderBytes);
                        Message msg = new Message(content, sender);
                        newMessages[i] = msg;
                    }
                    if (listener != null){
                        listener.onNewMessages(newMessages);
                    }
                }
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static interface NewMessagesListener{
        void onNewMessages(Message[] newMessages);
    }
}
