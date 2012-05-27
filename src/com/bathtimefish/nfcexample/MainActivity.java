package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.net.Uri;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Button button1 = (Button) findViewById(R.id.button1);
        //Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        /*
        button1.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, BattleActivity.class);
                //startActivity(intent);

            }
        });
        */
        
        button3.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);

            }
        });
        
        button4.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
        		// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Tokyo"));
        		Intent intent = new Intent(MainActivity.this, QrscanActivity.class);
                startActivity(intent);
            }
        });
        
    }
}