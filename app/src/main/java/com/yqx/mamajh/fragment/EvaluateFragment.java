package com.yqx.mamajh.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.EvaluateEntity;
import com.yqx.mamajh.bean.EvaluatesEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/9.
 */

public class EvaluateFragment extends BaseFragment {

    @BindView(R.id.ll_root)
    RelativeLayout root;
    @BindView(R.id.rl_root)
    FrameLayout mRootView;

    @BindView(R.id.tv_num)
    TextView num;
    @BindView(R.id.tv_about)
    TextView about;
    @BindView(R.id.rb_manner)
    RatingBar manner;
    @BindView(R.id.rb_quality)
    RatingBar quality;
    @BindView(R.id.rb_speed)
    RatingBar speed;
    @BindView(R.id.btn_use)
    Button use;
    @BindView(R.id.btn_unuse)
    Button unused;
    @BindView(R.id.fragment_evaluate)
    ListView loadMoreListView;

    private String id;
    private String status = "1";
    private int page=1;
    private String pageSize = "5";
    private List<EvaluatesEntity.ResEntity.CommentlistEntity> commentlist;
    private EvaluatesAdapter evaluatesAdapter;

    public void scrollHeader(int scrollY) {
        root.setTranslationY(-scrollY);
    }

    @Override
    protected void onFirstUserVisible() {
//        mScrollView = ButterKnife.findById(root, R.id.scrollview);
//        mRootView.setMinimumHeight(mScreenHeight*2);
//        setScrollViewOnScrollListener();
        Bundle bundle = getArguments();
        if(bundle != null ){
            id = bundle.getString(ShopActivity.IDBUNDLE);
        }
//        Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
        loadData();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return root;
    }

    @Override
    protected void initViewsAndEvents() {

        Bundle bundle = getArguments();
        if(bundle != null ){
            id = bundle.getString(ShopActivity.IDBUNDLE);
        }
        use.setBackgroundColor(Color.rgb(226,65,88));//#E24158
        unused.setBackgroundColor(Color.rgb(144,144,144));//#909090
        loadData();
//        loadMoreListView.setCanLoadMore(true);
//        loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                Toast.makeText(getActivity(),"++",Toast.LENGTH_SHORT).show();
//                page++;
//                loadRvaluate(true);
//                loadMoreListView.onLoadMoreComplete();
//            }
//        });
        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isBottom;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        //Log.i("info", "SCROLL_STATE_FLING");
                        break;
                    case SCROLL_STATE_IDLE:
                        //Log.i("info", "SCROLL_STATE_IDLE");
                        //如果已经到底了  加载下一页
                        if (isBottom) {
                            page++;
                            loadRvaluate(2);
                        }
                            break;
                        }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount){
                    //Log.i("info", "到底了....");
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        });
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                status="1";
                loadRvaluate(3);
                use.setBackgroundColor(Color.rgb(226,65,88));//#E24158
                unused.setBackgroundColor(Color.rgb(144,144,144));//#909090
            }
        });

        unused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                status="2";
                loadRvaluate(3);
                use.setBackgroundColor(Color.rgb(144,144,144));//#909090
                unused.setBackgroundColor(Color.rgb(226,65,88));//#E24158
            }
        });

    }

    private void loadData() {
        showLoading("", true);
        Call<EvaluateEntity> call = RetrofitService.getInstance().loadRvaluate(id);
        call.enqueue(new Callback<EvaluateEntity>() {
            @Override
            public void onResponse(Response<EvaluateEntity> response, Retrofit retrofit) {
                EvaluateEntity evaluateEntity = response.body();
                if(evaluateEntity == null){
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody!=null) {
                        try {
//                            loadedListener.onException(responseBody.string());
                            showError(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showError("(responseBody = null)极有可能是json解析失败");
                    }
                }else{
                    if(evaluateEntity.getStatus() != 0){
                        showError(evaluateEntity.getMes());
                    }else{
                        EvaluateEntity.ResEntity resEntity = evaluateEntity.getRes();
//                        Toast.makeText(getActivity(),""+resEntity.getHighscore(),Toast.LENGTH_SHORT).show();
                        num.setText(resEntity.getPjscore());
                        about.setText("高于周边商家" + resEntity.getHighscore() + "%");
                        manner.setRating((float) resEntity.getServicescore());
                        quality.setRating((float) resEntity.getProductscore());
                        speed.setRating((float) resEntity.getDeliverscore());
                        use.setText("全部("+resEntity.getPjtotal()+")");
                        unused.setText("有图("+resEntity.getPjpic()+")");
                        loadRvaluate(1);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();
                showError(t.getMessage());

            }
        });
    }

    private void loadRvaluate(final int isLoadMore) {
        Call<EvaluatesEntity> call = RetrofitService.getInstance().loadRvaluates(id, status, page, pageSize);
        call.enqueue(new Callback<EvaluatesEntity>() {
            @Override
            public void onResponse(Response<EvaluatesEntity> response, Retrofit retrofit) {
                hideLoading();
                EvaluatesEntity evaluatesEntity = response.body();
//                Toast.makeText(getActivity(),""+evaluatesEntity.getRes().getCommentlist().size(),Toast.LENGTH_SHORT).show();
                if(evaluatesEntity == null){
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody!=null) {
                        try {
//                            loadedListener.onException(responseBody.string());
                            showError(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showError("(responseBody = null)极有可能是json解析失败");
                    }
                }else{
                    if(evaluatesEntity.getStatus() != 0){
                        showError(evaluatesEntity.getMes());
                    }else{
                        if (isLoadMore==2) {
                            //加载更多
//                            Toast.makeText(getActivity(),"加载更多...",Toast.LENGTH_SHORT).show();
                            List<EvaluatesEntity.ResEntity.CommentlistEntity> commentlistAdd = evaluatesEntity.getRes().getCommentlist();
                            commentlist.addAll(commentlistAdd);
                            evaluatesAdapter.notifyDataSetChanged();
                        }else if (isLoadMore==1){
                            //首次加载
                            commentlist=evaluatesEntity.getRes().getCommentlist();
//                        Toast.makeText(getActivity(),""+evaluatesEntity.getRes().getCommentlist().size(),Toast.LENGTH_SHORT).show();
                            evaluatesAdapter=new EvaluatesAdapter();
                            loadMoreListView.setAdapter(evaluatesAdapter);
                        }else{
                            //切换
                            commentlist=evaluatesEntity.getRes().getCommentlist();
//                        Toast.makeText(getActivity(),""+evaluatesEntity.getRes().getCommentlist().size(),Toast.LENGTH_SHORT).show();
                            evaluatesAdapter=new EvaluatesAdapter();
                            loadMoreListView.setAdapter(evaluatesAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_evaluate;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    class EvaluatesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return commentlist.size();
        }

        @Override
        public Object getItem(int position) {
            return commentlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EvaluatesEntity.ResEntity.CommentlistEntity comment = commentlist.get(position);
            CommentViewHolder holder;
            if (convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_evaluate, null);
                holder=new CommentViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder= (CommentViewHolder) convertView.getTag();
            }

            Glide.with(getContext())
                    .load(comment.getUserLogo())
                    .error(R.mipmap.mmjhicon512)
                    .placeholder(R.mipmap.mmjhicon512)
                    .into(holder.iv_evaluate_shop_icon);
            holder.tv_evaluate_person.setText(comment.getUserName()+"");
            holder.tv_evaluate_timie.setText(comment.getTime()+"");
            holder.tv_evaluate_content.setText(comment.getContent()+"");
            holder.tv_evaluate_buy_day.setText("购买日期："+comment.getOrderTime()+"");
            holder.tv_evaluate_product.setText("");
            for (int i=0;i<comment.getListProduct().size();i++){
                holder.tv_evaluate_product.append(comment.getListProduct().get(i).getTitle()+";");
            }
            switch (comment.getListImg().size()){
                case 0:
                    holder.ll_evaluate_imgs.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(comment.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.GONE);
                    holder.iv_evaluate_img03.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(comment.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    Glide.with(getContext()).load(comment.getListImg().get(1).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img02);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img03.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(comment.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    Glide.with(getContext()).load(comment.getListImg().get(1).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img02);
                    Glide.with(getContext()).load(comment.getListImg().get(2).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img03);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img03.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            return convertView;
        }
    }

    static class CommentViewHolder{
        @BindView(R.id.iv_evaluate_shop_icon)
        ImageView iv_evaluate_shop_icon;
        @BindView(R.id.iv_evaluate_img01)
        ImageView iv_evaluate_img01;
        @BindView(R.id.iv_evaluate_img02)
        ImageView iv_evaluate_img02;
        @BindView(R.id.iv_evaluate_img03)
        ImageView iv_evaluate_img03;
        @BindView(R.id.tv_evaluate_person)
        TextView tv_evaluate_person;
        @BindView(R.id.tv_evaluate_timie)
        TextView tv_evaluate_timie;
        @BindView(R.id.tv_evaluate_content)
        TextView tv_evaluate_content;
        @BindView(R.id.tv_evaluate_product)
        TextView tv_evaluate_product;
        @BindView(R.id.tv_evaluate_buy_day)
        TextView tv_evaluate_buy_day;
        @BindView(R.id.ll_evaluate_imgs)
        LinearLayout ll_evaluate_imgs;

        CommentViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
