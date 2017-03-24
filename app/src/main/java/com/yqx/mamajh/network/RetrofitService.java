package com.yqx.mamajh.network;

import android.util.Log;

import com.github.obsessive.library.utils.TLog;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.AccountBalance;
import com.yqx.mamajh.bean.AccountIntegral;
import com.yqx.mamajh.bean.AddShowProduct;
import com.yqx.mamajh.bean.BankCard;
import com.yqx.mamajh.bean.Cartlist;
import com.yqx.mamajh.bean.ClassifivationInfoEntity;
import com.yqx.mamajh.bean.ClassifyEntity;
import com.yqx.mamajh.bean.CollectProduct;
import com.yqx.mamajh.bean.CollectScoreProduct;
import com.yqx.mamajh.bean.CollectShop;
import com.yqx.mamajh.bean.Coupon;
import com.yqx.mamajh.bean.CreateOrder;
import com.yqx.mamajh.bean.DeliveryAddress;
import com.yqx.mamajh.bean.DeliveryInfo;
import com.yqx.mamajh.bean.DeliveryWay;
import com.yqx.mamajh.bean.DemoFormtEntity;
import com.yqx.mamajh.bean.EvaluateEntity;
import com.yqx.mamajh.bean.EvaluatesEntity;
import com.yqx.mamajh.bean.FeedbackType;
import com.yqx.mamajh.bean.HelpCenterContent;
import com.yqx.mamajh.bean.HelpCenterIndex;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.bean.HotGoodsEntity;
import com.yqx.mamajh.bean.MallMyList;
import com.yqx.mamajh.bean.MallProductDatails;
import com.yqx.mamajh.bean.MemberOrderInfo;
import com.yqx.mamajh.bean.ProInfo;
import com.yqx.mamajh.bean.SearchResultBean;
import com.yqx.mamajh.bean.ShopCategoryEntity;
import com.yqx.mamajh.bean.ShopInformationEntity;
import com.yqx.mamajh.bean.ShopList;
import com.yqx.mamajh.bean.MemberInfo;
import com.yqx.mamajh.bean.MemberOrder;
import com.yqx.mamajh.bean.MemeberIndex;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.OrderInfo;
import com.yqx.mamajh.bean.ProdectItemEntity;
import com.yqx.mamajh.bean.ProfitEntity;
import com.yqx.mamajh.bean.RankingEntity;
import com.yqx.mamajh.bean.SearchHistoryListEntity;
import com.yqx.mamajh.bean.SendRegMessage;
import com.yqx.mamajh.bean.ShopInfoEntity;
import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;
import com.yqx.mamajh.bean.Token;
import com.yqx.mamajh.bean.TopUpIntegral;
import com.yqx.mamajh.bean.TopUpOrder;
import com.yqx.mamajh.bean.TopUpPrice;
import com.yqx.mamajh.bean.UserCenterAccount;
import com.yqx.mamajh.bean.UserCenterWithdrawal;
import com.yqx.mamajh.bean.WeiXinPay;
import com.yqx.mamajh.bean.WithdrawalLog;
import com.yqx.mamajh.bean.WithdrawalsSave;
import com.yqx.mamajh.dbcity.City;
import com.yqx.mamajh.update.UpdateInfo;
import com.yqx.mamajh.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by young on 2016/11/6.
 */

public class RetrofitService {
    private static RetrofitService retrofitService = new RetrofitService();
    private static RetrofitInterface retrofitInterface;
    private        String            methodName;

    private RetrofitService() {
        initRetrofit();
    }

    public static RetrofitService getInstance() {
        if (retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

    private void initRetrofit() {
        OkHttpClient           okHttpClient = new OkHttpClient();
        HttpLoggingInterceptor logging      = new HttpLoggingInterceptor();
        // set your desired log level
        if (TLog.isLogEnable) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        okHttpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
        okHttpClient.interceptors().add(interceptor);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }


    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable(AppApplication.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.i("zl", "This is network error...");
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable(AppApplication.getInstance())) {
                int maxAge = 60 * 60; // read from cache for 1 minute
//                int maxAge = 60 * 10;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };

    /**
     * 应用更新
     *
     * @param version
     * @return
     */
    public Call<UpdateInfo> getUpdateInfo(int version) {
        return retrofitInterface.getUpdateInfo(version);
    }

    public Call<NetBaseEntity<MemeberIndex>> memeberIndex(String token) {
        return retrofitInterface.memeberIndex(token);
    }

    public Call<NetBaseEntity<List<DeliveryAddress>>> deliveryInfoList(String token, int id) {
        return retrofitInterface.deliveryInfoList(token, id);
    }

    public Call<NetBaseEntity> selectAddress(String token, int id) {
        return retrofitInterface.selectAddress(token, id);
    }

    public Call<NetBaseEntity> setDefaultAddress(String token, String id) {
        return retrofitInterface.setDefaultAddress(token, id);
    }

    public Call<NetBaseEntity> saveDeliveryInfo(String token, Map<String, String> params) {
        return retrofitInterface.saveDeliveryInfo(token, params);
    }

    public Call<NetBaseEntity<HomeInfoEntity>> getHomeInfo(String x, String y) {
        return retrofitInterface.getHomeInfo(x, y);
    }

    public Call<NetBaseEntity<Token>> login(String uname, String password) {
        return retrofitInterface.login(uname, password);
    }

    public Call<NetBaseEntity<SendRegMessage>> sendRegMessage(String mobile, int t) {
        return retrofitInterface.sendRegMessage(mobile, t);
    }

    public Call<NetBaseEntity<Token>> registered(String mobile, String password, String verifyCode, int obj) {
        return retrofitInterface.registered(mobile, password, verifyCode, obj);
    }

    public Call<NetBaseEntity> forgotPassword(String phone, String newpwd, String code, int obj) {
        return retrofitInterface.forgotPassword(phone, newpwd, code, obj);
    }

    public Call<NetBaseEntity<MemberInfo>> memberInfo(String token) {
        return retrofitInterface.memberInfo(token);
    }

    public Call<NetBaseEntity<Cartlist>> getShopCartList(String token) {
        return retrofitInterface.getShopCartList(token);
    }

    public Call<NetBaseEntity> addShopCart(String token, int id) {
        return retrofitInterface.addShopCart(token, id);
    }

    public Call<NetBaseEntity> updateShopCartCount(String token, int id, int count) {
        return retrofitInterface.updateShopCartCount(token, id, count);
    }

    public Call<NetBaseEntity> deleteShopCartProduct(String token, int id) {
        return retrofitInterface.deleteShopCartProduct(token, id);
    }

    public Call<NetBaseEntity<ClassifivationInfoEntity>> getClassificationList() {
        return retrofitInterface.getClassificationList();
    }

    public Call<NetBaseEntity<List<CollectProduct>>> collectProductList(String token, int p, int size) {
        return retrofitInterface.collectProductList(token, p, size);
    }

    public Call<NetBaseEntity<List<CollectShop>>> collectShopList(String token, int p, int size) {
        return retrofitInterface.collectShopList(token, p, size);
    }

    public Call<NetBaseEntity<List<CollectScoreProduct>>> collectScoreProductList(String token, int p, int size) {
        return retrofitInterface.collectScoreProductList(token, p, size);
    }

    public Call<NetBaseEntity<SearchHistoryListEntity>> getSearchHistory() {
        return retrofitInterface.getSearchHistory();
    }

    public Call<NetBaseEntity<List<MemberOrder>>> memberOrderAll(String token, String state, int p, int size) {
        return retrofitInterface.memberOrderAll(token, state, p, size);
    }

    public Call<NetBaseEntity<UserCenterAccount>> userCenterAccount(String token) {
        return retrofitInterface.userCenterAccount(token);
    }

    public Call<NetBaseEntity<List<AccountBalance>>> userCenterAccountBalance(String token, int p, int size) {
        return retrofitInterface.userCenterAccountBalance(token, p, size);
    }

    public Call<NetBaseEntity<List<TopUpPrice>>> topUpPriceList(String token) {
        return retrofitInterface.topUpPriceList(token);
    }

    public Call<NetBaseEntity<TopUpOrder>> createTopUpOrder(String token, String money) {
        return retrofitInterface.createTopUpOrder(token, money);
    }

    public Call<NetBaseEntity<List<AccountIntegral>>> userCenterAccountIntegral(String token, int type, int p, int size) {
        return retrofitInterface.userCenterAccountIntegral(token, type, p, size);
    }

    public Call<NetBaseEntity<TopUpIntegral>> getIntegralMallRechargeList(String token) {
        return retrofitInterface.getIntegralMallRechargeList(token);
    }

    public Call<NetBaseEntity<TopUpOrder>> createIntegralMallRechargeOrder(String token, String money) {
        return retrofitInterface.createIntegralMallRechargeOrder(token, money);
    }

    public Call<NetBaseEntity<List<HelpCenterIndex>>> helpCenterIndex() {
        return retrofitInterface.helpCenterIndex();
    }

    public Call<NetBaseEntity<List<HelpCenterIndex.HelpCenterList>>> helpCenterList(int cid) {
        return retrofitInterface.helpCenterList(cid);
    }

    public Call<NetBaseEntity<HelpCenterContent>> helpCenterContent(int id) {
        return retrofitInterface.helpCenterContent(id);
    }

    public Call<NetBaseEntity<List<FeedbackType>>> feedbackTypeList(String token) {
        return retrofitInterface.feedbackTypeList(token);
    }

    public Call<NetBaseEntity> feedbackSubmit(String token, String type, String content) {
        return retrofitInterface.feedbackSubmit(token, type, content);
    }

    public Call<NetBaseEntity<List<BankCard>>> userCenterBankCard(String token) {
        return retrofitInterface.userCenterBankCard(token);
    }

    public Call<NetBaseEntity> userCenterBankCardAdd(String token, String name, String number, String bank) {
        return retrofitInterface.userCenterBankCardAdd(token, name, number, bank);
    }

    public Call<NetBaseEntity<OrderInfo>> getDefaultOrderInfo(String token, Map<String, String> params) {
        return retrofitInterface.getDefaultOrderInfo(token, params);
    }

    public Call<NetBaseEntity<OrderInfo>> getOrderTotal(String token, Map<String, String> params) {
        return retrofitInterface.getOrderTotal(token, params);
    }

    public Call<NetBaseEntity<DeliveryWay>> deliveryWayList(String token, int id) {
        return retrofitInterface.deliveryWayList(token, id);
    }

    public Call<NetBaseEntity<Coupon>> shopOrderCoupon(String token, int id) {
        return retrofitInterface.shopOrderCoupon(token, id);
    }

    public Call<NetBaseEntity<CreateOrder>> createOrder(String token, Map<String, String> params) {
        return retrofitInterface.createOrder(token, params);
    }

    public Call<NetBaseEntity<WeiXinPay>> getOrderWeiXinPayParam(String token, String ordernumber, String useBlance) {
        return retrofitInterface.getOrderWeiXinPayParam(token, ordernumber, useBlance);
    }

    public Call<NetBaseEntity<Coupon>> userCenterCouponList(String token, int type) {
        return retrofitInterface.userCenterCouponList(token, type);
    }

    public Call<UserCenterWithdrawal> userCenterWithdrawal(String token, int type) {
        return retrofitInterface.userCenterWithdrawal(token, type);
    }

    public Call<WithdrawalLog> userCenterWithdrawalList(String token) {
        return retrofitInterface.userCenterWithdrawalList(token);
    }

    public Call<WithdrawalsSave> userCenterWithdrawal(String token, int type, String money, String id) {
        return retrofitInterface.userCenterWithdrawal(token, type, money, id);
    }

    public Call<NetBaseEntity> orderCancel(String token, String orderid) {
        return retrofitInterface.orderCancel(token, orderid);
    }

    public Call<NetBaseEntity<AddShowProduct>> addShowProduct(String token, String orderNumber, int fwtd, int cpzl, int shsd, String content, int isimg, String productlist) {
        return retrofitInterface.addShowProduct(token, orderNumber, fwtd, cpzl, shsd, content, isimg, productlist);
    }

    public Call<NetBaseEntity> saveImage(String token, int type, int showid, MultipartBody.Part file_img1, MultipartBody.Part file_img2, MultipartBody.Part file_img3) {
        return retrofitInterface.saveImage(token, type, showid, file_img1, file_img2, file_img3);
    }

    public Call<NetBaseEntity<ShopInfoEntity>> getShopInfo(String token,String userId) {
        return retrofitInterface.getShopInfo(token,userId);
    }

    public Call<NetBaseEntity<ArrayList<ProdectItemEntity>>> getProdectList(String cid, String key, String p, String Size,
                                                                     String sp, String sc, String bid, String lp,
                                                                     String hp){
        return retrofitInterface.getProdectList(cid, key, p, Size, sp, sc, bid, lp, hp);
    }

    public Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> getSpecialOfferList(int state, int page) {
        return retrofitInterface.getSpecialOfferList(state, page);
    }

    public Call<DemoFormtEntity> getCreditList(){
        return retrofitInterface.getCreditList();
    }

    public Call<ClassifyEntity> getClassify() {
        return retrofitInterface.getClassify();
    }

    public Call<RankingEntity> getRankingList(int page, String size) {
        return retrofitInterface.getRankingList(page, size);
    }

    public Call<ProfitEntity> getProfit(String page) {
        return retrofitInterface.getProfit(page);
    }

    public Call<NetBaseEntity<ShopList>> getHotShopByS(String x, String y, String os,String p){
        return retrofitInterface.getHotShopByS(x,y,os,p);
    }
    public Call<NetBaseEntity<ShopList>> getHotShopByD(String x, String y, String od, String p, int size){
        return retrofitInterface.getHotShopByD(x,y,od,p,size);
    }

    public Call<NetBaseEntity> deleteDeliveryInfo(String token, String id) {
        return retrofitInterface.deleteDeliveryInfo(token, id);
    }

    public Call<SearchResultBean> getSearchResult(String k, String x, String y) {
        return retrofitInterface.getSearchResult(k,x,y);
    }

    public Call<ProInfo> getProInfo(int id,String token) {
        return retrofitInterface.getProInfo(id,token);
    }

    public Call<NetBaseEntity> addProductCollect(String token,int pid) {
        return retrofitInterface.addProductCollect(token,pid);
    }

    public Call<NetBaseEntity> deleteShopCollect(String token,int shopid) {
        return retrofitInterface.deleteShopCollect(token,shopid);
    }
    public Call<NetBaseEntity> addShopCollect(String token,int shopid) {
        return retrofitInterface.addShopCollect(token,shopid);
    }

    public Call<ShopInformationEntity> getShopInformation(String id) {
        return retrofitInterface.getShopInformation(id);
    }

    public Call<ShopCategoryEntity> getShopClassify(String id) {
        return retrofitInterface.getShopClassify(id);
    }

    public Call<HotGoodsEntity> getHotGoods(String id, String cid, int page, String pageSize) {
        return retrofitInterface.getHotGoods(id, cid, page, pageSize);
    }

    public Call<EvaluateEntity> loadRvaluate(String id) {
        return retrofitInterface.getEvaluate(id);
    }

    public Call<EvaluatesEntity> loadRvaluates(String id, String status, int page, String pageSize) {
        return retrofitInterface.getEvaluates(id, status, page, pageSize);
    }

    public Call<NetBaseEntity> deleteCollectProduct(String token, int pid) {
        return retrofitInterface.deleteCollectProduct(token, pid);
    }

    public Call<NetBaseEntity> orderSign(String token, String orderid) {
        return retrofitInterface.orderSign(token, orderid);
    }

    public Call<City> getAreaDictionary(int pid) {
        return retrofitInterface.getAreaDictionary(pid);
    }

    public Call<DeliveryInfo> getDeliveryInfo(String token,int id) {
        return retrofitInterface.getDeliveryInfo(token,id);
    }

    public Call<MemberOrderInfo> memberOrderInfo(String token, String id) {
        return retrofitInterface.memberOrderInfo(token,id);
    }

    public Call<MallProductDatails> getMallProductDatails(String id){
        return retrofitInterface.getMallProductDatails(id);
    }

    public Call<MallMyList> integralMallMyList(String token, int cid, int o,int p, int psize){
        return retrofitInterface.integralMallMyList(token, cid, o, p, psize);
    }

}
