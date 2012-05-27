package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QrscanActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan);

        final Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                //intent.putExtra("SCAN_MODE", "ONE_D_MODE");
                try {
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException e) {
                    new AlertDialog.Builder(QrscanActivity.this)
                            .setTitle("QR Scaner not found.")
                            .setMessage("Please install QR code scanner")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            TextView textView = (TextView)findViewById(R.id.text);
            if (resultCode == RESULT_OK) {
                final String barcode = intent.getStringExtra("SCAN_RESULT");
                textView.setText(barcode);
            }
        }
    }
	
}
