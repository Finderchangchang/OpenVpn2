package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private EditText mEmailView;
    private EditText mPasswordView;
    SharedPreferences sharedPreferences;

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
    }

    String result = "{\"status\":\"success\",\"time\":\"30\",\"remain\":\"1024\"}";

    //time是剩余时间，remain是剩余可用VPN流量
    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
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
            String url = "http:/xxxxx";//所需url
            JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("TAG", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            String remain = "";
            String time = "";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", email);
            editor.putString("userpwd2", password);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("remain", remain);
            intent.putExtra("time", time);
            startActivity(intent);
        }
    }
}

