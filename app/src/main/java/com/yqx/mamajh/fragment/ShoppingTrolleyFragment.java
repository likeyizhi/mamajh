package com.yqx.mamajh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.DoubleUtil;

import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.OrderSettlementActivity;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.Cartlist;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/11/6.
 * 购物车
 */

public class ShoppingTrolleyFragment extends BaseFragment {
    @BindView(R.id.list)
    ExpandableListView list;
    @BindView(R.id.btn_cart_gohome)
    BootstrapButton btnCartGohome;
    @BindView(R.id.lay_cart_null)
    LinearLayout layCartNull;
    @BindView(R.id.tv_total_txt)
    TextView tvTotalTxt;
    @BindView(R.id.tv_total_price)
    TextView           tvTotalPrice;

    private ShoppingTrolleyFragment.ShopCartAdapter mAdapter;

    private List<Cartlist.Shop> mEntities = new ArrayList<>();
    private MaterialDialog mMaterialDialog = null;

    @Override
    protected void onFirstUserVisible() {
        if (AppApplication.TOKEN != null) {
            getData(AppApplication.TOKEN);
        } else {
            return;
        }
        list.setAdapter(mAdapter = new ShopCartAdapter());
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                for (int i1 = 0; i1 < mEntities.size(); i1++) {
                    list.expandGroup(i1);
                }
                return true;
            }
        });
    }
    @OnClick({R.id.btn_settlement, R.id.btn_cart_gohome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settlement:
                boolean isSelect = false;
                String ids = "";
                for (Cartlist.Shop entity : mEntities) {
                    if (entity.getIsCheck()) {
                        isSelect = true;
                        List<Cartlist.Shop.Product> productList = new ArrayList<>();
                        for (Cartlist.Shop.Product product : entity.getProductList()) {
                            if (product.getIsCheck()) {
                                productList.add(product);
                                ids += product.getID() + ",";
                            }
                        }
                        //entity.setProductList(productList);
                        //orderEntities.add(entity);
                    }
                }
                if (isSelect) {
                    if (!ids.isEmpty()) {
                        ids = ids.substring(0, ids.length() - 1);
                        Bundle bundle = new Bundle();
                        bundle.putString("ids", ids);
                        readyGo(OrderSettlementActivity.class, bundle);
                    } else {
                        Toast toast=Toast.makeText(getActivity(),"您还没有选择商品",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
//                        Toast.makeText(getActivity(),"您还没有选择商品",Toast.LENGTH_SHORT).show();
//                        showToast("还没有选择商品哦~");
                    }
                } else {
                    Toast toast=Toast.makeText(getActivity(),"您还没有选择商品",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
//                    showToast("请选择一个店铺的商品进行结算");
                }
                break;
            case R.id.btn_cart_gohome:

                break;
        }
    }

    private void getData(String token) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(getActivity())
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<Cartlist>> mGetDataCallNet = RetrofitService.getInstance().getShopCartList(token);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<Cartlist>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<Cartlist>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        mMaterialDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().getCartlist();
                        if (mEntities.size() > 0) {
                            layCartNull.setVisibility(View.GONE);
                            mAdapter.notifyDataSetChanged();
                            for (int i = 0; i < mEntities.size(); i++) {
                                list.expandGroup(i);
                            }
                            mMaterialDialog.dismiss();
                        } else {
                            layCartNull.setVisibility(View.VISIBLE);
                            mMaterialDialog.dismiss();
                        }
                    } else {
                        layCartNull.setVisibility(View.VISIBLE);
                        mMaterialDialog.dismiss();
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
    }

    @Override
    protected void onUserVisible() {
        if (AppApplication.TOKEN != null) {
            getData(AppApplication.TOKEN);
        } else {
            return;
        }
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shopping_trolley;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private class ShopCartAdapter extends BaseExpandableListAdapter {

        @Override
        public void notifyDataSetChanged() {
            upDateTotlaPrice();
            super.notifyDataSetChanged();
        }


        @Override
        public int getGroupCount() {
            return mEntities.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mEntities.get(i).getProductList().size();
        }

        @Override
        public Object getGroup(int i) {
            return mEntities.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return mEntities.get(i).getProductList().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
            final ShoppingTrolleyFragment.GroupViewHolder holder;
            final Cartlist.Shop   entity = mEntities.get(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_cart_group, null);
                holder = new ShoppingTrolleyFragment.GroupViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ShoppingTrolleyFragment.GroupViewHolder) view.getTag();
            }
            Glide.with(getActivity()).load(entity.getShopLogo()).crossFade().into(holder.ivItemPic);
            holder.tvItemTitle.setText(entity.getShopName());
            holder.cbItemCheck.setChecked(entity.getIsCheck());
            holder.cbItemCheck.setOnClickListener(new View.OnClickListener() {
                public int onlyC=0;

                @Override
                public void onClick(View view) {
                    for (int j1 = 0; j1 < mEntities.size(); j1++) {
                        mEntities.get(j1).setIsCheck(false);
                        for (int j2 = 0; j2 < mEntities.get(j1).getProductList().size(); j2++) {
                            mEntities.get(j1).getProductList().get(j2).setIsCheck(false);
                        }
                    }
//                    for (int m=0;m<mEntities.size();m++){
//                        if(mEntities.get(m).getIsCheck()==true){
//                            onlyC++;
//                            if (onlyC==2){
//                                mEntities.get(m).setIsCheck(false);
//                            }
//                        }
//                    }
                    mEntities.get(i).setIsCheck(holder.cbItemCheck.isChecked());
                    for (int i1 = 0; i1 < mEntities.get(i).getProductList().size(); i1++) {
                        mEntities.get(i).getProductList().get(i1).setIsCheck(holder.cbItemCheck.isChecked());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            return view;
        }

        @Override
        public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
            final ShoppingTrolleyFragment.ChildViewHolder holder;
            final Cartlist.Shop.Product entity = mEntities.get(i).getProductList().get(i1);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_cart_child, null);
                holder = new ShoppingTrolleyFragment.ChildViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ShoppingTrolleyFragment.ChildViewHolder) view.getTag();
            }
            Glide.with(getActivity()).load(entity.getImg()).crossFade().into(holder.ivItemPic);
            holder.tvItemTitle.setText(entity.getName());
            holder.tvItemPrice.setText("￥" + entity.getOrderPrice());
            holder.stepperItemNum.setValue(entity.getCount());
            holder.cbItemCheck.setChecked(entity.getIsCheck());
            holder.cbItemCheck.setOnClickListener(new View.OnClickListener() {
                public int mCheckNum=0;
                public int checkNum=0;
                @Override
                public void onClick(View view) {
                    if (!holder.cbItemCheck.isChecked()){
                        for (int q=0;q<mEntities.get(i).getProductList().size();q++){
                            if (mEntities.get(i).getProductList().get(q).getIsCheck()){
                                checkNum=checkNum+1;
                            }
                        }
//                        Toast.makeText(ShoppingTrolleyFragment.this,checkNum+"d",Toast.LENGTH_SHORT).show();
                        if (checkNum==1){
                            mEntities.get(i).setIsCheck(false);
                        }else{
                            mEntities.get(i).setIsCheck(true);
                        }
                    }else{
                        for (int q=0;q<mEntities.get(i).getProductList().size();q++){
                            if (mEntities.get(i).getProductList().get(q).getIsCheck()){
                                checkNum=checkNum+1;
                            }
                        }
//                        Toast.makeText(ShoppingTrolleyFragment.this,checkNum+"w",Toast.LENGTH_SHORT).show();
                        if (checkNum>=0){
                            mEntities.get(i).setIsCheck(true);
                        }else{
                            mEntities.get(i).setIsCheck(false);
                        }
                    }
                    for (int m=0;m<mEntities.size();m++){
                        if (mEntities.get(m).getIsCheck()){
                            mCheckNum=mCheckNum+1;
                        }
                    }
                    if (mCheckNum>1){
                        for (int m=0;m<mEntities.size();m++){
                            mEntities.get(m).setIsCheck(false);
                        }
                        mEntities.get(i).setIsCheck(true);
                        for (int mm=0;mm<mEntities.size();mm++){
                            if (!mEntities.get(mm).getIsCheck()){
                                for (int mmm=0;mmm<mEntities.get(mm).getProductList().size();mmm++){
                                    mEntities.get(mm).getProductList().get(mmm).setIsCheck(false);
                                }
                            }
                        }
                    }

                    mEntities.get(i).getProductList().get(i1).setIsCheck(holder.cbItemCheck.isChecked());
//                    mEntities.get(i).setIsCheck(holder.cbItemCheck.isChecked());
                    mAdapter.notifyDataSetChanged();
                }
            });
            holder.ivItemDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(getActivity())
                            .title("是否删除")
                            .neutralText(R.string.cancel)
                            .neutralColorRes(R.color.gray_500)
                            .positiveText(R.string.btn_ok)
                            .positiveColorRes(R.color.colorPrimary)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    delete(AppApplication.TOKEN, entity.getID(), i, i1);
                                }
                            })
                            .show();
                }
            });
            holder.stepperItemNum.setOnValueChangeListener(new SnappingStepperValueChangeListener() {
                @Override
                public void onValueChange(View view, int value) {
                    if (value == 0) {
                        holder.stepperItemNum.setValue(1);
                    } else {
                        updateCount(AppApplication.TOKEN, entity.getID(), value, i, i1);
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(ShoppingTrolleyFragment.this,""+entity.getID(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),ProductInfoActivity.class);
                    intent.putExtra("_id", entity.getID());
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }


    }

    static class GroupViewHolder {
        @BindView(R.id.cb_item_check)
        CheckBox cbItemCheck;
        @BindView(R.id.iv_item_pic)
        ImageView ivItemPic;
        @BindView(R.id.tv_item_title)
        TextView  tvItemTitle;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.cb_item_check)
        CheckBox cbItemCheck;
        @BindView(R.id.tv_item_title)
        TextView        tvItemTitle;
        @BindView(R.id.iv_item_del)
        ImageView       ivItemDel;
        @BindView(R.id.iv_item_pic)
        ImageView       ivItemPic;
        @BindView(R.id.tv_item_price)
        TextView        tvItemPrice;
        @BindView(R.id.stepper_item_num)
        SnappingStepper stepperItemNum;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void upDateTotlaPrice() {
        double total         = 0.0;
        int    childCheckNum = 0;
        for (Cartlist.Shop entity : mEntities) {
            if (entity.getIsCheck()) {
                for (Cartlist.Shop.Product product : entity.getProductList()) {
                    if (product.getIsCheck()) {
                        childCheckNum++;
                        double d = DoubleUtil.mul(product.getCount(), product.getOrderPrice());
                        total = DoubleUtil.add(total, d);
                    }
                }
            }
        }
        tvTotalPrice.setText(total + "");
        tvTotalTxt.setText("您选购了" + childCheckNum + "件商品");

    }

    /**
     * 删除
     */
    private void delete(String token, int id, final int i, final int i1) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity> call = RetrofitService.getInstance().deleteShopCartProduct(token, id);
            call.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities.get(i).getProductList().remove(i1);
                        if (mEntities.get(i).getProductList().size() == 0) {
                            mEntities.remove(i);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast(response.body().getMes());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
    }

    /**
     * 修改购物车指定商品数量
     */
    private void updateCount(String token, int id, final int count, final int i, final int i1) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity> call = RetrofitService.getInstance().updateShopCartCount(token, id, count);
            call.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities.get(i).getProductList().get(i1).setCount(count);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
    }
}
