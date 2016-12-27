package net.openvpn.openvpn;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView txt;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);

        String name = sharedPreferences.getString("username", "");
        String userpwd2 = sharedPreferences.getString("userpwd2", "");
        txt.setText("name:" + name + ":" + userpwd2);
    }
}
