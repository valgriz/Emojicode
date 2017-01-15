package com.smarturano.emojicode;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mainView = findViewById(R.id.activity_main);
        mainView.getRootView().setBackgroundColor(getResources().getColor(R.color.colorS15));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final EditText input = (EditText) findViewById(R.id.input);
        input.setSelection(0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final TextView countTextView = (TextView) findViewById(R.id.countTextView);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countTextView.setText(input.getText().length() + "/160");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
