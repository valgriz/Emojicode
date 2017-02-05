package com.thatemojiapp.emojicode;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.IBinder;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.thatemojiapp.emojicode.util.IabHelper;
import com.thatemojiapp.emojicode.util.IabResult;
import com.thatemojiapp.emojicode.util.Inventory;
import com.thatemojiapp.emojicode.util.Purchase;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class Main extends AppCompatActivity {

    TextView countTextView;
    EditText input;
    PushArrayAdapter pushArrayAdapter;
    ListView listView;
    Boolean buttonPaste = true;

    SharedPreferences sharedPref;

    private static final String TAG = "InAppBilling";
    IabHelper mHelper;
    //static final  String ITEM_SKU = "com.thatemojiapp.removeads";
    static final  String ITEM_SKU = "android.test.purchased";
    String base64EncodedPublicKey;
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
        sharedPref = getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxR0U1jZPbxniuoPXVsTjNZhbjTZFUBgrcgrjZvthSj7Pl+sWEMUZhbLvIeGCyoatuzYNes8kDX/pL81o4KId6LDDgqzDgK3H8pS24rYIodxN59bouy/AoyBL0P408hJHnMY3SKDZXSvMsnkBh4VtSp74VyDNQo9qQo0gCOeasK8C6yPW6pDmjdeXXOk48zNyQ+FlRiHwMhl6koJYkWO0fXsHFyjdSzyHzqDqnsgDKfKbk1xQc2mathLB7cb1J5KOYM30Sy0OCcslpAUHWWfYT0WH53Mh6s6QbjdCF5AZ3S86ArBk85vrdvImGxXt26YOopRNSDU9BnZlB263sRGciwIDAQAB";
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if(!result.isSuccess()){

                } else {

                }
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        if(sharedPref.getBoolean("free", true)) {
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-5725702906096392~2490916261");
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countTextView.setText(input.getText().length() + "/160");
                if(sharedPref.getBoolean("free", true)){
                    if(input.getText().length() > 160){
                        String message = input.getText().toString();
                        String ms = message.substring(0, 2);
                        if(!ms.matches(emo_regex)){ //character is an emoji
                            input.setText(input.getText().subSequence(0, input.length()-1));
                            input.setSelection(input.length()-1);

                            Toast toast = Toast.makeText(getApplicationContext(), "Remove ads to type more characters.", Toast.LENGTH_SHORT);
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
                        input.setSelection(input.length());
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
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Web Version").setIcon(R.drawable.send_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thatemojiapp.com"));
                startActivity(browserIntent);
                return false;
            }
        });
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Tell Friends").setIcon(R.drawable.send_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "\uD83D\uDE07\uD83D\uDE35\uD83D\uDC75\uD83D\uDE13\uD83D\uDE13\uD83E\uDD14\uD83D\uDE10\uD83D\uDE08\uD83D\uDE13  \uD83D\uDE18\uD83D\uDE00  \uD83D\uDE18\uD83D\uDC76\uD83D\uDE26\uD83D\uDE17" +
                        "\n" +
                        "Decode this using emojicode!    \n" +
                        "http://thatemojiapp.com/");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return false;
            }
        });

        if(sharedPref.getBoolean("free", true)){
            final Activity s = this;
            menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Remove Ads").setIcon(R.drawable.send_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mHelper.launchPurchaseFlow(s, ITEM_SKU, 10001, new IabHelper.OnIabPurchaseFinishedListener() {
                        @Override
                        public void onIabPurchaseFinished(IabResult result, Purchase info) {

                        }
                    }, "purchasetoken");
                    return false;
                }
            });
        }
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Settings").setIcon(R.drawable.send_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent settingIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(settingIntent);
                return false;
            }
        });
        menu.add(0, Menu.CATEGORY_CONTAINER, Menu.NONE, "Help").setIcon(R.drawable.send_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent settingIntent = new Intent(getApplicationContext(), Help.class);
                startActivity(settingIntent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if(result.isFailure()){
                return;
            } else if(info.getSku().equals(ITEM_SKU)){
                consumeItem();
            }
        }
    };


    public void consumeItem(){

        mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if(result.isFailure()){

                } else {
                    mHelper.consumeAsync(inv.getPurchase(ITEM_SKU), new IabHelper.OnConsumeFinishedListener() {
                        @Override
                        public void onConsumeFinished(Purchase purchase, IabResult result) {
                            if(result.isSuccess()){
                                //App is now activated
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putBoolean("free", false);
                                editor.commit();
                                Toast toast = Toast.makeText(getApplicationContext(), "App activated.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, -100);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHelper != null){
            mHelper.dispose();
            mHelper = null;
        }
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
                    if(ps.equals("\uD83D\uDE20\uD83D\uDE10\uD83D\uDE3C\uD83D\uDE0C\uD83D\uDC7E  \uD83D\uDE16\uD83D\uDE14  \uD83D\uDE26\uD83D\uDE48\uD83D\uDE27  \uD83D\uDE14\uD83D\uDE16\uD83D\uDE26  \uD83D\uDC80\uD83D\uDE0B\uD83D\uDE40\uD83D\uDE05")){

                        if(sharedPref != null){
                            SharedPreferences.Editor editor = sharedPref.edit();
                            if(sharedPref.getBoolean("free", true)){
                                editor.putBoolean("free", false);
                                editor.commit();
                                Toast toast = Toast.makeText(getApplicationContext(), "App activated.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, -100);
                                toast.show();
                            } else {
                                editor.putBoolean("free", true);
                                editor.commit();
                                Toast toast = Toast.makeText(getApplicationContext(), "App deactivated.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, -100);
                                toast.show();
                            }
                        }
                    }
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
