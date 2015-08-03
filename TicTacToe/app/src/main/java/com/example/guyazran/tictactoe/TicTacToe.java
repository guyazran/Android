package com.example.guyazran.tictactoe;

/**
 * Created by guyazran on 8/3/15.
 */
public class TicTacToe {

    public enum CellValue{
        X, O, EMPTY
    }

    public enum MoveResult{
        VALID_MOVE, INVALID_MOVE, VICTORY, DRAW
    }

    private CellValue[] board;
    private boolean isXTurn;

    public TicTacToe(){
        board = new CellValue[9];
        for (CellValue v : board)
            v = CellValue.EMPTY;
        isXTurn = true;
    }

    public MoveResult makeMove(int cell){
        return MoveResult.VALID_MOVE;
    }

    private boolean checkVictory(){
        return false;
    }

    public boolean isXTurn(){
        return isXTurn;
    }
}
