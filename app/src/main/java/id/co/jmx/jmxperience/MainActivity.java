package id.co.jmx.jmxperience;

import android.content.Context;
import android.os.Handler;
import android.support.*;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.TableLayout;

import id.co.jmx.jmxperience.Helper.AksesManager;
import id.co.jmx.jmxperience.Helper.JmxHelper;

public class MainActivity extends DasarActivity {
    private SwipeRefreshLayout swipeLayout;
    private static final int PERIOD = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logobar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        AksesManager sesi = new AksesManager(c);
        if (sesi.isLogin()) {
            setContentView(R.layout.activity_main);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                //aksi ketika ada koneksi internet
                WebView myWebView = (WebView) findViewById(R.id.Promo);
                myWebView.loadUrl("http://mobile.trisentrautama.com/iklan.php");
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            } else {
                //aksi ketika tidak ada koneksi internet
                WebView myWebView = (WebView) findViewById(R.id.Promo);
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
        }else {
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
    public  void onKlik (View view){
        view.startAnimation(btnAnimasi);
        //pilih button
        switch (view.getId()){
            //ganti dengan class lain terlebih dahulu, jika belum membuat class tersebut, ganti dengan class ini lagi jika sudah membuat class tersebut. Kode berfungsi untuk pindah ke class tersebut ketika tombol tsb diklik.
            case R.id.btnAgen:
                startActivity(new Intent(c, AgenActivity.class));
                break;
            case R.id.btnTarif:
                startActivity(new Intent(c, TarifActivity.class));
                break;
            case R.id.btnLayanan:
                startActivity(new Intent(c, ServiceActivity.class));
                break;
            case R.id.btnTransaksi:
                startActivity(new Intent(c, HistoriActivity.class));
                break;
            case R.id.btnProfil:
                startActivity(new Intent(c, ProfilActivity.class));
                break;
            case R.id.btnKontak:
                startActivity(new Intent(c, KontakActivity.class));
                break;
            case R.id.btnPickup:
                startActivity(new Intent(c, PickupActivity.class));
                break;
            case R.id.btnTracking:
                startActivity(new Intent(c, TrackingActivity.class));
                break;
            case R.id.btnLogout:
                // tambahkan ini setelah membuat SessionManager
                AksesManager sesi = new AksesManager(c);
                sesi.logout();
                startActivity(new Intent(c, LoginActivity.class));
                finish();
                JmxHelper.pesan(c, "anda telah logout");
                break;
        }
    }
}
