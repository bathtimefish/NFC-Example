package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;

public class WebViewInterface extends Activity  {
	Context mContext;

	/** Instantiate the interface and set the context */
	WebViewInterface(Context c) {
		mContext = c;
	}

	/** Show a toast from the web page */
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}
	
	// NFC Scan Activity を呼び出す
	public void callNfcScanner() {
        //Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Tokyo"));
        startActivity(intent);
	}
	
}
