package jeremy.com.zhihusimple.view.iview;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


public interface IZhihuWebView {

    WebView getWebView();
    ImageView getWebImg();
    TextView getImgTitle();
    TextView getImgSource();
}
