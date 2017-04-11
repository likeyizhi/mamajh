package com.yqx.mamajh.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.AddShowProduct;
import com.yqx.mamajh.bean.MemberOrder;
import com.yqx.mamajh.bean.MemberOrderInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.SettingImage;
import com.yqx.mamajh.utils.UploadUtil;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 订单评价
 */
public class OrderCommentActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView  tvName;
    @BindView(R.id.rating_fwtd)
    RatingBar ratingFwtd;
    @BindView(R.id.rating_cpzl)
    RatingBar ratingCpzl;
    @BindView(R.id.rating_shsd)
    RatingBar ratingShsd;
    @BindView(R.id.gv_img)
    GridView  gvImg;
    @BindView(R.id.lv_productlist)
    ListView  lvProductlist;
    @BindView(R.id.et_comment)
    EditText  etComment;
    @BindView(R.id.iv_img1)
    ImageView iv_img1;
    @BindView(R.id.iv_img2)
    ImageView iv_img2;
    @BindView(R.id.iv_img3)
    ImageView iv_img3;

    private MemberOrder mOrder;

    private List<String> mImgs = new ArrayList<>();

    private MaterialDialog mMaterialDialog = null;
    private String orderId;
    private String[] prosfs;
    private String ordernumber;
    private String requestURL;
    private String request;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 111:
                    showToast("晒单成功"+request);
                    mMaterialDialog.dismiss();
                    finish();
                    break;
                case 222:
                    showToast("晒单失败,请重试"+request);
                    mMaterialDialog.dismiss();
                    break;
            }
        }
    };
    private int whichImg;
    private static final String IMAGE_FILE_NAME = "file_img.jpg";
    private String[] items = new String[]{"选择本地图片", "拍照"};
    private static final int SELECT_PIC_KITKAT = 49;
    private static final int IMAGE_REQUEST_CODE = 50;
    private static final int CAMERA_REQUEST_CODE = 51;
    private static final int RESULT_REQUEST_CODE = 52;
    private String file_imgPath1;
    private String file_imgPath2;
    private String file_imgPath3;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_comment;
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
        setTitle("订单评价");

        orderId =(String)getIntent().getExtras().get("orderId");
//        Toast.makeText(OrderCommentActivity.this,orderId+"",Toast.LENGTH_SHORT).show();
        loadData(orderId);
//        tvName.setText(mOrder.getName());
//        ProductItmeAdapter productItmeAdapter = new ProductItmeAdapter(mContext, mOrder.getProductlist());
//        lvProductlist.setAdapter(productItmeAdapter);

        mImgs.add("add");
        gvImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(mImgs.get(i), "add")) {
                    new PickPhotoView.Builder(OrderCommentActivity.this)
                            .setPickPhotoSize(3)   //select max size
                            .setShowCamera(true)   //is show camera
                            .setSpanCount(3)       //SpanCount
                            .start();
                }
            }
        });
        gvImg.setAdapter(new ImgAdapter());
        iv_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichImg=1;
                showDialog();
            }
        });
        iv_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichImg=2;
                showDialog();
            }
        });
        iv_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichImg=3;
                showDialog();
            }
        });
    }
    /**
     * 修改头像
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("添加方式")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                intentFromGallery.addCategory(Intent.CATEGORY_OPENABLE);
                                intentFromGallery.setType("image/jpeg"); // 设置文件类型
                                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
                                    startActivityForResult(intentFromGallery, SELECT_PIC_KITKAT);
                                }else{
                                    startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                                }
                                break;
                            case 1 :
                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                String state = Environment
                                        .getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    File path = Environment
                                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                    File file = new File(path, IMAGE_FILE_NAME);
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(file));
                                }
                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private void loadData(final String orderId) {
        Call<MemberOrderInfo> call= RetrofitService.getInstance().memberOrderInfo(AppApplication.TOKEN,orderId+"");
        call.enqueue(new Callback<MemberOrderInfo>() {
            @Override
            public void onResponse(Response<MemberOrderInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    MemberOrderInfo.MemberOrderInfoRes memRes = response.body().getRes();
                    ordernumber=memRes.getNumber();
                    tvName.setText(memRes.getShopname());
                    prosfs=new String[memRes.getProductlist().size()];
                    ProductItmeAdapter productItmeAdapter = new ProductItmeAdapter(mContext, memRes.getProductlist());
                    lvProductlist.setAdapter(productItmeAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            mImgs = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            mImgs.add("add");
            gvImg.setAdapter(new ImgAdapter());
        }
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE :
                    startPhotoZoom(data.getData());
                    break;
                case SELECT_PIC_KITKAT :
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE :
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(data.getData());
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE : // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }

    }

    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
//            Log.i("tag", "The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url=getPath(OrderCommentActivity.this,uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/jpeg");
        }else{
            intent.setDataAndType(uri, "image/jpeg");
        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 1080);
        intent.putExtra("outputY", 1920);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void getImageToView(Intent data){
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            switch (whichImg){
                case 1:
                    SettingImage settingImage1 = new SettingImage(photo, "file_img1");
                    file_imgPath1=settingImage1.imagePath();
                    iv_img1.setImageBitmap(photo);
//                    Toast.makeText(OrderCommentActivity.this,""+file_imgPath1,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    SettingImage settingImage2 = new SettingImage(photo, "file_img2");
                    file_imgPath2=settingImage2.imagePath();
                    iv_img2.setImageBitmap(photo);
//                    Toast.makeText(OrderCommentActivity.this,""+file_imgPath2,Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    SettingImage settingImage3 = new SettingImage(photo, "file_img3");
                    file_imgPath3=settingImage3.imagePath();
                    iv_img3.setImageBitmap(photo);
//                    Toast.makeText(OrderCommentActivity.this,""+file_imgPath3,Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String productlist = "";
        for (int i=0;i<prosfs.length;i++){
            if (prosfs[i]==null){
                productlist +=(i+1)+":"+5+",";
            }else{
                productlist +=prosfs[i]+",";
            }
        }
        productlist = productlist.substring(0, productlist.length() - 1);
//        Toast.makeText(OrderCommentActivity.this,productlist,Toast.LENGTH_SHORT).show();

        int isimg = 0;
            isimg = 1;
        addShowProduct((int) ratingFwtd.getRating(), (int) ratingCpzl.getRating(), (int) ratingShsd.getRating(), etComment.getText().toString().trim(), isimg, productlist);
    }

    private void addShowProduct(int fwtd, int cpzl, int shsd, String content, int isimg, String productlist) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderCommentActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<AddShowProduct>> mGetDataCallNet = RetrofitService.getInstance().addShowProduct(AppApplication.TOKEN, ordernumber, fwtd, cpzl, shsd, content, isimg, productlist);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<AddShowProduct>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<AddShowProduct>> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        if (file_imgPath1!=null||file_imgPath2!=null||file_imgPath3!=null){
                            saveImage(response.body().getRes().getShowid(), null, null, null);
                        }else{
                            Toast.makeText(getApplicationContext(), "晒单成功", Toast.LENGTH_SHORT).show();
                            mMaterialDialog.dismiss();
                            finish();
                        }
//                        if (mImgs.size() == 2) {
//                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), null, null);
//                        } else if (mImgs.size() == 3) {
//                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), new File(mImgs.get(1)), null);
//                        } else if (mImgs.size() == 4) {
//                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), new File(mImgs.get(1)), new File(mImgs.get(2)));
//                        } else {
//                            Toast.makeText(getApplicationContext(), "晒单成功", Toast.LENGTH_SHORT).show();
//                            mMaterialDialog.dismiss();
//                            finish();
//                        }
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
                    t.printStackTrace();
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

    private void saveImage(int showid, File file_img1, File file_img2, File file_img3) {
//        Toast.makeText(OrderCommentActivity.this,""+file_img1.getPath()+"|"+file_img2+"|"+file_img3,Toast.LENGTH_SHORT).show();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("token", AppApplication.TOKEN+"");
        params.put("type", 2+"");
        params.put("showid", showid+"");
        final Map<String, File> files = new TreeMap<String, File>();
        if (file_imgPath1!=null){
            files.put("file_img1", new File(file_imgPath1));
        }
        if (file_imgPath2!=null){
            files.put("file_img2", new File(file_imgPath2));
        }
        if (file_imgPath3!=null){
            files.put("file_img3", new File(file_imgPath3));
        }
        requestURL= Constants.BASE_URL+"/SaveImage.aspx?token="+AppApplication.TOKEN+"&type=2&showid="+showid;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    request = UploadUtil.post(requestURL, params, files);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ((request+"").contains("成功")) { // 请求成功
                    Message message=new Message();
                    message.what=111;
                    handler.sendMessage(message);
                } else { // 请求失败
                    Message message=new Message();
                    message.what=222;
                    handler.sendMessage(message);
                }
            }
        }).start();
//        RequestBody requestBody;
//        MultipartBody.Part part1 = null, part2 = null, part3 = null;
//        if (file_img1 != null) {
//            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img1);
//            part1 = MultipartBody.Part.createFormData("file_img1", file_img1.getName(), requestBody);
//        }
//        if (file_img2 != null) {
//            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img2);
//            part2 = MultipartBody.Part.createFormData("file_img2", file_img2.getName(), requestBody);
//        }
//        if (file_img3 != null) {
//            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img3);
//            part3 = MultipartBody.Part.createFormData("file_img3", file_img3.getName(), requestBody);
//        }
//
//        if (NetUtils.isNetworkConnected(mContext)) {
//            mMaterialDialog = new MaterialDialog.Builder(OrderCommentActivity.this)
//                    .content(R.string.loading)
//                    .cancelable(false)
//                    .progress(true, 0)
//                    .progressIndeterminateStyle(false)
//                    .show();
//            Call<NetBaseEntity> mGetDataCallNet = RetrofitService.getInstance().saveImage(AppApplication.TOKEN, 2, showid, part1, part2, part3);
//            mGetDataCallNet.enqueue(new Callback<NetBaseEntity>() {
//                @Override
//                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
//                    Toast.makeText(getApplicationContext(), "晒单成功", Toast.LENGTH_SHORT).show();
//                    mMaterialDialog.dismiss();
//                    finish();
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    mMaterialDialog.dismiss();
//                    t.printStackTrace();
//                }
//            });
//        } else {
//            toggleNetworkError(true, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //错误后的点击屏幕的处理
//                }
//            });
//        }
    }


    private class ImgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImgs.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ImgViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order_comment_img, null);
                holder = new ImgViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ImgViewHolder) view.getTag();
            }
            if (TextUtils.equals(mImgs.get(i), "add")) {
                Glide.with(getApplicationContext()).load(R.mipmap.jiat).crossFade().into(holder.ivItemImg);
            } else {
                Glide.with(getApplicationContext()).load(mImgs.get(i)).crossFade().into(holder.ivItemImg);
            }
            return view;
        }


    }

    static class ImgViewHolder {
        @BindView(R.id.iv_item_img)
        ImageView ivItemImg;

        ImgViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class ProductItmeAdapter extends BaseAdapter {
        private Context context;
        private List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> entityList;

        ProductItmeAdapter(Context context, List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> entityList) {
            super();
            this.context = context;
            this.entityList = entityList;
        }

        @Override
        public int getCount() {
            return this.entityList.size();
        }

        @Override
        public MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist getItem(int i) {
            return entityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ProductViewHolder   holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order_comment_product, null);
                holder = new ProductViewHolder(view);
                view.setTag(holder);
            }
            holder = (ProductViewHolder) view.getTag();
            MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist entity = getItem(i);
            Glide.with(getApplicationContext()).load(entity.getImgurl()).crossFade().into(holder.ivMineOrderProductItemImg);
            holder.tvItemOrderProductTitle.setText(entity.getName());
            holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                    Toast.makeText(OrderCommentActivity.this,i+":"+v,Toast.LENGTH_SHORT).show();
//                    mOrder.getProductlist().get(i).setRating((int) v);
                    prosfs[i]=(i+1)+":"+(int) v;
//                    commentInfo.getProductlist().get(i).setPropos(i);
//                    commentInfo.getProductlist().get(i).setFenShu((int)v);
//                    Toast.makeText(OrderCommentActivity.this,prosfs[i]+"",Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }


    }

    static class ProductViewHolder {
        @BindView(R.id.iv_mine_order_product_item_img)
        ImageView ivMineOrderProductItemImg;
        @BindView(R.id.tv_item_order_product_title)
        TextView  tvItemOrderProductTitle;
        @BindView(R.id.rating)
        RatingBar rating;

        ProductViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    /**
     * <br>功能简述:4.4及以上获取图片的方法
     * <br>功能详细描述:
     * <br>注意:
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
