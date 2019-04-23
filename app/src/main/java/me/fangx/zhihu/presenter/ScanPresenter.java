package me.fangx.zhihu.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.fangx.zhihu.modle.bean.RecordBean;
import me.fangx.zhihu.modle.bean.TestBean;
import me.fangx.zhihu.modle.http.HomeListService;
import me.fangx.zhihu.modle.http.utils.RetrofitUtils;
import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.entity.ArticleListEntity;
import me.fangx.zhihu.modle.http.utils.TestListService;
import me.fangx.zhihu.utils.BaseUtil;
import me.fangx.zhihu.view.HomeListView;
import me.fangx.zhihu.view.TestListView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fangxiao on 16/1/5.
 */
public class ScanPresenter extends BasePresenter<TestListView> {

    private static final String tag = "growthhacker";

    public static final int LIMIT = 10;

    private TestListService testListService;
    private Subscription mSubscription;

    public ScanPresenter(Context context) {
        testListService = RetrofitUtils.createApi(context, TestListService.class);
    }

    @Override
    public void attachView(TestListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }


    public void uploadrecord(RecordBean bean){
        checkViewAttached();
        Log.e("recordbean",bean.toString());
        mSubscription = testListService.uploadrecord(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TestBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR",e.toString()+"\n"+e.getStackTrace());
                        getMvpView().showError(e.getMessage(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("ERROR","Onclick()");
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<TestBean> testListBeanList) {
                        Log.e("onNext","onNext");
//                        if (page == 1) {
                            getMvpView().refresh(testListBeanList);
//                        } else {
//                            getMvpView().loadMore(testListBeanList);
//                        }
                    }
                });
    }

    public void loadList(final int page,String qrcode) {
        checkViewAttached();

        String date = BaseUtil.getNYYMMDD(1 - page);

        int offset = (page - 1) * LIMIT;


        mSubscription = testListService.getTestList(qrcode)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
//                .map(new Func1<List<TestBean>, List<TestBean>>() {
//                    @Override
//                    public List<TestBean> call(List<TestBean> testListBeanList) {
//                        Log.e("call","call");
////                        for (TestBean testBean : testListBeanList) {
////                            testBean.setOperater(BaseUtil.delHTMLTag(testBean.getOperater()));
////                            testBean.setProject(BaseUtil.delHTMLTag(testBean.getProject()));
////                        }
//                        Log.e("testListBeanList",String.valueOf(testListBeanList.size()));
//                        return testListBeanList;
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TestBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR",e.toString()+"\n"+e.getStackTrace());
                        getMvpView().showError(e.getMessage(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("ERROR","Onclick()");
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<TestBean> testListBeanList) {
                        Log.e("onNext","onNext");
                        if (page == 1) {
                            getMvpView().refresh(testListBeanList);
                        } else {
                            getMvpView().loadMore(testListBeanList);
                        }
                    }
                });

    }


    //数据缓存
    private void saveDataToDBorSP(ArticleListEntity articleListEntity) {

    }


}
