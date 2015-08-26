package com.example.guyazran.tictactoe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by guyazran on 8/26/15.
 */
public class CheckForOpponentMoveThread extends Thread {

    private boolean go;
    CheckOpponentForMoveListener listener;

    public CheckForOpponentMoveThread(CheckOpponentForMoveListener listener){
        this.listener = listener;
        go = true;
    }

    void stopRunning(){
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
                clientSocket = new Socket(TicTacToeOnline.SERVER_IP, TicTacToeOnline.PORT);
                //send action (check for opponent move)
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                outputStream.write(TicTacToeOnline.CHECK_FOR_OPPONENT_MOVE);
                //receive status
                if (inputStream.read() == TicTacToeOnline.SUCCESS){
                    int cell = inputStream.read();
                    listener.onOpponentMakeMove(cell);
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

    public static interface CheckOpponentForMoveListener {
        void onOpponentMakeMove(int cell);
    }
}
