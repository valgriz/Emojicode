package com.thatemojiapp.emojicode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.thatemojiapp.emojicode.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        View mainView = findViewById(R.id.activity_settings);
        mainView.getRootView().setBackgroundColor(getResources().getColor(R.color.colorS15));

        final RadioButton rbSetting1Yes = (RadioButton) findViewById(R.id.rbSetting1Yes);
        final RadioButton rbSetting1No = (RadioButton) findViewById(R.id.rbSetting1No);

        final RadioButton rbSetting2Yes = (RadioButton) findViewById(R.id.rbSetting2Yes);
        final RadioButton rbSetting2No = (RadioButton) findViewById(R.id.rbSetting2No);

        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shareCM", Context.MODE_PRIVATE);
        int shareCM = sharedPref.getInt("shareCM", 1);
        int copyCM = sharedPref.getInt("copyCM", 0);

        if(shareCM == 0){
            rbSetting1No.setChecked(true);
        } else {
            rbSetting1Yes.setChecked(true);
        }

        if(copyCM == 0){
            rbSetting2No.setChecked(true);
        } else {
            rbSetting2Yes.setChecked(true);
        }

        rbSetting1Yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if(rbSetting1Yes.isChecked()){
                    editor.putInt("shareCM", 1);
                } else {
                    editor.putInt("shareCM", 0);
                }
                editor.commit();
                displayMessage();
            }
        });


        rbSetting2Yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if(rbSetting2Yes.isChecked()){
                    editor.putInt("copyCM", 1);
                } else {
                    editor.putInt("copyCM", 0);
                }
                editor.commit();
                displayMessage();
            }
        });

    }

    public void displayMessage(){
        Toast toast = Toast.makeText(getApplicationContext(), "Your settings have been saved.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.show();
    }
}
