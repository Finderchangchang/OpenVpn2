package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/28.
 */

public class SplashActivity extends Activity {
    SharedPreferences sharedPreferences;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);

        mQueue = Volley.newRequestQueue(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!("").equals(sharedPreferences.getString("username_edit", ""))) {
                    load();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
    }

    private void load() {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/user/login_api.php?user="
                + sharedPreferences.getString("username_edit", "") + "&pass=" + sharedPreferences.getString("password_edit", "");//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (("success").equals(status)) {
                        String time = response.getString("time");
                        String left = response.getString("left");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("time", time);
                        editor.putString("left", left);
                        editor.commit();
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {

                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(req);
    }
}
