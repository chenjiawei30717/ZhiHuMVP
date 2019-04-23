package me.fangx.zhihu.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import butterknife.Bind;
import me.fangx.common.ui.fragment.BaseLazyFragment;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.log.LogUtil;
import me.fangx.zhihu.R;
import me.fangx.zhihu.presenter.ScanPresenter;
//import me.fangx.zhihu.presenter.TestPresenter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by fangxiao on 15/12/28.
 */
public class TestFragment extends BaseFragment {
    private ImageButton imageButton;
//    public TestPresenter testPresenter;
    @Override
    protected void initViewsAndEvents() {
//        testPresenter = new TestPresenter(mContext);
        imageButton = (ImageButton) getActivity().findViewById(R.id.btn_record_voice);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("点击","店家");
                AndPermission.with(getActivity())
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
                                ZxingConfig config = new ZxingConfig();
                                // config.setPlayBeep(false);//是否播放扫描声音 默认为true
                                //  config.setShake(false);//是否震动  默认为true
                                // config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                                config.setReactColor(R.color.theme_primary_accent);//设置扫描框四个角的颜色 默认为白色
//                                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                                config.setScanLineColor(R.color.theme_primary_accent);//设置扫描线的颜色 默认白色
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, 101);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
//                        Uri packageURI = Uri.parse("package:" + getPackageName());
                                Log.e("getURI之前","1");
                                Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                                Log.e("getURI之后","1");
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_smbtrain;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.err.println(requestCode + "      " + resultCode);
        // 扫描二维码/条码回传
        Log.e("ScanFra",String.valueOf(requestCode));
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.e("二維碼",content);
//                Log.e("二維碼1",content);
                if(null==content&&content.equals(null)){
                    Log.e("哔了狗","哔了狗");
                }else {
                    Fragment fragment =new ScanFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcode", content);
                    fragment.setArguments(bundle);
//                    testPresenter.loadList(1);
                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, fragment).commit();
                        getActivity().setTitle("点检");
//                        closeDrawer();
                    } else {
                        LogUtil.e("HomeActivity", "Error in creating fragment");
                    }
                }

            }
        }
    }

}
