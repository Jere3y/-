package jeremy.com.zhihusimple.view.iview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public interface IGankFragmentView {

    void setDataRefresh(Boolean refresh);
    GridLayoutManager getLayoutManager();
    RecyclerView getRecyclerView();
}
