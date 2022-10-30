package online.fycloud.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import online.fycloud.myapplication.Util.HttpResponse;
import online.fycloud.myapplication.Util.HttpUtil;

public class DouyinParseActivity extends AppCompatActivity {
    public static TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin_parse);
        getSupportActionBar().hide();
        EditText et_dyInput = findViewById(R.id.et_input);
        tv_result = findViewById(R.id.tv_dyResult);
        tv_result.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.btn_dySubmit).setOnClickListener(view -> {
            new DouyinParseTask().execute(et_dyInput.getText().toString());
        });
    }

    class DouyinParseTask extends AsyncTask<String, Void, List<String>> {
        public final String API = getResources().getString(R.string.douyin);

        @Override
        protected List<String> doInBackground(String... datas) {
            List<String> list = new LinkedList<>();
            HttpResponse response = HttpUtil.getInstance().doPost(API, new HashMap<>() {{
                put("url", datas[0]);
            }});
            JSONObject jsonObj = response.getJsonObject();
            String msg = jsonObj.getString("msg");
            if (msg.equals("success")) {
                JSONObject data = jsonObj.getJSONObject("data");
                String desc = data.getString("desc");
                String playUrl = data.getString("play_url");
                list.add(desc);
                list.add(playUrl);
            } else {
                list.add(msg);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> datas) {
            TextView result = DouyinParseActivity.tv_result;
            String str = "";
            for (String s : datas) {
                str += s + "\n";
            }
            result.setText(str);
        }
    }
}