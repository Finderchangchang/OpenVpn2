package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private EditText mEmailView;
    private EditText mPasswordView;
    SharedPreferences sharedPreferences;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mQueue = Volley.newRequestQueue(this);

    }

    String email;
    String password;

    //time是剩余时间，remain是剩余可用VPN流量
    private void attemptLogin() {
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            load();
        }
    }

    private void load() {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/user/login_api.php?user=" + email + "&pass=" + password;//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (("success").equals(status)) {
                        String time = response.getString("time");
                        String left = response.getString("left");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username_edit", email);
                        editor.putString("password_edit", password);
                        editor.putString("time", time);
                        editor.putString("left", left);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {

                    }
                    Toast.makeText(LoginActivity.this, response.getString("notice"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(req);
    }
}

