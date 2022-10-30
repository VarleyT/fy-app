package online.fycloud.myapplication.Util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtil {
    private static OkHttpClient client;
    private static HttpUtil Instance = new HttpUtil();

    private HttpUtil() {
        client = new OkHttpClient.Builder()
                .build();
    }

    public static HttpUtil getInstance() {
        return Instance;
    }

    public HttpResponse doGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return new HttpResponse(submit(request));
    }

    public HttpResponse doPost(String url, Map<String, String> body) {
        String json = JSON.toJSONString(body);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .build();
        return new HttpResponse(submit(request));
    }

    private Response submit(Request request) {
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            Log.e("网络连接", "连接失败！");
            e.printStackTrace();
        }
        return null;
    }
}


