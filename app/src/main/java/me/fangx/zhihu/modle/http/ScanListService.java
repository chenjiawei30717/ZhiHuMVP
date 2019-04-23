package me.fangx.zhihu.modle.http;

import java.util.List;

import me.fangx.zhihu.modle.bean.ArticleListBean;
import me.fangx.zhihu.modle.bean.TestBean;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface ScanListService {
    @POST("scanListService")
    Observable<List<TestBean>> getHomeArticleList(@Body String id);
}
