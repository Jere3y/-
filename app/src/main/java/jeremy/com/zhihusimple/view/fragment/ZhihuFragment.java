package jeremy.com.zhihusimple.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.actual.ZhihuFragmentPresenter;
import jeremy.com.zhihusimple.view.BaseFragment;
import jeremy.com.zhihusimple.view.iview.IZhihuFragmentView;

public class ZhihuFragment extends BaseFragment<IZhihuFragmentView,ZhihuFragmentPresenter> implements IZhihuFragmentView {

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    RecyclerView content_list;

    @Override
    protected ZhihuFragmentPresenter createPresenter() {
        return new ZhihuFragmentPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_zhihu;
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
        mPresenter.getLatestNews();
        mPresenter.scrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getLatestNews();
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
