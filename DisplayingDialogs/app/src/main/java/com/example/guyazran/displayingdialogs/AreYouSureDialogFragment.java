package com.example.guyazran.displayingdialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by guyazran on 7/30/15.
 */
public class AreYouSureDialogFragment extends DialogFragment {
    Button btnYes;
    Button btnNo;
    String title;
    AreYouSureDialogListener listener;

    public void setFragment(String title, AreYouSureDialogListener listener){
        this.title = title;
        this.listener = listener;
    }

    public static interface AreYouSureDialogListener{
        //my methods
        void onSelectYes();
        void onSelectNo();

        //teacher method
        void onFinishSelection(boolean answer);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_areyousure_dialog, container);
        btnYes = (Button)view.findViewById(R.id.btnYes);
        btnNo = (Button)view.findViewById(R.id.btnNo);

        //my solution
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onSelectYes();
                    dismiss();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onSelectNo();
                    dismiss();
                }
            }
        });

        //teacher solution
        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    boolean answer = v.getId()== R.id.btnYes;
                    listener.onFinishSelection(answer);
                    dismiss();
                }
            }
        };

        btnYes.setOnClickListener(btnListener);
        btnNo.setOnClickListener(btnListener);

        getDialog().setTitle(title);

        return view;
    }
}
