package jeremy.com.zhihusimple.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jeremy.com.zhihusimple.BuildConfig;
import jeremy.com.zhihusimple.R;
import jeremy.com.zhihusimple.view.BaseActivity;
import jeremy.com.zhihusimple.presenter.BasePresenter;
import jeremy.com.zhihusimple.view.widget.SplashView;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();

    @BindView(R.id.splash_view)
    SplashView splash_view;
    @BindView(R.id.tv_splash_info)
    TextView tv_splash_info;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoadingData();
    }

    /**
     * start splash animation
     */
    private void startLoadingData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1000);
    }

    private void onLoadingDataEnded() {
        // start the splash animation
        splash_view.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
                // log the animation start event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction) {
                // log animation update events
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd() {
                // log the animation end event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                splash_view = null;
                goToMain();
            }
        });
    }

    public void goToMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate");
        }
    }
}
