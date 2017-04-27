package jeremy.com.zhihusimple.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import java.util.List;

import jeremy.com.zhihusimple.view.BaseFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<BaseFragment> fragmentList;

    private final String[] titles = {"干货集中","知乎日报","好奇日报"};

    public MyFragmentPagerAdapter(FragmentManager supportFragmentManager, List<BaseFragment> fragmentList) {
        super(supportFragmentManager);
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }


    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
