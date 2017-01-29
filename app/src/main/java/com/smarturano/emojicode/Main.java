package com.smarturano.emojicode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class Main extends AppCompatActivity {

    TextView countTextView;
    EditText input;
    PushArrayAdapter pushArrayAdapter;
    ListView listView;
    Boolean free = true;
    Boolean buttonPaste = true;

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
        final ImageButton sendImageButton = (ImageButton) findViewById(R.id.sendImageButton);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countTextView.setText(input.getText().length() + "/160");
                if(free){
                    if(input.getText().length() > 160){
                        String message = input.getText().toString();
                        String ms = message.substring(0, 2);
                        if(!ms.matches(emo_regex)){ //character is an emoji
                            input.setText(input.getText().subSequence(0, input.length()-1));
                            input.setSelection(input.length()-1);

                            Toast toast = Toast.makeText(getApplicationContext(), "Remove Ads to type more   characters.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, -100);
                            toast.show();

                        }
                    }
                }
                if(input.getText().length() == 0){
                    buttonPaste = true;
                    sendImageButton.setBackgroundResource(R.drawable.send_button_paste_0);
                } else {
                    buttonPaste = false;
                    sendImageButton.setBackgroundResource(R.drawable.send_button_decode_0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(buttonPaste){
            sendImageButton.setBackgroundResource(R.drawable.send_button_paste_0);
        } else {
            sendImageButton.setBackgroundResource(R.drawable.send_button_decode_0);
        }

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(buttonPaste){
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        String pasteData = "";
                        if(!(clipboard.hasPrimaryClip())) {
                        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                        } else {
                            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                            pasteData = item.getText().toString();
                        }
                        input.setText(pasteData);
                    } else {
                        pushMessage();
                        sendImageButton.setBackgroundResource(R.drawable.send_button_paste_0);
                    }
            }
        });

        sendImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(buttonPaste){
                        sendImageButton.setBackgroundResource(R.drawable.send_button_paste_1);
                    } else {
                        sendImageButton.setBackgroundResource(R.drawable.send_button_decode_1);
                    }
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    if(buttonPaste){
                        sendImageButton.setBackgroundResource(R.drawable.send_button_paste_0);
                    } else {
                        sendImageButton.setBackgroundResource(R.drawable.send_button_decode_0);
                    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Web Version").setIcon(R.drawable.send_button);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thatemojiapp.com"));
                startActivity(browserIntent);
                return false;
            }
        });
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Tell Friends").setIcon(R.drawable.send_button);
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "\uD83D\uDC67\uD83D\uDE10\uD83E\uDD12\uD83D\uDC7D  \uD83D\uDE08\uD83D\uDE33\uD83D\uDE10  \uD83D\uDE26\uD83D\uDE0C  \uD83D\uDC6E\uD83D\uDE48\uD83D\uDE1E\uD83D\uDE18  \uD83D\uDC7D\uD83D\uDC6E\uD83D\uDE02\uD83D\uDE00\uD83D\uDE00  \uD83D\uDE14\uD83D\uDE15\uD83D\uDE26\uD83D\uDC6E\uD83D\uDE17\uD83D\uDE0A\n" +
                        "\n" +
                        "Decode this message using emojicode!\n" +
                        "http://thatemojiapp.com/");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return false;
            }
        });
        if(free){
            menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Remove Ads").setIcon(R.drawable.send_button);
        }
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Settings").setIcon(R.drawable.send_button);
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Help").setIcon(R.drawable.send_button);
        return super.onCreateOptionsMenu(menu);
    }

    private final String emo_regex = "([[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+])";

    public void pushMessage(){
        if(!input.getText().toString().equals("")){
        String message = input.getText().toString();
        //check if message is emojis or letters
            String ms = new String("");
            if(message.length() >= 2) {
                ms = message.substring(0, 2);
            }
            char mm = message.charAt(0);
            if(ms.matches(emo_regex)){ //character is an emoji
                String ps = Converter.emojiToText(message);
                if(!ps.equals("")) {
                    pushArrayAdapter.add(new TEMessage(true, ps));
                }
            } else if((mm >= 33) && (mm <= 126)){ //character is a letter
                String ps = Converter.textToEmoji(message);
                if(!ps.equals("")) {
                    pushArrayAdapter.add(new TEMessage(false, ps));
                }
            }
        }

        //After message has been converted
        input.setText("");
        countTextView.setText("0/160");
        buttonPaste = true;

    }
}
