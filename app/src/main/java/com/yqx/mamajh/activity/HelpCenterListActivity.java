package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.HelpCenterIndex;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 常用帮助 列表
 */
public class HelpCenterListActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;

    private List<HelpCenterIndex.HelpCenterList> mEntities = new ArrayList<>();

    private MaterialDialog mMaterialDialog = null;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_help_center_list;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("常见问题");
        int cid = getIntent().getExtras().getInt("cid");
        getData(cid);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", mEntities.get(i).getId());
                readyGo(HelpCenterContentActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void getData(int cid) {
        mMaterialDialog = new MaterialDialog.Builder(HelpCenterListActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<List<HelpCenterIndex.HelpCenterList>>> call = RetrofitService.getInstance().helpCenterList(cid);
        call.enqueue(new Callback<NetBaseEntity<List<HelpCenterIndex.HelpCenterList>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<List<HelpCenterIndex.HelpCenterList>>> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes();
                        lv.setAdapter(new HelpListAdapter());
                    }
                }
                mMaterialDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                mMaterialDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    class HelpListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return mEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final HelpCenterIndex.HelpCenterList entity = mEntities.get(i);
            final ViewHolder                     holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_help_center_list, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tv.setText(entity.getTitle());
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
