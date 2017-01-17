package com.smarturano.emojicode;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
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

        final ImageButton sendImageButton = (ImageButton) findViewById(R.id.sendImageButton);
        sendImageButton.setBackgroundResource(R.drawable.send_button_decode_0);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        sendImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendImageButton.setBackgroundResource(R.drawable.send_button_decode_1);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendImageButton.setBackgroundResource(R.drawable.send_button_decode_0);
                }
                return false;
            }
        });




    }
}
