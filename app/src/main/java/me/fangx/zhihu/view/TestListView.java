package me.fangx.zhihu.view;

import java.util.List;

import me.fangx.zhihu.modle.bean.TestBean;

public interface TestListView extends MvpView {
    void refresh(List<TestBean> data);

    void loadMore(List<TestBean> data);
}
