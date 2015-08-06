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
    private int moveCount;

    public TicTacToe(){
        board = new CellValue[9];
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
        if (board[cell] == CellValue.EMPTY) {
            moveCount++;
            board[cell] = isXTurn ? CellValue.X : CellValue.O;
            isXTurn = !isXTurn;
            if (moveCount >= 5 && checkVictory(cell))
                return MoveResult.VICTORY;
            if (moveCount == board.length)
                return MoveResult.DRAW;
            return MoveResult.VALID_MOVE;
        }
        return MoveResult.INVALID_MOVE;
    }

    //cell is zero based
    private boolean checkVictory(int cell) {
//        switch (cell) {
//            case 0:
//                if (board[1] == board[cell] && board[2] == board[cell])
//                    return true;
//                if (board[3] == board[cell] && board[6] == board[cell])
//                    return true;
//                if (board[4] == board[cell] && board[8] == board[cell])
//                    return true;
//                break;
//            case 1:
//                if (board[0] == board[cell] && board[2] == board[cell])
//                    return true;
//                if (board[4] == board[cell] && board[7] == board[cell])
//                    return true;
//                break;
//            case 2:
//                if (board[0] == board[cell] && board[1] == board[cell])
//                    return true;
//                if (board[5] == board[cell] && board[8] == board[cell])
//                    return true;
//                if (board[4] == board[cell] && board[6] == board[cell])
//                    return true;
//                break;
//            case 3:
//                if (board[0] == board[cell] && board[6] == board[cell])
//                    return true;
//                if (board[4] == board[cell] && board[5] == board[cell])
//                    return true;
//                break;
//            case 4:
//                if (board[0] == board[cell] && board[8] == board[cell])
//                    return true;
//                if (board[1] == board[cell] && board[7] == board[cell])
//                    return true;
//                if (board[2] == board[cell] && board[6] == board[cell])
//                    return true;
//                if (board[3] == board[cell] && board[5] == board[cell])
//                    return true;
//                break;
//            case 5:
//                if (board[2] == board[cell] && board[8] == board[cell])
//                    return true;
//                if (board[3] == board[cell] && board[4] == board[cell])
//                    return true;
//                break;
//            case 6:
//                if (board[0] == board[cell] && board[3] == board[cell])
//                    return true;
//                if (board[2] == board[cell] && board[4] == board[cell])
//                    return true;
//                if (board[7] == board[cell] && board[8] == board[cell])
//                    return true;
//                break;
//            case 7:
//                if (board[1] == board[cell] && board[4] == board[cell])
//                    return true;
//                if (board[6] == board[cell] && board[8] == board[cell])
//                    return true;
//                break;
//            case 8:
//                if (board[0] == board[cell] && board[4] == board[cell])
//                    return true;
//                if (board[2] == board[cell] && board[5] == board[cell])
//                    return true;
//                if (board[6] == board[cell] && board[7] == board[cell])
//                    return true;
//                break;
//            default:
//                return false;
//        }
//        return false;

        //teacher solution
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

    public void newGame(){
        for (int i = 0; i < 9; i++) {
            board[i] = CellValue.EMPTY;
        }
        isXTurn = true;
        moveCount = 0;
    }
}
