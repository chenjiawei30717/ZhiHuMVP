package me.fangx.zhihu.modle.http.utils;

import java.util.List;

import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.bean.RecordBean;
import me.fangx.zhihu.modle.bean.TestBean;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface TestListService {
    @POST("scanListService")
    Observable<List<TestBean>> getTestList(@Body String qrcode);

    @POST("uploadrecord")
    Observable<List<TestBean>> uploadrecord(@Body RecordBean bean);
}
