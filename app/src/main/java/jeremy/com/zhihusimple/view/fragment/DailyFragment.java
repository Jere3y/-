package jeremy.com.zhihusimple.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.actual.DailyFragmentPresenter;
import jeremy.com.zhihusimple.view.BaseFragment;
import jeremy.com.zhihusimple.view.iview.IDailyFragmentView;

public class DailyFragment extends BaseFragment<IDailyFragmentView,DailyFragmentPresenter> implements IDailyFragmentView {

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    RecyclerView content_list;

    @Override
    protected DailyFragmentPresenter createPresenter() {
        return new DailyFragmentPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initView(View rootView) {
        mLayoutManager = new LinearLayoutManager(getContext());
        content_list.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataRefresh(true);
        mPresenter.getDailyTimeLine("0");
        mPresenter.scrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getDailyTimeLine("0");
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return content_list;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }
}
