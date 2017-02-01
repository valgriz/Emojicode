package com.thatemojiapp.emojicode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.vision.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 1/18/2017.
 */

public class PushArrayAdapter extends ArrayAdapter{

    private TextView chatText;
    private List teMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    Context context;

    public PushArrayAdapter(Context context, int textViewResourceID){
        super(context, textViewResourceID);
        this.context = context;
    }

    public int getCount(){
        return this.teMessageList.size();
    }



    public void add(TEMessage teMessage){
        teMessageList.add(teMessage);
        if(teMessage.left == false){

            final SharedPreferences sharedPref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            int shareCM = sharedPref.getInt("shareCM", 1);
            int copyCM = sharedPref.getInt("copyCM", 0);

            if(shareCM == 1){
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, teMessage.message);
                sendIntent.setType("text/plain");
                getContext().startActivity(Intent.createChooser(sendIntent, "Send Emojicode to").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            if(copyCM == 1){
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Emojicode message", teMessage.message);
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(context, "Message has been copied to clipboard.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, -100);
                toast.show();
            }
        }
        super.add(teMessage);
    }

    public TEMessage getItem(int index){
        return (TEMessage) this.teMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.message_a, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        final TEMessage teMessage = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(teMessage.message);
        if(teMessage.left){
            chatText.setBackgroundResource(R.drawable.chat_bubble_l);
            singleMessageContainer.setGravity(Gravity.LEFT);
            singleMessageContainer.setPadding(0,0,200,0);
        } else {
            chatText.setBackgroundResource(R.drawable.chat_bubble_r);
            singleMessageContainer.setGravity(Gravity.RIGHT);
            singleMessageContainer.setPadding(200,0,0,0);
        }
        singleMessageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Emojicode message", teMessage.message);
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(context, "Message has been copied to clipboard.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, -100);
                toast.show();
            }
        });

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
