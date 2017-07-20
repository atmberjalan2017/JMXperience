package id.co.jmx.jmxperience;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import id.co.jmx.jmxperience.Helper.AksesManager;

public class TarifActivity extends DasarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AksesManager sesi = new AksesManager(c);
        if(sesi.isLogin()){
            setContentView(R.layout.activity_tarif);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                //aksi ketika ada koneksi internet
                WebView myWebView = (WebView) findViewById(R.id.TarifView);
                myWebView.loadUrl("http://mobile.trisentrautama.com/tarif.php");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            } else {
                WebView myWebView = (WebView) findViewById(R.id.TarifView);
                myWebView.loadUrl("file:///android_asset/404.html");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            }
        }else{
            Intent i = new Intent(c, LoginActivity.class);
            finish();
            startActivity(i);
        }
    }
}
