package online.fycloud.myapplication.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Response;

public class HttpResponse {
    private Response response;

    public HttpResponse(Response response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return response.isSuccessful();
    }

    public int code() {
        return response.code();
    }

    public Headers getHeaders() {
        return response.headers();
    }

    public String getString() {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJsonObject() {
        try {
            String str = response.body().string();
            return JSON.parseObject(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getBytes() {
        try {
            return response.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
