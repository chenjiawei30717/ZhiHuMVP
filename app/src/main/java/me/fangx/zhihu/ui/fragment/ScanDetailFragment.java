package me.fangx.zhihu.ui.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.ui.activity.BaseToolBarActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.zhihu.R;
import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.bean.RecordBean;
import me.fangx.zhihu.modle.bean.TestBean;
import me.fangx.zhihu.presenter.ScanPresenter;
import me.fangx.zhihu.utils.DummyContent;
import me.fangx.zhihu.view.TestListView;

/**
 * Shows the quote detail page.
 * <p/>
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ScanDetailFragment extends BaseFragment implements TestListView {

    public ScanPresenter scanPresenter;
    public static final String ARG_ITEM_INFO = "item_info";
    private TestBean testListBean;

    @Bind(R.id.scan_appbar)
    AppBarLayout scan_appbar;

    @Bind(R.id.scan_toolbar)
    Toolbar scan_toolbar;

    @Bind(R.id.scan_author)
    TextView scan_author;

    @Bind(R.id.scan_tv_content)
    TextView scan_tv_content;

    @Bind(R.id.scan_ok)
    Button scan_ok;

    @Bind(R.id.scan_ng)
    Button scan_ng;

    @Bind(R.id.scan_submit)
    Button scan_submit;

    @Bind(R.id.scan_edit_submit)
    EditText scan_edit_submit;

    @Bind(R.id.scan_backdrop)
    SimpleDraweeView scan_backdropImg;

    @Bind(R.id.scan_collapsing_toolbar)
    CollapsingToolbarLayout scan_collapsingToolbar;

//layout共用
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_scan_detail;
    }

    @Override
    protected void initViewsAndEvents() {

        if (getArguments().containsKey(ARG_ITEM_INFO)) {
            testListBean = getArguments().getParcelable(ARG_ITEM_INFO);
        }

        setHasOptionsMenu(true);


        if (!((BaseToolBarActivity) getActivity()).providesActivityToolbar()) {
            ((BaseToolBarActivity) getActivity()).setToolbar(scan_toolbar);
        }

        if (testListBean != null) {
            loadBackdrop();
//            collapsingToolbar.setTitle(testListBean.getTitle());
//            author.setText(testListBean.getAuthor().getName());
//            tv_content.setText(testListBean.getContent());
            scan_collapsingToolbar.setTitle(testListBean.getProject());
            scan_author.setText(testListBean.getOperator());
            scan_tv_content.setText(testListBean.getGoods_name());
            if(testListBean.getThreshold_code()!=1){
                scan_ng.setVisibility(View.GONE);
                scan_ok.setVisibility(View.GONE);
                scan_submit.setVisibility(View.VISIBLE);
                scan_edit_submit.setVisibility(View.VISIBLE);
            }
        }

        // AppBar的监听
        scan_appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                scan_toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 19, 121, 214));
            }
        });


        scanPresenter = new ScanPresenter(mContext);
        scanPresenter.attachView(this);
        final RecordBean recordBean = new RecordBean();
        scan_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("OK","Ok");
//                scanPresenter.loadList(1,"6934665087981");
                recordBean.setDepartment(testListBean.getDepartment());
                recordBean.setDepartment_group(testListBean.getDepartment_group());
                recordBean.setPart(testListBean.getGoods_part());
                recordBean.setOkng("OK");
                recordBean.setOperator(testListBean.getOperator());
                Log.e("recordbean",recordBean.toString());
                scanPresenter.uploadrecord(recordBean);
                getActivity().finish();
            }
        });


        scan_ng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("NG","NG");
//                scanPresenter.loadList(1,"6934665087981");
                recordBean.setDepartment(testListBean.getDepartment());
                recordBean.setDepartment_group(testListBean.getDepartment_group());
                recordBean.setPart(testListBean.getGoods_part());
                recordBean.setOkng("NG");
                recordBean.setOperator(testListBean.getOperator());
                Log.e("recordbean",recordBean.toString());
                scanPresenter.uploadrecord(recordBean);
                getActivity().finish();
            }
        });



        scan_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordBean.setDepartment(testListBean.getDepartment());
                recordBean.setDepartment_group(testListBean.getDepartment_group());
                recordBean.setPart(testListBean.getGoods_part());
                recordBean.setOperator(testListBean.getOperator());
                recordBean.setNumber(Integer.valueOf(scan_edit_submit.getText().toString()));
                Log.e("recordbean",recordBean.toString());
                scanPresenter.uploadrecord(recordBean);
                getActivity().finish();
            }
        });


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

    private void loadBackdrop() {
//        if (!TextUtils.isEmpty(testListBean.getTitleImage())) {
//            backdropImg.setImageURI(Uri.parse(testListBean.getTitleImage()));
//        }
        if (!TextUtils.isEmpty(testListBean.getBuilding())) {
            scan_backdropImg.setImageURI(Uri.parse("http://localhost:8080/RICOH/frame/2008814183939909_2.jpg"));
//            backdropImg.setImageURI(Uri.parse("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=102140137,3435781676&fm=15&gp=0.jpg"));
//            backdropImg.setImageURI(R.drawable.avatar_img));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // your logic
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static ScanDetailFragment newInstance(TestBean testListBean) {
        ScanDetailFragment fragment = new ScanDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ScanDetailFragment.ARG_ITEM_INFO, testListBean);
        fragment.setArguments(args);
        return fragment;
    }

    public ScanDetailFragment() {
    }

    @Override
    public void refresh(List<TestBean> data) {

    }

    @Override
    public void loadMore(List<TestBean> data) {

    }
}
