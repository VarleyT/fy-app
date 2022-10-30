package online.fycloud.myapplication;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import online.fycloud.myapplication.Util.HttpResponse;
import online.fycloud.myapplication.Util.HttpUtil;

public class RegisterActivity extends AppCompatActivity {
    ImageView iv_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText et_username = findViewById(R.id.et_register_username);
        EditText et_passwd = findViewById(R.id.et_register_passwd);
        EditText et_repasswd = findViewById(R.id.et_register_repasswd);
        EditText et_verify = findViewById(R.id.et_register_verify);
        iv_verify = findViewById(R.id.iv_register_verify);
        Button btn_submit = findViewById(R.id.btn_register_submit);

        btn_submit.setOnClickListener(view -> {
            String userName = et_username.getText().toString();
            String passwd = et_passwd.getText().toString();
            String repasswd = et_repasswd.getText().toString();
            if (!passwd.equals(repasswd)) {
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("两次输入的密码不一致！")
                        .setPositiveButton("确定", null)
                        .show();
                return;
            }
            String verify = et_verify.getText().toString();
            new RegisterTask().execute(userName, passwd, verify);
        });
        iv_verify.setOnClickListener(view -> {
            new VerifyTask().execute();
        });
        new VerifyTask().execute();
    }

    class RegisterTask extends AsyncTask<String, Void, String> {
        private final String API = getResources().getString(R.string.register);

        @Override
        protected String doInBackground(String... strings) {
            String userName = strings[0];
            String passwd = strings[1];
            String verify = strings[2];
            HttpResponse response = HttpUtil.getInstance().doPost(API, new HashMap<>() {{
                put("username", userName);
                put("password", passwd);
                put("verify", verify);
            }});
            JSONObject resultData = response.getJsonObject();
            int code = resultData.getIntValue("code");
            if (code != 200) {
                return resultData.getString("msg");
            } else {
                return resultData.getString("data");
            }
        }

        @Override
        protected void onPostExecute(String msg) {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("提示")
                    .setMessage(msg)
                    .setPositiveButton("确定", null)
                    .show();
        }
    }

    class VerifyTask extends AsyncTask<Void, Void, Bitmap> {
        private final String API = getResources().getString(R.string.verify);

        @Override
        protected Bitmap doInBackground(Void... voids) {
            byte[] bytes = HttpUtil.getInstance().doGet(API).getBytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iv_verify.setImageBitmap(bitmap);
        }
    }
}