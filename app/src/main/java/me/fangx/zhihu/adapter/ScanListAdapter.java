package me.fangx.zhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.fangx.zhihu.R;
import me.fangx.zhihu.modle.bean.TestBean;
import me.fangx.zhihu.ui.activity.ScanDetailActivity;
import me.fangx.zhihu.ui.fragment.ScanDetailFragment;
import me.fangx.zhihu.utils.BaseUtil;
import me.fangx.zhihu.utils.DummyContent;

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.ViewHolder> {
    public ArrayList<TestBean> datas = null;

    Context context;

    public ScanListAdapter(Context context, ArrayList<TestBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scan_list_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.tag_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScanDetailActivity.class);
                intent.putExtra(ScanDetailFragment.ARG_ITEM_INFO, datas.get(position));
                context.startActivity(intent);
            }
        });

        viewHolder.title_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScanDetailActivity.class);
                intent.putExtra(ScanDetailFragment.ARG_ITEM_INFO, datas.get(position));
                context.startActivity(intent);
            }
        });
        viewHolder.content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScanDetailActivity.class);
                intent.putExtra(ScanDetailFragment.ARG_ITEM_INFO, datas.get(position));
                context.startActivity(intent);
            }
        });

        TestBean testBean = datas.get(position);
        viewHolder.title_text.setText(testBean.getProject());
        viewHolder.tv_summary.setText(testBean.getOperator());
        viewHolder.tv_likecount.setText(String.valueOf(testBean.getNumber()));
        viewHolder.tag_text.setText(testBean.getBuilding());
//        viewHolder.tv_likecount.setText(testBean.getLikesCount() > 1000 ? (float) (articleListBean.getLikesCount() / 1000) * 10 / 10 + "k" : articleListBean.getLikesCount() + "");

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_summary;
        public TextView tv_likecount;
        public TextView tag_text;
        public TextView title_text;
        public LinearLayout content_layout;

        public ViewHolder(View view) {
            super(view);
            tv_summary = (TextView) view.findViewById(R.id.scan_tv_summary);
            tv_likecount = (TextView) view.findViewById(R.id.scan_tv_likecount);
            tag_text = (TextView) view.findViewById(R.id.scan_tag_text);
            title_text = (TextView) view.findViewById(R.id.scan_title_text);
            content_layout = (LinearLayout) view.findViewById(R.id.scan_content_layout);

        }
    }
}
