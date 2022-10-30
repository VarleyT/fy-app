package online.fycloud.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.fycloud.myapplication.Util.HttpUtil;

public class FreeGameActivity extends AppCompatActivity {
    public static TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_game);
        getSupportActionBar().hide();
        tv_result = findViewById(R.id.tv_freeGameResult);
        findViewById(R.id.btn_freeGame_submit).setOnClickListener(view -> {
            new FreeGameTask().execute();
        });
    }

    class FreeGameTask extends AsyncTask<Void, Void, List<JSONObject>> {
        public final String API = getResources().getString(R.string.freeGame);

        @Override
        protected List<JSONObject> doInBackground(Void... voids) {
            JSONObject resultData = HttpUtil.getInstance().doGet(API).getJsonObject();
            if (resultData != null) {
                LinkedList<JSONObject> list = new LinkedList<>();
                JSONArray data = resultData.getJSONArray("data");
                for (Object obj : data) {
                    JSONObject item = (JSONObject) obj;
                    list.add(item);
                }
                return list;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<JSONObject> list) {
            String str = "";
            for (JSONObject obj : list) {
                String gameName = obj.getString("gameName");
                String store = obj.getString("store");
                String url = obj.getString("url");
                String startTime = obj.getString("startTime");
                String endTime = obj.getString("endTime");
                str += "\ngameName: " + gameName
                        + "\nstore: " + store
                        + "\nurl: " + url
                        + "\nstartTime: " + startTime
                        + "\nendTime: " + endTime
                        + "\n";
            }
            FreeGameActivity.tv_result.setText(str);
        }
    }
}