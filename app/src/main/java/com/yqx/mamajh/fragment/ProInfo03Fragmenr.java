package com.yqx.mamajh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.bean.EvaluatesEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/11.
 */

public class ProInfo03Fragmenr extends android.support.v4.app.Fragment {
    private View view;
    private int id;
    private ListView lv_pro_evaluate;
    private int p=1;
    private int size=10;
    private List<EvaluatesEntity.ResEntity.CommentlistEntity> entities;
    private EntitiesAdapter entitiesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_proinfo_03,null);
        id = ((ProductInfoActivity) getActivity()).getId();
        p=1;
        size=10;
        initView();
        loadData(false);
        return view;
    }

    private void loadData(final boolean isLoadMore) {
        Call<EvaluatesEntity> call= RetrofitService.getInstance().getShopProductEvaluateContent(id,p,size);
        call.enqueue(new Callback<EvaluatesEntity>() {
            @Override
            public void onResponse(Response<EvaluatesEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    if (isLoadMore){
                        List<EvaluatesEntity.ResEntity.CommentlistEntity> entitiesAdd = response.body().getRes().getCommentlist();
                        entities.addAll(entitiesAdd);
                        entitiesAdapter.notifyDataSetChanged();
                    }else{
                        entities = response.body().getRes().getCommentlist();
                        setEntitiesAdapter();
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(),"服务器错误:"+t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEntitiesAdapter() {
        entitiesAdapter=new EntitiesAdapter();
        lv_pro_evaluate.setAdapter(entitiesAdapter);
    }

    private void initView() {
        lv_pro_evaluate=(ListView)view.findViewById(R.id.lv_pro_evaluate);

        lv_pro_evaluate.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                            p++;
                            loadData(true);
                        }
                        break;

                    default:
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

    }

    class EntitiesAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return entities.size();
        }

        @Override
        public EvaluatesEntity.ResEntity.CommentlistEntity getItem(int position) {
            return entities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return entities.get(position).getID();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EntitiesViewHolder holder=null;
            if (convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pro_evaluate, null);
                holder=new EntitiesViewHolder();
                holder.tv_evaluate_person=(TextView)convertView.findViewById(R.id.tv_evaluate_person);
                holder.tv_evaluate_timie=(TextView)convertView.findViewById(R.id.tv_evaluate_timie);
                holder.ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar);
                holder.tv_evaluate_contenc=(TextView)convertView.findViewById(R.id.tv_evaluate_contenc);
                holder.iv_evaluate_img01=(ImageView)convertView.findViewById(R.id.iv_evaluate_img01);
                holder.iv_evaluate_img02=(ImageView)convertView.findViewById(R.id.iv_evaluate_img02);
                holder.iv_evaluate_img03=(ImageView)convertView.findViewById(R.id.iv_evaluate_img03);
                holder.ll_evaluate_imgs=(LinearLayout) convertView.findViewById(R.id.ll_evaluate_imgs);
                convertView.setTag(holder);
            }
            holder= (EntitiesViewHolder) convertView.getTag();
            EvaluatesEntity.ResEntity.CommentlistEntity entity=getItem(position);
            holder.tv_evaluate_person.setText(entity.getUserName()+"");
            holder.tv_evaluate_timie.setText(entity.getTime()+"");
//            holder.ratingBar.setRating(entity.get);
            holder.tv_evaluate_contenc.setText(entity.getContent()+"");
            switch (entity.getListImg().size()){
                case 0:
                    holder.ll_evaluate_imgs.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(entity.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.GONE);
                    holder.iv_evaluate_img03.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(entity.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    Glide.with(getContext()).load(entity.getListImg().get(1).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img02);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img03.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.ll_evaluate_imgs.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(entity.getListImg().get(0).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img01);
                    Glide.with(getContext()).load(entity.getListImg().get(1).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img02);
                    Glide.with(getContext()).load(entity.getListImg().get(2).getImg())
                            .error(R.mipmap.mmjhicon512)
                            .placeholder(R.mipmap.mmjhicon512)
                            .into(holder.iv_evaluate_img03);
                    holder.iv_evaluate_img01.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img02.setVisibility(View.VISIBLE);
                    holder.iv_evaluate_img03.setVisibility(View.VISIBLE);
                    break;
            }
//            holder.iv_evaluate_img01
//            holder.iv_evaluate_img02
//            holder.iv_evaluate_img03
            return convertView;
        }

        class EntitiesViewHolder{
            TextView tv_evaluate_person;
            TextView tv_evaluate_timie;
            RatingBar ratingBar;
            TextView tv_evaluate_contenc;
            ImageView iv_evaluate_img01;
            ImageView iv_evaluate_img02;
            ImageView iv_evaluate_img03;
            LinearLayout ll_evaluate_imgs;
        }
    }
}
