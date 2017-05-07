package jeremy.com.zhihusimple.presenter.actual;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.model.bean.zhihu.NewsTimeLine;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.presenter.adapter.ZhihuFragmentRecyclerAdapter;
import jeremy.com.zhihusimple.view.iview.IZhihuFragmentView;

public class ZhihuFragmentPresenter extends BasePresenter<IZhihuFragmentView> {

    private Context context;
    private IZhihuFragmentView zhihuFgView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private NewsTimeLine timeLine;
    private ZhihuFragmentRecyclerAdapter adapter;
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多

    public ZhihuFragmentPresenter(Context context) {
        this.context = context;
    }

    public void getLatestNews() {
        zhihuFgView = getView();
        if (zhihuFgView != null) {
            mRecyclerView = zhihuFgView.getRecyclerView();
            layoutManager = zhihuFgView.getLayoutManager();

            zhihuApi.getLatestNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<NewsTimeLine>() {
                        @Override
                        public void accept(@NonNull NewsTimeLine newsTimeLine) throws Exception {
                            disPlayZhihuList(newsTimeLine, context, zhihuFgView, mRecyclerView);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadError(throwable);
                        }
                    });
        }
    }

    private void getBeforeNews(String time) {
        zhihuFgView = getView();
        if (zhihuFgView != null) {
            mRecyclerView = zhihuFgView.getRecyclerView();
            layoutManager = zhihuFgView.getLayoutManager();

            zhihuApi.getBeforetNews(time)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((new Consumer<NewsTimeLine>() {
                        @Override
                        public void accept(@NonNull NewsTimeLine newsTimeLine) throws Exception {
                            disPlayZhihuList(newsTimeLine, context, zhihuFgView, mRecyclerView);
                        }
                    }), new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadError(throwable);
                        }
                    });
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    String time;

    private void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context, IZhihuFragmentView iZhihuFragmentView, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (time == null) {
                adapter.updateLoadStatus(ZhihuFragmentRecyclerAdapter.LOAD_NONE);
                iZhihuFragmentView.setDataRefresh(false);
                return;
            } else {
                timeLine.getStories().addAll(newsTimeLine.getStories());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = newsTimeLine;
            adapter = new ZhihuFragmentRecyclerAdapter(context, timeLine);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        iZhihuFragmentView.setDataRefresh(false);
        time = newsTimeLine.getDate();
    }

    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                            .findLastVisibleItemPosition();
                    if (layoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(ZhihuFragmentRecyclerAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()) {
                        adapter.updateLoadStatus(ZhihuFragmentRecyclerAdapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        adapter.updateLoadStatus(ZhihuFragmentRecyclerAdapter.LOAD_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getBeforeNews(time);
                            }
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

}
