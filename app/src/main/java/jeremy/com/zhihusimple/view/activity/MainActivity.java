package jeremy.com.zhihusimple.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.view.BaseActivity;
import jeremy.com.zhihusimple.view.BaseFragment;
import jeremy.com.zhihusimple.view.fragment.DailyFragment;
import jeremy.com.zhihusimple.view.fragment.GankFragment;
import jeremy.com.zhihusimple.view.fragment.ZhihuFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

//    @BindView(R.id.tabLayout)
//    TabLayout tabLayout;
//    @BindView(R.id.content_viewPager)
//    ViewPager content_viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private List<BaseFragment> fragmentList;
    private FragmentManager mFm;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
    }

    //初始化Tab滑动
    public void initTabView(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new DailyFragment());
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new GankFragment());
        mFm = getSupportFragmentManager();
        mFm.beginTransaction().replace(R.id.content,fragmentList.get(0)).commit();
//        content_viewPager.setAdapter(new MyFragmentPagerAdapter(fm,fragmentList));
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.zhihu:
                mFm.beginTransaction().replace(R.id.content,fragmentList.get(1)).commit();
                break;
            case R.id.gank:
                mFm.beginTransaction().replace(R.id.content,fragmentList.get(2)).commit();
                break;
            case R.id.haoqixin:
                mFm.beginTransaction().replace(R.id.content,fragmentList.get(0)).commit();
                break;
            case R.id.today_github:
                Intent it1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jere3y"));
                it1.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                startActivity(it1);
                break;
            case R.id.about:
                AboutMeActivity.start(this);
                break;
            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setTitle("真的要退出吗？")
                        .setNegativeButton("不退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("退出啦", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                finish();
                            }
                        })
                        .setMessage("我要退出？")
                        .create()
                        .show();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
