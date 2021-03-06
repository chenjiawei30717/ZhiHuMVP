//package me.fangx.zhihu.presenter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import me.fangx.zhihu.modle.bean.TestBean;
//import me.fangx.zhihu.modle.http.HomeListService;
//import me.fangx.zhihu.modle.http.utils.RetrofitUtils;
//import me.fangx.zhihu.modle.bean.ArticleListBean;
//import me.fangx.zhihu.modle.entity.ArticleListEntity;
//import me.fangx.zhihu.modle.http.utils.TestListService;
//import me.fangx.zhihu.utils.BaseUtil;
//import me.fangx.zhihu.view.HomeListView;
//import me.fangx.zhihu.view.TestListView;
//import rx.Observable;
//import rx.Observer;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//
///**
// * Created by fangxiao on 16/1/5.
// */
//public class TestPresenter extends BasePresenter<TestListView> {
//
//    private static final String tag = "growthhacker";
//
//    public static final int LIMIT = 10;
//
//    private TestListService testListService;
//    private Subscription mSubscription;
//
//    public TestPresenter(Context context) {
//        testListService = RetrofitUtils.createApi(context, TestListService.class);
//    }
//
//    @Override
//    public void attachView(TestListView mvpView) {
//        super.attachView(mvpView);
//    }
//
//    @Override
//    public void detachView() {
//        super.detachView();
//        if (mSubscription != null) mSubscription.unsubscribe();
//    }
//
//
//    public void loadList(final int page) {
//        checkViewAttached();
//
////        String date = BaseUtil.getNYYMMDD(1 - page);
////
////        int offset = (page - 1) * LIMIT;
//
//
//        mSubscription = testListService.getTestList("6785786")
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
////                .map(new Func1<List<TestBean>, List<TestBean>>() {
////                    @Override
////                    public List<TestBean> call(List<TestBean> testBeanslist) {
////                        for (TestBean testBean : testBeanslist) {
////                            testBean.setCode(BaseUtil.delHTMLTag(testBean.getCode()));
////                            testBean.setOperater(BaseUtil.delHTMLTag(testBean.getOperater()));
////                        }
////                        return testBeanslist;
////                    }
////                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<TestBean>>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("onCompleted","onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("ERROR",e.getMessage().toString()+"\n"+e.getStackTrace());
//                        getMvpView().showError(e.getMessage(), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNext(List<TestBean> testBeanslist) {
//                        Log.e("onNext","onNext");
////                        if (page == 1) {
//                        if(testBeanslist.size()<5){
//                            getMvpView().refresh(testBeanslist);
//                        } else {
//                            getMvpView().loadMore(testBeanslist);
//                        }
//                    }
//                });
//
//    }
//
//
//    //数据缓存
//    private void saveDataToDBorSP(ArticleListEntity articleListEntity) {
//
//    }
//
//
//}
