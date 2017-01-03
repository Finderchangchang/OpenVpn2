package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/12/30.
 */

public class OpenVPNAttachmentReceiver extends Activity {
    String id;
    RequestQueue mQueue;
    SharedPreferences sharedPreferences;
    TextView title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vpn);
        id = getIntent().getStringExtra("data");
        title_tv = (TextView) findViewById(R.id.title_tv);
        mQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        load();
    }

    //120.27.224.36:88/cloudapp/appapi.php?c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss&cid=1
    private void load() {
        String name = sharedPreferences.getString("username_edit", "");
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/cloudapp/appapi.php?" +
                "c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=" + sharedPreferences.getString("username_edit", "") + "&cid=" + id;//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Model person = new Gson().fromJson(response.toString(), Model.class);
                title_tv.setText(person.getMsg().get(0).getTitle());
                FileOutputStream out = null;
                try {
                    out = OpenVPNAttachmentReceiver.this.openFileOutput("demo.ovpn", Context.MODE_PRIVATE);
                    out.write(person.getMsg().get(0).getContent().getBytes("UTF-8"));
                    out.close();
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(req);
    }
}
