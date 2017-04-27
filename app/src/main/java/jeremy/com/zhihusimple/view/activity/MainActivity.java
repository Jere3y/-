package jeremy.com.zhihusimple.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.presenter.adapter.MyFragmentPagerAdapter;
import jeremy.com.zhihusimple.view.BaseActivity;
import jeremy.com.zhihusimple.view.BaseFragment;
import jeremy.com.zhihusimple.view.fragment.DailyFragment;
import jeremy.com.zhihusimple.view.fragment.GankFragment;
import jeremy.com.zhihusimple.view.fragment.ZhihuFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager content_viewPager;

    private List<BaseFragment> fragmentList;

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

        content_viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.today_github:
                String github_trending = "https://github.com/trending";
                startActivity(GankWebActivity.newIntent(this,github_trending));
                break;
        }
        return true;
    }
}
