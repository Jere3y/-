package jeremy.com.zhihusimple.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.view.BaseActivity;


public class AboutMeActivity extends BaseActivity {


    public CollapsingToolbarLayout collapsingToolbarLayout;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutMeActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about_me;
    }

    /**
     * 初始化ToolBar
     */
    private void initToolbar() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("很高兴你能看到这里");
    }

}
