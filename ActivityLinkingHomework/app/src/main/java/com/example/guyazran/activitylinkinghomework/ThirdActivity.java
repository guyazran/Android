package com.example.guyazran.activitylinkinghomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by guyazran on 7/19/15.
 */
public class ThirdActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void returnToSecondActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("str2", getIntent().getStringExtra("str1") + " and returned from Third Activity");
        setResult(RESULT_OK, intent);
        finish();
    }
}
