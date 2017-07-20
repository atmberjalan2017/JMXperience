package id.co.jmx.jmxperience;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import id.co.jmx.jmxperience.Helper.JmxHelper;
import id.co.jmx.jmxperience.Helper.AksesManager;

public class LoginActivity extends DasarActivity {
    private EditText f_txtUsername;
    private EditText f_txtPassword;
    private Button btnLogin;
    private Button btnDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupTampil();
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
    private void setupTampil(){
        f_txtUsername=(EditText) findViewById(R.id.txtUsername);
        f_txtPassword=(EditText) findViewById(R.id.txtPassword);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(btnAnimasi);
                simpanAksi();
            }
        });
        btnDaftar=(Button)findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(btnAnimasi);
              Intent i=new Intent(c,RegisterActivity.class);
              startActivity(i);
            }
        });
    }
    private void simpanAksi(){
        f_txtUsername.setError(null);
        f_txtPassword.setError(null);
        boolean cancel=false;
        View focusview=null;
        //cek username password
        if(JmxHelper.isEmpty(f_txtUsername)){
            f_txtUsername.setError("Username Harus Diisi");
            focusview=f_txtUsername;
            cancel=true;
        }else if(!JmxHelper.isEmailValid(f_txtUsername)){
            f_txtUsername.setError("Username Tidak Sesuai");
            focusview=f_txtUsername;
            cancel=true;
        }
        if(JmxHelper.isEmpty(f_txtPassword)){
            f_txtPassword.setError("Password Harus Diisi");
            focusview=f_txtPassword;
            cancel=true;
        }
        if(cancel){
            focusview.requestFocus();
        }else{
            //proses cek username dan password
            String url=JmxHelper.BASE_URL+"login.php";
            Map<String,String> params=new HashMap<>();
            params.put("userID",f_txtUsername.getText().toString());//userID
            params.put("password",f_txtPassword.getText().toString());//password
            ProgressDialog progressDialog=new ProgressDialog(c);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setInverseBackgroundForced(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Mohon Tunggu Sedang Diproses");
            try {
                //cari tahu url dan parameter yg dikirimkan
                JmxHelper.pre("url:" + url + ",params:" + params.toString());
                //gunakan aquery untuk koneksi
                kueri.progress(progressDialog).ajax(url, params, String.class,
                        new AjaxCallback<String>() {

                            @Override
                            public void callback(String url, String hasil, AjaxStatus status) {
                                //cek apakah hasil adalah null atau tidak
                                if (hasil != null) {
                                    JmxHelper.pre("respon:" + hasil);
                                    //convert ke bentuk json
                                    try {
                                        JSONObject json = new JSONObject(hasil);
                                        //ambil data
                                        String result = json.getString("result");
                                        String pesan = json.getString("msg");
                                        if (result.equalsIgnoreCase("true")) {

                                            //tambahkan session manager nanti disini
                                            String token = "masuk";
                                            AksesManager sesi = new AksesManager(c);
                                            sesi.createLoginSession(token);

                                            //JSONObject jobj = json.getJSONObject("data");
                                            //sesi.setNama(jobj.getString("user_nama"));
                                            //sesi.setEmail(jobj.getString("user_email"));
                                            // sesi.setPhone(jobj.getString("user_hp"));
                                            // sesi.setIdUser(jobj.getString("id_user"));

                                            Intent i = new Intent(c, MainActivity.class);
                                            startActivity(i);

                                            JmxHelper.pesan(c, pesan);
                                        } else {
                                            JmxHelper.pesan(c, pesan);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        JmxHelper.pesan(c, "Error convert json");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        JmxHelper.pesan(c, "Error parsing data");
                                    }

                                }
                                super.callback(url, hasil, status);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                JmxHelper.pesan(c, "Error get Data");
            }
        }
    }

}
