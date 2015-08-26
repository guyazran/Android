package com.example.guyazran.tictactoe;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guyazran.tictactoe.TicTacToeOnline.MoveResult;

public class OnlineGameActivity extends AppCompatActivity implements GameOverDialogFragment.GameOverDialogListener, TicTacToeOnline.ChangeUIListener {

    TicTacToeOnline game;
    LinearLayout boardLayout;

    int player1Score=0;
    int player2Score=0;
    TextView player1ScoreView;
    TextView player2ScoreView;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
        handler = new Handler();
        game = new TicTacToeOnline(this);
        boardLayout = (LinearLayout)findViewById(R.id.boardLayout);
        player1ScoreView = (TextView)findViewById(R.id.player1Score);
        player2ScoreView = (TextView)findViewById(R.id.player2Score);
        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                ImageView cellView = new ImageView(this);
                cellView.setOnClickListener(cellClickListener);
                cellView.setTag(i * 3 + j + 1);

                LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(275, 275, 1.0f);
                textViewLayout.setMargins(5, 5, 5, 5);
                row.addView(cellView, textViewLayout);
            }
            LinearLayout.LayoutParams rowLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            boardLayout.addView(row, rowLayout);
        }
    }

    private View.OnClickListener cellClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cell = (Integer)v.getTag();
            makeMove(cell, (ImageView)v);
        }
    };

    public void makeMove(int cell, ImageView imageView){
        //save turn here because makeMove() changes turn
        boolean xTurn = game.isXTurn();
        MoveResult moveResult = game.makeMove(cell);
        if (moveResult == MoveResult.INVALID_MOVE) {
            Toast.makeText(getBaseContext(), (game.isPlayable() ? "Invalid move!" : "Start a new game"), Toast.LENGTH_SHORT).show();
        }
        else{
            imageView.setImageResource(xTurn ? R.drawable.x : R.drawable.o);
            if (moveResult != MoveResult.VALID_MOVE) {
                GameOverDialogFragment fragment = new GameOverDialogFragment();
                if (moveResult == MoveResult.VICTORY) {
                    if (xTurn) {
                        player1ScoreView.setText(String.valueOf(++player1Score));
                        fragment.setFragment("We Have a Winner!", "Player 1 wins", OnlineGameActivity.this);

                    } else {
                        player2ScoreView.setText(String.valueOf(++player2Score));
                        fragment.setFragment("We Have a Winner!", "Player 2 wins", OnlineGameActivity.this);
                    }
                } else if (moveResult == MoveResult.DRAW) {
                    fragment.setFragment("Draw", "Start a new game", OnlineGameActivity.this);
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragment.show(fragmentManager, "game over");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startNewGame(View view) {
        startNewGame();
    }

    public void startNewGame(){
        for (int i = 1; i <= 9; i++) {
            ImageView cellView = (ImageView)boardLayout.findViewWithTag(i);
            cellView.setImageDrawable(null);
        }
        game.newGame();
    }

    @Override
    public void onStartNewGame() {
        startNewGame();
    }

    @Override
    public void onOpponentMove(final int cell) {

        class MakeMoveRunable implements Runnable{

            int cell;

            public MakeMoveRunable(int cell){
                this.cell = cell;
            }

            @Override
            public void run() {
                ImageView imageView = (ImageView) boardLayout.findViewWithTag(cell);
                makeMove(cell, imageView);
            }
        }

        handler.post(new MakeMoveRunable(cell));
    }
}
