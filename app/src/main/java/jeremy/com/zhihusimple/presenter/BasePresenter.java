package jeremy.com.zhihusimple.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import jeremy.com.zhihusimple.presenter.api.ApiFactory;
import jeremy.com.zhihusimple.presenter.api.DailyApi;
import jeremy.com.zhihusimple.presenter.api.GankApi;
import jeremy.com.zhihusimple.presenter.api.ZhihuApi;

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;

    public static final ZhihuApi zhihuApi = ApiFactory.getZhihuApiSingleton();
    public static final GankApi gankApi = ApiFactory.getGankApiSingleton();
    public static final DailyApi dailyApi = ApiFactory.getDailyApiSingleton();

    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null&&mViewRef.get()!=null;
    }

    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

}