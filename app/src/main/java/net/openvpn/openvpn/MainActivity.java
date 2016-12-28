package net.openvpn.openvpn;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView time_tv;
    TextView ll_tv;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time_tv = (TextView) findViewById(R.id.time_tv);
        ll_tv = (TextView) findViewById(R.id.ll_tv);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);

        String time = sharedPreferences.getString("time", "");
        String left = sharedPreferences.getString("left", "");
        time_tv.setText("剩余分钟数:" + time);
        ll_tv.setText("流量:" + left);
    }
}
