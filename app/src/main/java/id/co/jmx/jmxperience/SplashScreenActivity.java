package id.co.jmx.jmxperience;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends DasarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Handler handler=new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(c,MainActivity.class));
            finish();
        }
    },3000L);

}
}
