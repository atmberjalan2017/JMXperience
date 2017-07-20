package id.co.jmx.jmxperience;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import id.co.jmx.jmxperience.Helper.AksesManager;

public class ServiceActivity extends DasarActivity {
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AksesManager sesi = new AksesManager(c);
        if(sesi.isLogin()){
            setContentView(R.layout.activity_service);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                //aksi ketika ada koneksi internet
                final WebView myWebView = (WebView) findViewById(R.id.LayananView);
                myWebView.loadUrl("http://mobile.trisentrautama.com/layanan.php");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            } else {
                WebView myWebView = (WebView) findViewById(R.id.LayananView);
                myWebView.loadUrl("file:///android_asset/404.html");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            }
            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh(){
                    refreshContent();
                }
            });
        }else{
            Intent i = new Intent(c, LoginActivity.class);
            finish();
            startActivity(i);
        }

    }

    public void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                swipeLayout.setRefreshing(false);
            }
        }, 100);
    }
}

