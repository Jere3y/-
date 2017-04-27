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
import jeremy.com.zhihusimple.model.bean.daily.DailyTimeLine;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.presenter.adapter.DailyFragmentRecyclerAdapter;
import jeremy.com.zhihusimple.view.iview.IDailyFragmentView;


public class DailyFragmentPresenter extends BasePresenter<IDailyFragmentView> {

    private Context context;
    private IDailyFragmentView dailyFgView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private DailyTimeLine timeLine;
    private DailyFragmentRecyclerAdapter adapter;
    private int lastVisibleItem;
    private String has_more;
    private String next_pager;
    private boolean isLoadMore = false; // 是否加载过更多

    public DailyFragmentPresenter(Context context) {
        this.context = context;
    }

    public void getDailyTimeLine(String num){
        dailyFgView = getView();
        if(dailyFgView !=null){
            mRecyclerView = dailyFgView.getRecyclerView();
            layoutManager = dailyFgView.getLayoutManager();

            dailyApi.getDailyTimeLine(num)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DailyTimeLine>() {
                        @Override
                        public void accept(@NonNull DailyTimeLine dailyTimeLine) throws Exception {
                            if (dailyTimeLine.getMeta().getMsg().equals("success")) {
                                disPlayDailyTimeLine(context, dailyTimeLine, mRecyclerView, dailyFgView);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            dailyFgView.setDataRefresh(false);
                            Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void disPlayDailyTimeLine(Context context, DailyTimeLine dailyTimeLine, RecyclerView recyclerView, IDailyFragmentView dailyFgView){
        if(dailyTimeLine.getResponse().getLast_key()!=null){
            next_pager = dailyTimeLine.getResponse().getLast_key();
        }
        has_more = dailyTimeLine.getResponse().getHas_more();
        if (isLoadMore) {
            if (dailyTimeLine.getResponse().getFeeds() == null) {
                adapter.updateLoadStatus(DailyFragmentRecyclerAdapter.LOAD_NONE);
                dailyFgView.setDataRefresh(false);
                return;
            }
            else {
                timeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = dailyTimeLine;
            adapter = new DailyFragmentRecyclerAdapter(context, timeLine.getResponse());
            recyclerView.setAdapter(adapter);
        }
        dailyFgView.setDataRefresh(false);
    }

    /**
     * recyclerView Scroll listener , maybe in here is wrong ?
     */
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                            .findLastVisibleItemPosition();
                    if (layoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(DailyFragmentRecyclerAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()) {
                        adapter.updateLoadStatus(DailyFragmentRecyclerAdapter.LOAD_PULL_TO);
                        if(has_more.equals("true")) {
                            isLoadMore = true;
                        }
                        adapter.updateLoadStatus(DailyFragmentRecyclerAdapter.LOAD_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getDailyTimeLine(next_pager);
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
