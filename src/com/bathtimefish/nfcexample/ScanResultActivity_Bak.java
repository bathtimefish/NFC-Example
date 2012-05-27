package com.bathtimefish.nfcexample;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class ScanResultActivity_Bak extends Activity {

    private Bundle mBundle;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanresult_bak);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        TextView textView8 = (TextView) findViewById(R.id.textView8);

        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            byte[] nfcIDm = mBundle.getByteArray(NfcAdapter.EXTRA_ID);

            // IDmを8桁と固定する(そうでないカードがきたらエラーを返す)
            if (nfcIDm.length == 8) {
                // 最下位1桁をそれ以外に足し算して（多少だけど）不可逆性を与える。
                // ＊＊＊アルゴリズムがザルなのでIDm漏れる危険性アリ＊＊＊

                int[] param = new int[8];

                for (int i = 0; i < nfcIDm.length; i++) {
                    param[i] = nfcIDm[i] & 0xff; // byte 2 int 変換
                }

                mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                mEditor = mPreferences.edit();
                /* ここで一桁足している */
                for (int i = 0; i < param.length - 1; i++) {
                    param[i] += param[7];
                    mEditor.putInt("PARAM" + i, param[i]);
                }
                param[7] = 0;// 意味あるのかどうか分からない。
                mEditor.commit();

                textView1.setText("データセット完了！");
                textView2.append(String.valueOf(param[0]));
                textView3.append(String.valueOf(param[1]));
                textView4.append(String.valueOf(param[2]));
                textView5.append(String.valueOf(param[3]));
                textView6.append(String.valueOf(param[4]));
                textView7.append(String.valueOf(param[5]));
                textView8.append(String.valueOf(param[6]));

            } else {
                Toast.makeText(this, "申し訳ありませんが別のカードをスキャンしてください", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

    // @Override
    // protected void onNewIntent(Intent intent) {
    // super.onNewIntent(intent);
    //
    // byte[] rawMsgs = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
    //
    // Log.v(TAG, "length:" + rawMsgs.length);
    //
    // for (int i = 0; i < rawMsgs.length; i++) {
    // Log.v(TAG, "rawMsgs[" + i + "]" + rawMsgs[i]);
    // }
    //
    // }
}
