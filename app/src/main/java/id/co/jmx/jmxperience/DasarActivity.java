package id.co.jmx.jmxperience;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;

import com.androidquery.AQuery;

import id.co.jmx.jmxperience.Helper.AksesManager;

/**
 * Created by MATHMKWU on 7/16/2017.
 */
public class DasarActivity extends AppCompatActivity{
    protected Context c;
    protected AQuery kueri;
    protected AksesManager sesi;
    protected AlphaAnimation btnAnimasi=new AlphaAnimation(1F,0.5F);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        c=this;
        kueri=new AQuery(c);
        sesi=new AksesManager(c);
    }
}
