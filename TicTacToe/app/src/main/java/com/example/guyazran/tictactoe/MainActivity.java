package com.example.guyazran.tictactoe;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.TokenWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TicTacToe gameLogic;

    LinearLayout boardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLogic = new TicTacToe();
        boardLayout = (LinearLayout)findViewById(R.id.boardLayout);
        int tagCounter = 1;
        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                TextView textView = new TextView(this);
                textView.setTextSize(50);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setOnClickListener(cellClickListener);
                textView.setBackgroundColor(Color.BLUE);
                textView.setTag(tagCounter++);

                LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(250, 250);
                textViewLayout.setMargins(5, 5, 5, 5);
                row.addView(textView, textViewLayout);
            }
            LinearLayout.LayoutParams rowLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            boardLayout.addView(row, rowLayout);
        }
    }

    private View.OnClickListener cellClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //save turn here because makeMove() changes turn
            boolean xTurn = gameLogic.isXTurn();
            TicTacToe.MoveResult moveResult = gameLogic.makeMove(Integer.valueOf(v.getTag().toString()));
            if (moveResult == TicTacToe.MoveResult.INVALID_MOVE) {
                return;
            }
            else{
                if (xTurn) {
                    ((TextView) v).setText("X");
                } else {
                    ((TextView) v).setText("O");
                }
            }
            if (moveResult == TicTacToe.MoveResult.VICTORY) {
                Toast.makeText(MainActivity.this, (xTurn ? "Player 1" : "Player 2") + " wins!", Toast.LENGTH_LONG).show();
                emptyBoard();
            }
            if (moveResult == TicTacToe.MoveResult.DRAW) {
                Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_LONG).show();
                emptyBoard();
            }
        }
    };

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
    public void emptyBoard(){
        for (int i = 1; i <= 9; i++) {
            TextView textView = (TextView)boardLayout.findViewWithTag(i);
            textView.setText("");
        }
        gameLogic.newGame();
    }
}
