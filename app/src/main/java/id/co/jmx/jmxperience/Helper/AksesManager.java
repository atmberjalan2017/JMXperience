package id.co.jmx.jmxperience.Helper;

/**
 * Created by MATHMKWU on 7/16/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class AksesManager {
    private static final String KEY_TOKEN = "jmxToken";
    private static final String KEY_LOGIN = "isLogin";
    private static final String GCM = "idGcm";

    String email, hp;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context c;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "JmxperiencePref";

    // konstruktor : yg pertama dipanggil : sama dengan nama Class
    public AksesManager(Context c) {
        this.c = c;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit(); // bisa baca dan tulis
    }

    public void createLoginSession(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGIN, true);

        editor.commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public boolean isLogin() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    public void setNama(String nama) {
        editor.putString("nama", nama);
        editor.commit();
    }

    public String getNama() {
        return pref.getString("nama", "");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString("email", "");
    }

    public void setHp(String hp) {
        editor.putString("hp", hp);
        editor.commit();
    }

    public String getHp() {
        return pref.getString("hp", "");
    }

    public void setUserId(String userid) {
        editor.putString("userid", userid);
        editor.commit();
    }

    public String getUserId() {
        return pref.getString("userid", "");
    }

    public void setGcm(String v) {
        editor.putString(GCM, v);
        editor.commit();
    }

    public String getGcm() {
        return pref.getString(GCM, "");
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

}
