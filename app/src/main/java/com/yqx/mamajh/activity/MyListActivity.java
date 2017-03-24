package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.MallMyList;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/20.
 */

public class MyListActivity extends Activity{
    private ImageButton ib_back;
    private TextView tv_inteCount;
    private ListView lv_mylist;
    private MyListAdapter mylistAdapter;
    private List<MallMyList.MallMyListRes.MallMyListMyList> mylist;
    private TextView tv_title;
    private String title;
    private int cid=0;
    private int o=0;
    private int p=1;
    private int psize=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        Intent intent=getIntent();
        cid=intent.getIntExtra("id", 0);
//        Toast.makeText(MyListActivity.this,""+cid,Toast.LENGTH_SHORT).show();
        title=intent.getStringExtra("title");
        initView();
        loadData(false);
    }

    private void loadData(final boolean isLoadMore) {
        Call<MallMyList> call= RetrofitService.getInstance().integralMallMyList(AppApplication.TOKEN,cid,o,p,psize);
        call.enqueue(new Callback<MallMyList>() {
            @Override
            public void onResponse(Response<MallMyList> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    tv_inteCount.setText("您当前积分为“"+response.body().getInteCount()+"”分可兑换的商品如下");
                    if (!response.body().getRes().isEmpty()){
                        if (isLoadMore){
                            List<MallMyList.MallMyListRes.MallMyListMyList> mylistAdd = response.body().getRes().get(0).getIntegralMallMyList();
                            if (mylistAdd.isEmpty()){
                                Toast.makeText(MyListActivity.this,"只有这些哦...",Toast.LENGTH_LONG).show();
                            }else{
                                mylist.addAll(mylistAdd);
                                mylistAdapter.notifyDataSetChanged();
                            }
                        }else {
                            mylist = response.body().getRes().get(0).getIntegralMallMyList();
                            mylistAdapter = new MyListAdapter();
                            lv_mylist.setAdapter(mylistAdapter);
                        }
                    }else{
                        Toast.makeText(MyListActivity.this,"没有可兑换产品",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initView() {
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText(title+"");
        ib_back=(ImageButton)findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_inteCount=(TextView)findViewById(R.id.tv_inteCount);
        lv_mylist=(ListView)findViewById(R.id.lv_mylist);
        lv_mylist.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public MallMyList.MallMyListRes.MallMyListMyList getItem(int position) {
            return mylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyListViewHolder holder=null;
            if (convertView==null){
                convertView= LayoutInflater.from(MyListActivity.this).inflate(R.layout.item_mylist, null);
                holder=new MyListViewHolder();
                holder.iv_mylist=(ImageView)convertView.findViewById(R.id.iv_mylist);
                holder.tv_mylist_proName=(TextView)convertView.findViewById(R.id.tv_mylist_proName);
                holder.tv_sellPrice=(TextView)convertView.findViewById(R.id.tv_sellPrice);
                holder.tv_marketPrice=(TextView)convertView.findViewById(R.id.tv_marketPrice);
                convertView.setTag(holder);
            }
            holder= (MyListViewHolder) convertView.getTag();
            final MallMyList.MallMyListRes.MallMyListMyList mylistItem = getItem(position);
            Glide.with(MyListActivity.this).load(mylistItem.getImgSrc()).error(R.mipmap.mmjhicon512).placeholder(R.mipmap.mmjhicon512).into(holder.iv_mylist);
            holder.tv_mylist_proName.setText(mylistItem.getTitle()+"");
            holder.tv_sellPrice.setText(mylistItem.getSellPrice()+"");
            holder.tv_marketPrice.setText("￥"+mylistItem.getMarketPrice()+"");
            holder.tv_marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (mylistItem!=null){
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MyListActivity.this,CreditProInfoActivity.class);
                        intent.putExtra("id",mylistItem.getID()+"");
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }

    }
    static class MyListViewHolder{
        ImageView iv_mylist;
        TextView tv_mylist_proName;
        TextView tv_sellPrice;
        TextView tv_marketPrice;
    }
}
