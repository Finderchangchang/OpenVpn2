package net.openvpn.openvpn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LineActivity extends FragmentActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    List<PlaceholderFragment> mList;
    private ViewPager mViewPager;
    RequestQueue mQueue;
    public static LineActivity mInstall;
    LinearLayout title_ll;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        mInstall = this;
        title_ll = (LinearLayout) findViewById(R.id.title_ll);
        mList = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.container);
        mQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        load();
    }

    public static class PlaceholderFragment extends Fragment {
        String vals;
        String ids;
        ListView lv;
        List<MessageModel.MsgBean> list;
        CommonAdapter<MessageModel.MsgBean> commonAdapter;

        @Override

        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            list = new ArrayList<>();
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            lv = (ListView) rootView.findViewById(R.id.lv);
            commonAdapter = new CommonAdapter<MessageModel.MsgBean>(mInstall, list, R.layout.item_bottom) {
                @Override
                public void convert(CommonViewHolder holder, MessageModel.MsgBean msgBean, int position) {
                    holder.setText(R.id.section_label, msgBean.getName());
                }
            };
            lv.setAdapter(commonAdapter);
//                    Intent intent = new Intent(mInstall, OpenVPNAttachmentReceiver.class);
//                    intent.putExtra("data", ids);
//                    startActivity(intent);

            return rootView;
        }

        public void setId(String id) {
            ids = id;
        }

        public void setList(List<MessageModel.MsgBean> list) {
            this.list = list;
            commonAdapter.refresh(list);
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
//                            Button btn = new Button(mInstall);
//                            btn.setWidth(0);
//                            btn.setText(bean.getName());
//                            title_ll.addView(btn);
                            loads(bean.getId());
                        }
                    }
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList, str_list);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
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

    private void loads(String id) {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/cloudapp/appapi.php?" +
                "c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss" + sharedPreferences.getString("username_edit", "") + "&cid=" + id;//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MessageModel person = new Gson().fromJson(response.toString(), MessageModel.class);
//                FileOutputStream out = null;
//                try {
//                    out = openFileOutput("demo.ovpn", Context.MODE_PRIVATE);
//                    out.write(person.getMsg().get(0).getContent().getBytes("UTF-8"));
//                    out.close();
//                } catch (Exception e) {
//
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s = "";
            }
        });
        mQueue.add(req);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
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
            fragment.setList(str_list);
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
