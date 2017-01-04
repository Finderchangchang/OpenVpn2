package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/12/30.
 */

public class OpenVPNAttachmentReceiver extends Activity {
    TextView title_tv;
    MessageModel.MsgBean bean;
    Button az_btn;
    Button qx_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vpn);
        bean = (MessageModel.MsgBean) getIntent().getSerializableExtra("data");
        title_tv = (TextView) findViewById(R.id.title_tv);
        az_btn = (Button) findViewById(R.id.az_btn);
        qx_btn = (Button) findViewById(R.id.qx_btn);
        title_tv.setText(bean.getTitle() + ".ovpn");
        az_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream out;
                try {
                    out = OpenVPNAttachmentReceiver.this.openFileOutput(bean.getTitle() + ".ovpn", Context.MODE_PRIVATE);
                    out.write(bean.getContent().getBytes("UTF-8"));
                    out.close();
                } catch (Exception e) {

                }
            }
        });
        qx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
