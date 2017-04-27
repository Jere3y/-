package jeremy.com.zhihusimple.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.view.BaseActivity;
import jeremy.com.zhihusimple.presenter.BasePresenter;

public class AboutMeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_github)
    TextView tv_github;

    public CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();

        tv_github.setOnClickListener(this);

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
        collapsingToolbarLayout.setTitle("欢迎你能来到这里");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_github:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tv_github.getText().toString()));
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                startActivity(intent);
                break;
        }
    }
}
