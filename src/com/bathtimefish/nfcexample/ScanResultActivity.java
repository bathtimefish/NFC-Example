package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.widget.Toast;
import android.media.MediaPlayer;
// import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ScanResultActivity extends Activity {

    private Bundle mBundle;
    private MediaPlayer mMediaPlayer;
	private WebView webView;
	private String mNFCID;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanresult);

        mBundle = getIntent().getExtras();
        
        // 音声を再生
        mMediaPlayer = MediaPlayer.create(this, R.raw.success_scan);
        mMediaPlayer.start();

        if (mBundle != null) {
            byte[] nfcIDBytes = mBundle.getByteArray(NfcAdapter.EXTRA_ID);
            String nfcID = "undefined";

            // IDmを8桁と固定する(そうでないカードがきたらエラーを返す)
            //if (nfcIDBytes.length == 8) {
            	//byte [] -> HexString
            	// バイト配列の２倍の長さの文字列バッファを生成。
            	StringBuffer strbuf = new StringBuffer(nfcIDBytes.length * 2);
            	// バイト配列の要素数分、処理を繰り返す。
            	for (int index = 0; index < nfcIDBytes.length; index++) {
            		// バイト値を自然数に変換。
            		int bt = nfcIDBytes[index] & 0xff;
            		// バイト値が0x10以下か判定。
            		if (bt < 0x10) {
            			// 0x10以下の場合、文字列バッファに0を追加。
            			strbuf.append("0");
            		}
            		// バイト値を16進数の文字列に変換して、文字列バッファに追加。
            		strbuf.append(Integer.toHexString(bt));
            	}
            	nfcID = strbuf.toString();
            	mNFCID = nfcID;

            //} else {
            //    Toast.makeText(this, "申し訳ありませんが別のカードをスキャンしてください", Toast.LENGTH_LONG).show();
            //    finish();
            //}

        }
        
        /* WebViewの表示 */
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
        webView.loadUrl("file:///android_asset/www/scanResult.html");
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
    	
    	public String getNfcId() {
    		return mNFCID;
    	}
    	
    	// indexページ Activity を呼び出す
    	public void goIndex() {
            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
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
