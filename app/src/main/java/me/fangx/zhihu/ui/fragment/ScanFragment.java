package me.fangx.zhihu.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.widget.loading.ProgressView;
import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;
import me.fangx.zhihu.R;
import me.fangx.zhihu.adapter.HomeListAdapter;
import me.fangx.zhihu.adapter.ScanListAdapter;
import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.bean.TestBean;
import me.fangx.zhihu.modle.entity.ArticleListEntity;
import me.fangx.zhihu.modle.entity.ScanListEntity;
import me.fangx.zhihu.presenter.HomePresenter;
import me.fangx.zhihu.presenter.ScanPresenter;
import me.fangx.zhihu.utils.ACache;
import me.fangx.zhihu.utils.BaseUtil;
import me.fangx.zhihu.utils.GsonUtil;
import me.fangx.zhihu.view.HomeListView;
import me.fangx.zhihu.view.TestListView;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends BaseFragment implements TestListView {
    public ScanPresenter scanPresenter;


    @Bind(R.id.scan_swiperefresh)
    SwipeRefreshLayout scan_swiperefresh;
    @Bind(R.id.scan_hao_recycleview)
    HaoRecyclerView scan_hao_recycleview;

    private ScanListAdapter scanListAdapter;
    private ArrayList<TestBean> listData = new ArrayList<>();
    private int page = 1;
    String qrcode=null;
    @Override
    protected void initViewsAndEvents() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            qrcode = bundle.getString("qrcode");
        }
        ACache mCache = ACache.get(this.getActivity().getApplicationContext());
        String value = mCache.getAsString("scan_list");
        scanListAdapter = new ScanListAdapter(mContext, listData);
        scan_hao_recycleview.setAdapter(scanListAdapter);
        scan_swiperefresh.setColorSchemeResources(R.color.textBlueDark, R.color.textBlueDark, R.color.textBlueDark,
                R.color.textBlueDark);

        scan_swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                scanPresenter.loadList(page,qrcode);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scan_hao_recycleview.setLayoutManager(layoutManager);
        //设置自定义加载中和到底了效果
        ProgressView progressView = new ProgressView(mContext);
        progressView.setIndicatorId(ProgressView.BallPulse);
        progressView.setIndicatorColor(0xff69b3e0);
        scan_hao_recycleview.setFootLoadingView(progressView);
        TextView textView = new TextView(mContext);
        textView.setText("已经到底啦~");
        scan_hao_recycleview.setFootEndView(textView);
        scan_hao_recycleview.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                scanPresenter.loadList(page,qrcode);
            }
        });

        scanPresenter = new ScanPresenter(mContext);
        scanPresenter.attachView(this);
        if (!TextUtils.isEmpty(value)) {
            ScanListEntity scanListEntity = GsonUtil.fromJson(value, ScanListEntity.class);
            if(scanListEntity != null){
                listData.addAll(scanListEntity.data);
            }
        }else{
//            showLoading("加载中...");
        }
        //初次加载
        page = 1;

        Log.e("bundle",qrcode);
        scanPresenter.loadList(1,qrcode);
    }



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.scan_list_layout;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void refresh(List<TestBean> data) {
//        hideLoading();
        //注意此处
        scan_hao_recycleview.refreshComplete();
        scan_hao_recycleview.loadMoreComplete();
        scan_swiperefresh.setRefreshing(false);
        listData.clear();
        listData.addAll(data);
        scanListAdapter.notifyDataSetChanged();

        ACache aCache = ACache.get(this.getActivity().getApplicationContext());
        ScanListEntity scanListEntity = new ScanListEntity();
        scanListEntity.data = data;
        aCache.put("home_list", GsonUtil.toJson(scanListEntity));

    }

    @Override
    public void loadMore(List<TestBean> data) {

        if (BaseUtil.isEmpty(data)) {
            scan_hao_recycleview.loadMoreEnd();
        } else {
            listData.addAll(data);
            scanListAdapter.notifyDataSetChanged();
            scan_hao_recycleview.loadMoreComplete();
        }

    }
}
