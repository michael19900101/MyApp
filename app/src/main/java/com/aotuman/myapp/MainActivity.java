package com.aotuman.myapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.aotuman.myapp.constants.Constants;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        Constants.screenWidth = dm.widthPixels;
        Log.e("jjjj","screewidth:"+Constants.screenWidth);

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new DashLineFragment();
                    case 1:
                        return new LineViewFragment();
                    case 2:
                        return new TotalViewFragment();
                    case 3:
                        return new VerticalLineViewFragment();
                    case 4:
                        return new ElementaryFragment();
                    case 5:
                        return new ElementaryFragment();
                    case 6:
                        return new ElementaryFragment();
                    case 7:
                        return new ElementaryFragment();
                    default:
                        return new ElementaryFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "店铺到达";
                    case 1:
                        return "门店信息";
                    case 2:
                        return "生动化检查";
                    case 3:
                        return "产品铺货";
                    case 4:
                        return "订单管理";
                    case 5:
                        return "促销执行";
                    case 6:
                        return "竞品管理";
                    case 7:
                        return "拜访小结";
                    default:
                        return "店铺到达";
                }
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
