package com.smarturano.emojicode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
