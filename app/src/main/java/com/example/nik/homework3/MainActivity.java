package com.example.nik.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://anime-market.kiev.ua/market/components/com_virtuemart/shop_image/product/B974.jpg";
    public static final String destFile = "image.jpg";
    public static final String INTENT_FILTER = "IMAGE_DOWNLOADED";

    private ImageView imageView;
    public TextView errorView;

    BroadcastReceiver screenOnReceiver, imageShowReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.picture);
        errorView = (TextView) findViewById(R.id.error);
        File file = new File(getFilesDir(), destFile);
        imageView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(R.string.not_downloaded);

        screenOnReceiver = new ScreenOnReceiver();
        imageShowReceiver = new BroadcastReceiver() {
            private static final String TAG = "imageShowReceiver";
            @Override
            public void onReceive(Context context, Intent intent) {
                File file = new File(getFilesDir(), destFile);
                if (file.exists()) {
                    Log.d(TAG, "file exists");
                    imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    imageView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
            }
        };
        registerReceiver(screenOnReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(imageShowReceiver, new IntentFilter(INTENT_FILTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenOnReceiver);
        unregisterReceiver(imageShowReceiver);
    }
}
