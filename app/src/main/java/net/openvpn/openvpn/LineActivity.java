package net.openvpn.openvpn;

import android.app.Activity;
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
import android.widget.ListView;
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

public class LineActivity extends FragmentActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    List<PlaceholderFragment> mList;
    private ViewPager mViewPager;
    RequestQueue mQueue;
    public static LineActivity mInstall;
    SharedPreferences sharedPreferences;
    PlaceholderFragment p1;
    PlaceholderFragment p2;
    PlaceholderFragment p3;
    Button yd_btn;
    Button lt_btn;
    Button dx_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        mInstall = this;
        mList = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.container);
        mQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);
//        load();
        yd_btn = (Button) findViewById(R.id.yd_btn);
        lt_btn = (Button) findViewById(R.id.lt_btn);
        dx_btn = (Button) findViewById(R.id.dx_btn);
        p1 = new PlaceholderFragment();
        p2 = new PlaceholderFragment();
        p3 = new PlaceholderFragment();
        mList.add(p1);
        mList.add(p2);
        mList.add(p3);
        loads("1");
        loads("2");
        loads("3");
        changeBtn(1);
        yd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(1);
            }
        });
        lt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(2);
            }
        });
        dx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(3);
            }
        });
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeBtn(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeBtn(int position) {
        mViewPager.setCurrentItem(position - 1);
        switch (position) {
            case 1:
                yd_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                lt_btn.setTextColor(getResources().getColor(R.color.white));
                dx_btn.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                lt_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                yd_btn.setTextColor(getResources().getColor(R.color.white));
                dx_btn.setTextColor(getResources().getColor(R.color.white));
                break;
            case 3:
                dx_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                lt_btn.setTextColor(getResources().getColor(R.color.white));
                yd_btn.setTextColor(getResources().getColor(R.color.white));
                break;
        }
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
            if (list == null) {
                list = new ArrayList<>();
            }
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            lv = (ListView) rootView.findViewById(R.id.lv);
            commonAdapter = new CommonAdapter<MessageModel.MsgBean>(mInstall, list, R.layout.item_bottom) {
                @Override
                public void convert(CommonViewHolder holder, final MessageModel.MsgBean msgBean, int position) {
                    holder.setText(R.id.section_label, msgBean.getTitle());
                    holder.setOnClickListener(R.id.btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mInstall, OpenVPNAttachmentReceiver.class);
                            intent.putExtra("data", msgBean);
                            startActivity(intent);
                        }
                    });
                }
            };
            lv.setAdapter(commonAdapter);

            return rootView;
        }

        public void setId(String id) {
            ids = id;
        }

        public void setList(List<MessageModel.MsgBean> list) {
            this.list = list;
            if (commonAdapter != null) {
                commonAdapter.refresh(list);
            }
        }

        public void setBtn(String val) {
            vals = val;
        }
    }

    //120.27.224.36:88/cloudapp/appapi.php?c=Linetype&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc
    //120.27.224.36:88/cloudapp/appapi.php?c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss&cid=1
    private void load() {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/cloudapp/appapi.php?c=Linetype&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss&cid=1";//所需url
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
//                            loads(bean.getId());
                        }
                    }

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

    private void loads(final String id) {
        String url = "http://" + getString(R.string.ip) + ":" + getString(R.string.port) + "/cloudapp/appapi.php?" +
                "c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=" + sharedPreferences.getString("username_edit", "") + "&cid=" + id;//所需url
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<MessageModel.MsgBean> str_list = new ArrayList<>();
                MessageModel person = new Gson().fromJson(response.toString(), MessageModel.class);
                switch (id) {
                    case "1":
                        p1.setList(person.getMsg());
                        break;
                    case "2":
                        p2.setList(person.getMsg());
                        break;
                    case "3":
                        p3.setList(person.getMsg());
                        break;
                }
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

        public SectionsPagerAdapter(FragmentManager fm, List<PlaceholderFragment> list) {
            super(fm);
            lists = list;
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return str_list.get(position).getTitle();
        }
    }
}
