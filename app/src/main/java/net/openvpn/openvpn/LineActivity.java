package net.openvpn.openvpn;

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

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    List<Fragment> mList;
    private ViewPager mViewPager;
    TabLayout tab;
    PlaceholderFragment fragment1;
    PlaceholderFragment fragment2;
    PlaceholderFragment fragment3;

    //120.27.224.36:88/cloudapp/appapi.php?c=Linetype&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc
    //120.27.224.36:88/cloudapp/appapi.php?c=Lines&key=cfa4805ba1e2fbfafaa1cca2beca9b823dcf0fcc&username=boss&cid=1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        fragment1 = new PlaceholderFragment();
        fragment2 = new PlaceholderFragment();
        fragment3 = new PlaceholderFragment();
        mList = new ArrayList<>();
        tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("联通"));
        tab.addTab(tab.newTab().setText("移动"));
        tab.addTab(tab.newTab().setText("ok"));
        mList.add(fragment1);
        mList.add(fragment2);
        mList.add(fragment3);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList);
        tab = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        TextView textView;
        Button btn;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            textView = (TextView) rootView.findViewById(R.id.section_label);
            btn = (Button) rootView.findViewById(R.id.btn);
            textView.setText("123");
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> lists;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> list) {
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
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
