package jeremy.com.zhihusimple.view;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import butterknife.ButterKnife;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.BasePresenter;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity{

    protected T mPresenter;
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsRequestDataRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        setContentView(provideContentViewId());//布局
        ButterKnife.bind(this);

        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null && mAppBar != null) {
            setSupportActionBar(mToolbar);
            if (canBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null)
                    actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                mAppBar.setElevation(9.6f);
            }
        }

        if (isSetRefresh()) {
            setupSwipeRefresh();
        }
    }

    private void setupSwipeRefresh() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2, R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }


    public void setRefresh(boolean requestDataRefresh) {
        if (mRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);

        } else {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 此时android.R.id.home即为返回箭头
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean canBack() {
        return false;
    }

    public Boolean isSetRefresh() {
        return false;
    }

    protected abstract T createPresenter();

    abstract protected int provideContentViewId();//用于引入布局文件

}
