package com.example.guyazran.tictactoe;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by guyazran on 8/3/15.
 */
public class TicTacToeOnline implements CheckGameReadyThread.CheckGameReadyListener, CheckForOpponentMoveThread.CheckOpponentForMoveListener {

    public static interface ChangeUIListener{
        void onOpponentMove(int cell);
    }

    public enum CellValue{
        X, O, EMPTY
    }

    public enum MoveResult{
        VALID_MOVE, INVALID_MOVE, VICTORY, DRAW
    }

    public static final int PORT = 3000;
    public static final int JOIN_GAME = 10;
    public static final int MAKE_MOVE = 20;
    public static final int VICTORY = 30;
    public static final int LEAVE_GAME = 40;
    public static final int YOU_ARE_PLAYER_ONE = 50;
    public static final int YOU_ARE_PLAYER_TWO = 60;
    public static final int GAME_IS_FULL = 70;
    public static final int GAME_READY = 80;
    public static final int WAIT_FOR_OTHER_PLAYER = 90;
    public static final int CHECK_FOR_OPPONENT_MOVE = 110;
    public static final int SUCCESS = 120;
    public static final int FAILURE= 130;
    public static final int CHECK_GAME_READY = 140;
    public static final int RESTART_GAME = 150;
    public static final String SERVER_IP = "192.168.1.9";

    private CellValue[] board;
    private boolean isXTurn;
    private int moveCount;
    private boolean playable;
    private boolean gameReady;
    private boolean opponentMove;
    private CheckForOpponentMoveThread checkForOpponentMoveThread;
    private CheckGameReadyThread checkGameReadyThread;
    int player;
    int gamesPlayed;
    ChangeUIListener listener;

    public TicTacToeOnline(ChangeUIListener listener){
        this.listener = listener;
        board = new CellValue[9];
        gamesPlayed = -1;
        gameReady = false;
        opponentMove = false;
        newGame();
    }

    /**
     * Plays a turn of X or O (X plays first and then O alternately)
     * @param cell a number between 1 to 9 that represents a tile on the board
     * @return the result of the move: valid, invalid, victory or draw
     */
    public MoveResult makeMove(int cell){
        cell--;

        if (cell<0 || cell > 8)
            return MoveResult.INVALID_MOVE;
        if (!((player == YOU_ARE_PLAYER_ONE && isXTurn)||(player==YOU_ARE_PLAYER_TWO && !isXTurn))&&!opponentMove)
            return MoveResult.INVALID_MOVE;
        if (board[cell] == CellValue.EMPTY && playable && gameReady){
            moveCount++;
            board[cell] = isXTurn ? CellValue.X : CellValue.O;
            isXTurn = !isXTurn;
            if (moveCount >= 5 && checkVictory(cell)) {
                playable = false;
                gameReady = false;
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        int cell = params[0];
                        boolean valid = false;
                        Socket clientSocket = null;
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            clientSocket = new Socket(SERVER_IP, PORT);
                            //send action (make move)
                            inputStream = clientSocket.getInputStream();
                            outputStream = clientSocket.getOutputStream();
                            outputStream.write(MAKE_MOVE);
                            //send move
                            outputStream.write(cell);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (clientSocket != null) {
                                try {
                                    clientSocket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        return null;
                    }
                }.execute(++cell);
                return MoveResult.VICTORY;
            }
            if (moveCount == board.length)
                return MoveResult.DRAW;
            if (!opponentMove) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        int cell = params[0];
                        boolean valid = false;
                        Socket clientSocket = null;
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            clientSocket = new Socket(SERVER_IP, PORT);
                            //send action (make move)
                            inputStream = clientSocket.getInputStream();
                            outputStream = clientSocket.getOutputStream();
                            outputStream.write(MAKE_MOVE);
                            //send move
                            outputStream.write(cell);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (clientSocket != null) {
                                try {
                                    clientSocket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        return null;
                    }
                }.execute(++cell);
                checkForOpponentMove();
            }else {
                opponentMove = false;
            }
            return MoveResult.VALID_MOVE;

        }
        return MoveResult.INVALID_MOVE;
    }

    //cell is zero based
    private boolean checkVictory(int cell) {
        int row = cell / 3;
        int col = cell % 3;
            if (board[col] == board[col + 3] && board[col] == board[col + 6])
                return true;
        row *= 3;
        if (board[row] == board[row + 1] && board[row] == board[row + 2])
            return true;

        boolean diagonal1 = board[0] != CellValue.EMPTY && board[0] == board[4] && board[0] == board[8];
        boolean diagonal2 = board[2] != CellValue.EMPTY && board[2] == board[4] && board[2] == board[6];

        return diagonal1 || diagonal2;
    }

    public boolean isXTurn(){
        return isXTurn;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void newGame(){
        gamesPlayed++;
        for (int i = 0; i < 9; i++) {
            board[i] = CellValue.EMPTY;
        }
        if (gamesPlayed == 0){
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Socket clientSocket = null;
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        clientSocket = new Socket(SERVER_IP, PORT);
                        //send action (make move)
                        inputStream = clientSocket.getInputStream();
                        outputStream = clientSocket.getOutputStream();
                        outputStream.write(JOIN_GAME);
                        //receive player number
                        player = inputStream.read();
                        checkGameReady();
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (clientSocket != null) {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (gamesPlayed % 2 == 0) {
                        isXTurn = true;
                        if (player == YOU_ARE_PLAYER_TWO){
                            checkForOpponentMove();
                        }
                    }else {
                        isXTurn = false;
                        if (player == YOU_ARE_PLAYER_ONE){
                            checkForOpponentMove();
                        }
                    }
                }
            }.execute();
        } else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Socket clientSocket = null;
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        clientSocket = new Socket(SERVER_IP, PORT);
                        //send action (make move)
                        inputStream = clientSocket.getInputStream();
                        outputStream = clientSocket.getOutputStream();
                        outputStream.write(RESTART_GAME);
                        //send player
                        if (player == YOU_ARE_PLAYER_ONE) {
                            outputStream.write(YOU_ARE_PLAYER_ONE);
                        }else{
                            outputStream.write(YOU_ARE_PLAYER_TWO);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (clientSocket != null) {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (gamesPlayed % 2 == 0) {
                        isXTurn = true;
                        if (player == YOU_ARE_PLAYER_TWO){
                            checkForOpponentMove();
                        }
                    }else {
                        isXTurn = false;
                        if (player == YOU_ARE_PLAYER_ONE){
                            checkForOpponentMove();
                        }
                    }
                }
            }.execute();
        }

        moveCount = 0;
        playable = true;
    }

    void checkGameReady(){
        checkGameReadyThread = new CheckGameReadyThread(this);
        checkGameReadyThread.start();
    }

    @Override
    public void onGameReady() {
        checkGameReadyThread.stopRunning();
        gameReady = true;
    }

    void checkForOpponentMove(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkForOpponentMoveThread = new CheckForOpponentMoveThread(this);
        checkForOpponentMoveThread.start();
    }

    @Override
    public void onOpponentMakeMove(int cell) {
        checkForOpponentMoveThread.stopRunning();
        opponentMove = true;
        listener.onOpponentMove(cell);
    }
}
