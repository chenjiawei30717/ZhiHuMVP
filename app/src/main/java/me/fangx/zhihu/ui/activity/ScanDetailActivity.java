package me.fangx.zhihu.ui.activity;

import android.os.Bundle;
import android.view.View;

import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.ui.activity.BaseToolBarActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import me.fangx.zhihu.R;
import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.bean.TestBean;
import me.fangx.zhihu.ui.fragment.ScanDetailFragment;

/**
 * Simple wrapper for {@link ScanDetailFragment}
 * This wrapper is only used in single pan mode (= on smartphones)
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ScanDetailActivity extends BaseToolBarActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_scan_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        ScanDetailFragment fragment = ScanDetailFragment.newInstance((TestBean) getIntent().getParcelableExtra(ScanDetailFragment.ARG_ITEM_INFO));
        getSupportFragmentManager().beginTransaction().replace(R.id.scan_detail_container, fragment).commit();
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
