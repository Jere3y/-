package jeremy.com.zhihusimple.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.actual.ZhihuWebPresenter;
import jeremy.com.zhihusimple.view.BaseActivity;
import jeremy.com.zhihusimple.view.iview.IZhihuWebView;

public class ZhihuWebActivity extends BaseActivity<IZhihuWebView,ZhihuWebPresenter> implements IZhihuWebView {

    private static final String ID = "id";

    private String id;

    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.iv_web_img)
    ImageView iv_web_img;
    @BindView(R.id.tv_img_title)
    TextView tv_img_title;
    @BindView(R.id.tv_img_source)
    TextView tv_img_source;

    @Override
    protected ZhihuWebPresenter createPresenter() {
        return new ZhihuWebPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        mPresenter.getDetailNews(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroyImg();
    }

    public static Intent newIntent(Context context, String id){
        Intent intent = new Intent(context,ZhihuWebActivity.class);
        intent.putExtra(ZhihuWebActivity.ID,id);
        return intent;
    }

    private void parseIntent(){
        id = getIntent().getStringExtra(ID);
    }

    @Override
    public WebView getWebView() {
        return web_view;
    }

    @Override
    public ImageView getWebImg() {
        return iv_web_img;
    }

    @Override
    public TextView getImgTitle() {
        return tv_img_title;
    }

    @Override
    public TextView getImgSource() {
        return tv_img_source;
    }
}
