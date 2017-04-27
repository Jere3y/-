package jeremy.com.zhihusimple.presenter.api;

import io.reactivex.Observable;
import jeremy.com.zhihusimple.model.bean.zhihu.News;
import jeremy.com.zhihusimple.model.bean.zhihu.NewsTimeLine;
import jeremy.com.zhihusimple.model.bean.zhihu.SplashImage;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ZhihuApi {

    @GET("start-image/1080*1920")
    Observable<SplashImage> getSplashImage();

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();

    @GET("news/before/{time}")
    Observable<NewsTimeLine> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<News> getDetailNews(@Path("id") String id);
}
