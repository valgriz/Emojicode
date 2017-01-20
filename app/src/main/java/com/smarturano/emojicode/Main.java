package com.smarturano.emojicode;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    TextView countTextView;
    EditText input;
    PushArrayAdapter pushArrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mainView = findViewById(R.id.activity_main);
        mainView.getRootView().setBackgroundColor(getResources().getColor(R.color.colorS15));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        input = (EditText) findViewById(R.id.input);
        input.setSelection(0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        countTextView = (TextView) findViewById(R.id.countTextView);
        listView = (ListView) findViewById(R.id.messagesScrollList);
        listView.setAdapter(pushArrayAdapter);
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

                    pushMessage();

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
        pushArrayAdapter = new PushArrayAdapter(getApplicationContext(), R.layout.message_a);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(pushArrayAdapter);

        //to scroll the list view to bottom on data change
        pushArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(pushArrayAdapter.getCount() - 1);
            }
        });


    }

    public void pushMessage(){
        pushArrayAdapter.add(new TEMessage(true, input.getText().toString()));
        input.setText("");
        countTextView.setText("0/160");
    }
}
