package com.thatemojiapp.emojicode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thatemojiapp.emojicode.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        View mainView = findViewById(R.id.activity_help);
        mainView.getRootView().setBackgroundColor(getResources().getColor(R.color.colorS15));

    }
}
