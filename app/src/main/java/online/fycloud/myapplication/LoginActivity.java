package online.fycloud.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import online.fycloud.myapplication.Util.HttpResponse;
import online.fycloud.myapplication.Util.HttpUtil;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sp;
    public static ImageView iv_verify;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        sp = getPreferences(Activity.MODE_PRIVATE);
        EditText et_user = findViewById(R.id.et_user);
        et_user.setText(sp.getString("username", ""));
        EditText et_passwd = findViewById(R.id.et_passwd);
        et_passwd.setText(sp.getString("passwd", ""));
        findViewById(R.id.btn_submit).setOnClickListener(view -> {
            String userName = et_user.getText().toString();
            String passwd = et_passwd.getText().toString();

            new HttpLoginTask().execute(userName, passwd);
        });
        CheckBox cb_rem = findViewById(R.id.cb_rem);
        cb_rem.setOnCheckedChangeListener(((compoundButton, flag) -> {
            if (flag) {
                sp.edit()
                        .putString("username", et_user.getText().toString())
                        .putString("passwd", et_passwd.getText().toString());
            } else {
                sp.edit()
                        .putString("username", null)
                        .putString("passwd", null);
            }
        }));
    }

    class HttpLoginTask extends AsyncTask<String, Integer, String> {
        public final String API = getResources().getString(R.string.login);

        @Override
        protected String doInBackground(String... params) {
            String userName = params[0];
            String passwd = params[1];
            HttpResponse response = HttpUtil.getInstance().doPost(API, new HashMap<>() {{
                put("username", userName);
                put("password", passwd);
            }});
            JSONObject result = response.getJsonObject();
            return result.getString("msg");
        }

        @Override
        protected void onPostExecute(String data) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("登录提示")
                    .setMessage(data)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
}
