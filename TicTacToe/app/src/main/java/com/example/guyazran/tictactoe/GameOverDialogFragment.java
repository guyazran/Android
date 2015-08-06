package com.example.guyazran.tictactoe;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by guyazran on 8/6/15.
 */
public class GameOverDialogFragment extends DialogFragment {

    private String text;
    private  GameOverDialogListener listener;
    String title;

    public void setFragment(String title, String text, GameOverDialogListener listener){
        this.text = text;
        this.listener = listener;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_over_dialog, container);
        TextView txtGameOverText = (TextView)view.findViewById(R.id.txtGameOverText);
        txtGameOverText.setText(text);
        Button btnNewGame = (Button)view.findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onStartNewGame();
                dismiss();
            }
        });
        getDialog().setTitle(title);

        return view;
    }

    public static interface GameOverDialogListener{
        void onStartNewGame();
    }
}
