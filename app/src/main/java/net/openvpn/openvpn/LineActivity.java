package net.openvpn.openvpn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    List<PlaceholderFragment> mList;
    private ViewPager mViewPager;
    TabLayout tab;
    PlaceholderFragment fragment1;
    PlaceholderFragment fragment2;
    PlaceholderFragment fragment3;
    RequestQueue mQueue;
    public static LineActivity mInstall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        mInstall = this;
        fragment1 = new PlaceholderFragment();
        fragment2 = new PlaceholderFragment();
        fragment3 = new PlaceholderFragment();
        mList = new ArrayList<>();
        tab = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.container);

        mQueue = Volley.newRequestQueue(this);
        load();
    }

    public static class PlaceholderFragment extends Fragment {
        TextView textView;
        Button btn;
        String vals;
        String ids;

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            textView = (TextView) rootView.findViewById(R.id.section_label);
            btn = (Button) rootView.findViewById(R.id.btn);
            textView.setText(vals);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mInstall, OpenVPNAttachmentReceiver.class);
                    intent.putExtra("data", ids);
                    startActivity(intent);
                }
            });
            return rootView;
        }

        public void setId(String id) {
            ids = id;
        }

        public void setBtn(String val) {
            vals = val;
        }
    }

    //120.27.224.36:88/cloudapp/appapi.php?c=Linetype&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc
    //120.27.224.36:88/cloudapp/appapi.php?c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss&cid=1
    private void load() {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/cloudapp/appapi.php?c=Linetype&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc";//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<MessageModel.MsgBean> str_list = new ArrayList<>();
                MessageModel person = new Gson().fromJson(response.toString(), MessageModel.class);
                if (person.getCode() == 1) {
                    List<MessageModel.MsgBean> list = person.getMsg();
                    if (list.size() > 0) {
                        for (MessageModel.MsgBean bean : list) {
                            mList.add(new PlaceholderFragment());
                            str_list.add(bean);
                        }
                    }
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList, str_list);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    tab.setupWithViewPager(mViewPager);
                    tab.setTabMode(TabLayout.MODE_FIXED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LineActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(req);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<PlaceholderFragment> lists;
        List<MessageModel.MsgBean> str_list;

        public SectionsPagerAdapter(FragmentManager fm, List<PlaceholderFragment> list, List<MessageModel.MsgBean> stringList) {
            super(fm);
            lists = list;
            str_list = stringList;
        }

        @Override
        public Fragment getItem(int position) {
            PlaceholderFragment fragment = lists.get(position);
            fragment.setBtn(str_list.get(position).getName());
            fragment.setId(str_list.get(position).getId());
            return fragment;
        }

        @Override
        public int getCount() {
            return str_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return str_list.get(position).getName();
        }
    }
}
