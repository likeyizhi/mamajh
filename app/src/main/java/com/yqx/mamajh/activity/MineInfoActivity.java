package com.yqx.mamajh.activity;

import android.annotation.TargetApi;
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
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
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
import com.yqx.mamajh.bean.BaseEntity;
import com.yqx.mamajh.bean.MemberInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.fragment.MineFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.AppConstant;
import com.yqx.mamajh.utils.SPUtils;
import com.yqx.mamajh.utils.SettingImage;
import com.yqx.mamajh.utils.UploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.app.AlertDialog;
import android.os.Handler;
import org.json.JSONException;

public class MineInfoActivity extends BaseActivity {

    @BindView(R.id.iv_info_avatar)
    CircleImageView ivInfoAvatar;
    @BindView(R.id.lay_info_avatar)
    LinearLayout    layInfoAvatar;
    @BindView(R.id.tv_info_jhid)
    TextView        tvInfoJhid;
    @BindView(R.id.tv_info_nickname)
    TextView        tvInfoNickname;
    @BindView(R.id.lay_info_nickname)
    LinearLayout    layInfoNickname;
    @BindView(R.id.tv_info_sex)
    TextView        tvInfoSex;
    @BindView(R.id.lay_info_sex)
    LinearLayout    layInfoSex;
    @BindView(R.id.tv_info_phone)
    TextView        tvInfoPhone;
    @BindView(R.id.lay_info_phone)
    LinearLayout    layInfoPhone;
    @BindView(R.id.lay_info_address)
    LinearLayout    layInfoAddress;
    @BindView(R.id.lay_info_account_safe)
    LinearLayout    layInfoAccountSafe;
    /** 头像名称 */
    private static final String IMAGE_FILE_NAME = "file_img.jpg";
    private String[] items = new String[]{"选择本地图片", "拍照"};
    private static final int SELECT_PIC_KITKAT = 49;
    private static final int IMAGE_REQUEST_CODE = 50;
    private static final int CAMERA_REQUEST_CODE = 51;
    private static final int RESULT_REQUEST_CODE = 52;
    private MaterialDialog mMaterialDialog = null;

    public MemberInfo mMemberInfo;

    private List<String> mImgs = new ArrayList<>();
    private String file_imgPath;
    private String request;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 111:
                    showToast("修改头像成功");
                    getData(AppApplication.TOKEN);
                    break;
                case 222:
                    showToast("修改头像失败");
                    break;
            }
        }
    };
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_info;
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
        setTitle("个人资料");
        getData(AppApplication.TOKEN);
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

    @OnClick({R.id.lay_info_avatar, R.id.lay_info_nickname, R.id.lay_info_sex, R.id.lay_info_address, R.id.lay_info_account_safe, R.id.btn_info_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_info_avatar:
//                showToast("修改头像");
                showDialog();
//                new PickPhotoView.Builder(MineInfoActivity.this)
//                        .setPickPhotoSize(1)   //select max size
//                        .setShowCamera(true)   //is show camera
//                        .setSpanCount(4)       //SpanCount
//                        .start();
                break;
            case R.id.lay_info_nickname:
                mMaterialDialog = new MaterialDialog.Builder(this)
                        .title("修改昵称")
                        .content("请输入昵称")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .inputRange(2, 16)
                        .neutralText(R.string.cancel)
                        .positiveText(R.string.btn_ok)
                        .input("昵称", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//                                showToast(input.toString());
                                changeNameOrSex(input.toString(),"");
                            }
                        }).show();
                break;
            case R.id.lay_info_sex:
                mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                        .title("修改性别")
                        .neutralText(R.string.cancel)
                        .neutralColorRes(R.color.gray_text)
                        .negativeText("女")
                        .negativeColorRes(R.color.red_500)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                showToast("女");
                                changeNameOrSex("",0+"");
                            }
                        })
                        .positiveText("男")
                        .positiveColorRes(R.color.blue_500)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                showToast("男");
                                changeNameOrSex("",1+"");
                            }
                        })
                        .show();
                break;
            case R.id.lay_info_address:
                readyGo(MineAddressActivity.class);
                break;
            case R.id.lay_info_account_safe:
                Bundle bundle=new Bundle();
                bundle.putString("changeType","修改账户密码");
                readyGo(ForgetPwdActivity.class, bundle);
                break;
            case R.id.btn_info_loginout:
                mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                        .title("退出")
                        .content("是否退出登陆")
                        .negativeText(R.string.cancel)
                        .negativeColorRes(R.color.gray_text)
                        .positiveText(R.string.btn_ok)
                        .positiveColorRes(R.color.colorPrimary)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                AppApplication.TOKEN = null;
                                AppApplication.memeberIndex = null;
                                SPUtils.putString(getApplicationContext(), AppConstant.SP_TOKEN, "");
                                MineFragment.LOGOUT = true;
                                finish();
                            }
                        })
                        .show();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
//            mImgs = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_PATH);
//            Toast.makeText(MineInfoActivity.this,""+data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT),Toast.LENGTH_SHORT).show();
//            saveImageHead(new File(data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT)+""));
//            startPhotoZoom(data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT)+"");
//        }
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
                        startPhotoZoom(Uri.fromFile(tempFile));
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

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            SettingImage settingImage = new SettingImage(photo, "file_img");
            file_imgPath=settingImage.imagePath();
//            Toast.makeText(MineInfoActivity.this,"path="+file_imgPath,Toast.LENGTH_SHORT).show();
            final Map<String, String> params = new HashMap<String, String>();
            params.put("token", AppApplication.TOKEN+"");
            params.put("type", 1+"");
            final Map<String, File> files = new TreeMap<String, File>();
            final String requestURL = Constants.BASE_URL+"/SaveImage.aspx?token="+AppApplication.TOKEN+"&type=1";
            if (!file_imgPath.equals("")) {
                files.put("file_img", new File(file_imgPath));
                Toast.makeText(MineInfoActivity.this,""+file_imgPath,Toast.LENGTH_SHORT).show();
            }
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
        }
    }


    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
//            Log.i("tag", "The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url=getPath(MineInfoActivity.this,uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/jpeg");
        }else{
            intent.setDataAndType(uri, "image/jpeg");
        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void saveImageHead(File file_img){
        RequestBody requestBody;
        MultipartBody.Part part = null;
        requestBody = RequestBody.create(MediaType.parse("image/png"), file_img);
        part = MultipartBody.Part.createFormData("file_img", file_img.getName(), requestBody);
//        Toast.makeText(MineInfoActivity.this,""+file_img.getName(),Toast.LENGTH_SHORT).show();
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity> mGetDataCallNet = RetrofitService.getInstance().saveImageHead(AppApplication.TOKEN, 1, part);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    Toast.makeText(getApplicationContext(), ""+response.body().getMes(), Toast.LENGTH_SHORT).show();
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
    private void changeNameOrSex(String name, String sex) {
        Call<NetBaseEntity> call=RetrofitService.getInstance().memberInfoSeave(AppApplication.TOKEN, name, sex);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    getData(AppApplication.TOKEN);
                    showToast(""+response.body().getMes());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showToast(""+t);
            }
        });
    }

    private void getData(String token) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<MemberInfo>> mGetDataCallNet = RetrofitService.getInstance().memberInfo(token);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<MemberInfo>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<MemberInfo>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mMemberInfo = response.body().getRes();
                        Glide.with(getApplicationContext())
                                .load(mMemberInfo.getFace())
                                .error(R.mipmap.mmjhicon512)
                                .placeholder(R.mipmap.mmjhicon512)
                                .crossFade()
                                .into(ivInfoAvatar);

                        tvInfoJhid.setText(mMemberInfo.getAccount() + "");
                        tvInfoNickname.setText(mMemberInfo.getNickname());
                        tvInfoPhone.setText(mMemberInfo.getMobile());
                        if (mMemberInfo.getSex() == 1) {
                            tvInfoSex.setText("男");
                        } else {
                            tvInfoSex.setText("女");

                        }
                    } else {
                        showToast(response.body().getMes());
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

    /**
     * 修改头像
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("修改方式")
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
