package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
// import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
//import android.os.Handler; 
//import android.net.Uri;

public class WebViewActivity extends Activity {
	private WebView webView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        // webViewの取得
        webView = (WebView)findViewById(R.id.webView1);
        
        // ウェブリソースの既定ディレクトリを設定する
        webView.loadDataWithBaseURL("file:///android_asset/www/", null, null, "utf-8", null);
        //webView.loadDataWithBaseURL("file:///android_asset/www/js", null, "application/javascript", "utf-8", null);
        //webView.loadDataWithBaseURL("file:///android_asset/www/css", null, "text/css", "utf-8", null);
        //webView.loadDataWithBaseURL("file:///android_asset/www/image", null, "image/png", null, null);
        // 右10pxの余白を消す
        webView.setVerticalScrollbarOverlay(true);
        // リンククリックで標準ブラウザが起動するのを防ぐ
        webView.setWebViewClient(new WebViewClient());
        // JavaScriptを使用可能にする
        webView.getSettings().setJavaScriptEnabled(true);
        // Flashなどのプラグインを有効にする
        webView.getSettings().setPluginsEnabled( true );
        
        // JavaScriptインタフェースの追加。
        // JavaScriptからandroidというオブジェクトを扱えるようになります
        //webView.addJavascriptInterface(new WebViewInterface(this), "Android");
        webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        
        // トップページの表示
        webView.loadUrl("file:///android_asset/www/index.html");
        //webView.loadUrl("http://html5test.com");
        
    	try {
    		webView.requestFocus();
    	} catch (Exception e) {
    	}
    	
    }
    
    public class JavaScriptInterface {
    	Context mContext;
    	
        public JavaScriptInterface (Context c) {  
        	mContext = c;
        } 
        
    	/** Show a toast from the web page */
    	public void showToast(String toast) {
    		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    	}
    	
    	// NFC Scan Activity を呼び出す
    	public void callNfcScanner() {
            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
    		// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Tokyo"));
            startActivity(intent);
    	}
    	
    }
    
    // 端末の戻るボタンを押した時に前のページに戻す
    /*
    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event ) {
        if ( event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_BACK
                && webView.canGoBack() == true ) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }
    */
    
}