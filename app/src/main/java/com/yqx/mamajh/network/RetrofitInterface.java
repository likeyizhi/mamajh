package com.yqx.mamajh.network;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by young on 2016/11/6.
 */

public interface RetrofitInterface {

    // 获取更新信息
    @GET("eye/check_update")
    Call<UpdateInfo> getUpdateInfo(@Query("code") int version);

    /**
     * 47、会员收货地址
     * <p>
     * TOKEN 	String	是
     * Id	Int	否	1	会员ID
     */
    @GET("DeliveryInfoList.aspx")
    Call<NetBaseEntity<List<DeliveryAddress>>> deliveryInfoList(@Query("token") String token, @Query("id") int id);

    /**
     * 48、 选择收货地址
     * <p>
     * Id	Int	是	1	配送地址ID
     * TOKEN 	String	是
     */
    @GET("SelectAddress.aspx")
    Call<NetBaseEntity> selectAddress(@Query("token") String token, @Query("id") int id);

    /**
     * 49、 设置默认收货地址
     * <p>
     * Id	Int	是	1	配送地址ID
     * TOKEN 	String	是
     */
    @GET("SetDefaultAddress.aspx")
    Call<NetBaseEntity> setDefaultAddress(@Query("token") String token, @Query("id") String id);

    /**
     * 50、 修改添加会员收货地址
     * <p>
     * TOKEN 	String	是
     * updateid	Int	是	1	配送地址ID
     * Name	String	是	李华	收货人
     * address	String	是	北京三里屯k吧	收货地址
     * Phone	String	是	138776222	移动电话
     * Phone2	String	否	400-820820	固定电话
     * PostCode	String	否	3322000	邮编
     * areaID	Int	是	1	地区ID
     */
    @GET("SaveDeliveryInfo.aspx")
    Call<NetBaseEntity> saveDeliveryInfo(@Query("token") String token, @QueryMap Map<String, String> params);

    /**
     * 64、个人中心页
     * TOKEN 	String	是
     */
    @GET("MemeberIndex.aspx")
    Call<NetBaseEntity<MemeberIndex>> memeberIndex(@Query("token") String token);

    /**
     * 65、个人资料页
     * TOKEN 	String	是
     */
    @GET("MemberInfo.aspx")
    Call<NetBaseEntity<MemberInfo>> memberInfo(@Query("token") String token);

    //首页信息（轮播图，热门店铺）
    @GET("GetIndexContent.aspx")
    Call<NetBaseEntity<HomeInfoEntity>> getHomeInfo(@Query("x") String x, @Query("y") String y);

    /**
     * 92、发送手机验证码
     * mobile	String	是		手机号
     * t	Int	是		发送类型[1=注册,2=找回密码]
     *
     * @return
     */
    @GET("SendRegMessage.aspx")
    Call<NetBaseEntity<SendRegMessage>> sendRegMessage(@Query("mobile") String mobile, @Query("t") int t);

    /**
     * 93、忘记密码
     * <p>
     * phone	String	是		手机号
     * newpwd	String	是		新密码
     * Code	String	是		手机验证码
     * Obj	Int	是		手机验证码标示
     *
     * @return
     */
    @GET("ForgotPassword.aspx")
    Call<NetBaseEntity> forgotPassword(@Query("phone") String phone, @Query("newpwd") String newpwd,
                                       @Query("Code") String code, @Query("Obj") int obj);

    /**
     * 94、注册
     * <p>
     * mobile	String	是		手机号
     * password	String	是		密码
     * verifyCode	String	是		手机验证码
     * Obj	Int	是		手机验证码标示
     *
     * @return
     */
    @GET("Registered.aspx")
    Call<NetBaseEntity<Token>> registered(@Query("mobile") String mobile, @Query("password") String password,
                                          @Query("verifyCode") String verifyCode, @Query("Obj") int obj);

    /**
     * 95、登录
     *
     * @param uname
     * @param password
     */
    @GET("Login.aspx")
    Call<NetBaseEntity<Token>> login(@Query("uname") String uname, @Query("password") String password);

    /**
     * 41、购物车
     */
    @GET("GetShopCartList.aspx")
    Call<NetBaseEntity<Cartlist>> getShopCartList(@Query("token") String token);

    /**
     * 42、添加商品到购物车
     * <p>
     * Id	Int	是	1	商品ID
     * token 	String	是
     * Count	Int	是	1	商品数量
     */
    @GET("AddShopCart.aspx")
    Call<NetBaseEntity> addShopCart(@Query("token") String token, @Query("Id") int id);

    /**
     * 43、修改购物车商品数量
     * <p>
     * Id	Int	是	1	商品ID
     * token 	String	是
     */
    @GET("UpdateShopCartCount.aspx")
    Call<NetBaseEntity> updateShopCartCount(@Query("token") String token, @Query("Id") int id, @Query("Count") int count);

    /**
     * 44、删除购物车中对应商品
     * <p>
     * Id	Int	是	1	商品ID
     * token 	String	是
     */
    @GET("DeleteShopCartProduct.aspx")
    Call<NetBaseEntity> deleteShopCartProduct(@Query("token") String token, @Query("Id") int id);


    @GET("CategoryList.aspx")
    Call<NetBaseEntity<ClassifivationInfoEntity>> getClassificationList();

    /**
     * 84、我的收藏商品
     * <p>
     * token 	String	是
     * P	Int	否		页码
     * Size	Int	否		显示条数
     */
    @GET("CollectProductList.aspx")
    Call<NetBaseEntity<List<CollectProduct>>> collectProductList(@Query("token") String token, @Query("P") int p, @Query("Size") int size);

    /**
     * 85、我的收藏店铺
     * <p>
     * token 	String	是
     * P	Int	否		页码
     * Size	Int	否		显示条数
     */
    @GET("CollectShopList.aspx")
    Call<NetBaseEntity<List<CollectShop>>> collectShopList(@Query("token") String token, @Query("P") int p, @Query("Size") int size);

    /**
     * 86、我的收藏积分商品
     * <p>
     * token 	String	是
     * P	Int	否		页码
     * Size	Int	否		显示条数
     */
    @GET("CollectScoreProductList.aspx")
    Call<NetBaseEntity<List<CollectScoreProduct>>> collectScoreProductList(@Query("token") String token, @Query("P") int p, @Query("Size") int size);

    //搜索页
    @GET("GetSearchList.aspx")
    Call<NetBaseEntity<SearchHistoryListEntity>> getSearchHistory();


    /**
     * 68、会员订单
     * token 	String	是
     * state	String	否	p	null=全部;p=待付款;s=待发货;r=代收货;d=待评价
     * P	Int	否	1	页码
     * Size	Int	否	10	显示数量
     */
    @GET("MemberOrderAll.aspx")
    Call<NetBaseEntity<List<MemberOrder>>> memberOrderAll(@Query("token") String token, @Query("state") String state, @Query("P") int p, @Query("Size") int size);

    /**
     * 71、我的资产
     */
    @GET("UserCenterAccount.aspx")
    Call<NetBaseEntity<UserCenterAccount>> userCenterAccount(@Query("token") String token);

    /**
     * 72、账号余额收支明细
     * token 	string	是
     * P	Int	否	1	页码
     * Size	Int	否	10	显示条数
     */
    @GET("UserCenterAccountBalance.aspx")
    Call<NetBaseEntity<List<AccountBalance>>> userCenterAccountBalance(@Query("token") String token, @Query("P") int p, @Query("Size") int size);

    /**
     * 82、充值金额列表
     * token 	string	是
     */
    @GET("TopUpPriceList.aspx")
    Call<NetBaseEntity<List<TopUpPrice>>> topUpPriceList(@Query("token") String token);

    /**
     * 83、充值支付单
     * token 	String	是
     * money	String	是		充值金额
     */
    @GET("CreateTopUpOrder.aspx")
    Call<NetBaseEntity<TopUpOrder>> createTopUpOrder(@Query("token") String token, @Query("money") String money);

    /**
     * 73、江湖币页
     * token 	string	是
     * type = 0是 金币，=1是银币
     * P	Int	否	1	页码
     * Size	Int	否	10	显示条数
     */
    @GET("UserCenterAccountIntegral.aspx")
    Call<NetBaseEntity<List<AccountIntegral>>> userCenterAccountIntegral(@Query("token") String token, @Query("type") int type, @Query("P") int p, @Query("Size") int size);

    /**
     * 82、充值金额列表
     * token 	string	是
     */
    @GET("GetIntegralMallRechargeList.aspx")
    Call<NetBaseEntity<TopUpIntegral>> getIntegralMallRechargeList(@Query("token") String token);

    /**
     * 24、创建江湖币充值订单
     * token 	String	是
     * money	String	是		充值金额
     */
    @GET("CreateIntegralMallRechargeOrder.aspx")
    Call<NetBaseEntity<TopUpOrder>> createIntegralMallRechargeOrder(@Query("token") String token, @Query("money") String money);

    /**
     * 87、常见问题页
     */
    @GET("HelpCenterIndex.aspx")
    Call<NetBaseEntity<List<HelpCenterIndex>>> helpCenterIndex();

    /**
     * 88、常见问题列表
     * <p>
     * cid	Int	是		分类ID
     */
    @GET("HelpCenterList.aspx")
    Call<NetBaseEntity<List<HelpCenterIndex.HelpCenterList>>> helpCenterList(@Query("cid") int cid);

    /**
     * 89、常见问题内容页
     * <p>
     * id	Int	是		问题ID
     */
    @GET("HelpCenterContent.aspx")
    Call<NetBaseEntity<HelpCenterContent>> helpCenterContent(@Query("id") int id);

    /**
     * 90、意见反馈字典
     */
    @GET("FeedbackTypeList.aspx")
    Call<NetBaseEntity<List<FeedbackType>>> feedbackTypeList(@Query("token") String token);

    /**
     * 91、意见反馈页
     * token	String	是
     * Type	Int	是		反馈类型ID
     * content	String	是		反馈内容
     */
    @GET("FeedbackSubmit.aspx")
    Call<NetBaseEntity> feedbackSubmit(@Query("token") String token, @Query("Type") String type, @Query("content") String content);

    /**
     * 74、会员银行卡
     */
    @GET("UserCenterBankCard.aspx")
    Call<NetBaseEntity<List<BankCard>>> userCenterBankCard(@Query("token") String token);

    /**
     * 75、添加银行卡
     * <p>
     * token 	string	是
     * Name	String	是		持卡人
     * Number	String	是		卡号
     * Bank	String	是		开户行名称
     */
    @GET("UserCenterBankCardAdd.aspx")
    Call<NetBaseEntity> userCenterBankCardAdd(@Query("token") String token, @Query("Name") String name, @Query("Number") String number, @Query("Bank") String bank);


    /**
     * 45、订单结算页(获取结算信息)
     *
     * @param token
     * @param params
     * @return
     */
    @GET("GetDefaultOrderInfo.aspx")
    Call<NetBaseEntity<OrderInfo>> getDefaultOrderInfo(@Query("token") String token, @QueryMap Map<String, String> params);

    /**
     * 46、更新结算信息
     *
     * @param token
     * @param params
     * @return
     */
    @GET("GetOrderTotal.aspx")
    Call<NetBaseEntity<OrderInfo>> getOrderTotal(@Query("token") String token, @QueryMap Map<String, String> params);

    /**
     * 52、配送方式列表
     *
     * @param token
     * @param id    店铺ID
     * @return
     */
    @GET("DeliveryWayList.aspx")
    Call<NetBaseEntity<DeliveryWay>> deliveryWayList(@Query("token") String token, @Query("id") int id);

    /**
     * 53、获取会员店铺优惠券
     *
     * @param token
     * @param id    店铺ID
     * @return
     */
    @GET("ShopOrderCoupon.aspx")
    Call<NetBaseEntity<Coupon>> shopOrderCoupon(@Query("token") String token, @Query("id") int id);

    /**
     * 47、生成订单
     *
     * @param token
     * @param params
     * @return
     */
    @GET("CreateOrder.aspx")
    Call<NetBaseEntity<CreateOrder>> createOrder(@Query("token") String token, @QueryMap Map<String, String> params);

    /**
     * 54、获取订单微信支付参数
     *
     * @param ordernumber 订单号
     * @param useBlance   是否使用余额支付
     * @return
     */
    @GET("GetOrderWeiXinPayParam.aspx")
    Call<NetBaseEntity<WeiXinPay>> getOrderWeiXinPayParam(@Query("token") String token, @Query("ordernumber") String ordernumber, @Query("UseBlance") String useBlance);

    /**
     * 96、个人中心-我的优惠券
     *
     * @param token
     * @param type  1：未使用
     *              2：已过期
     *              3：已使用
     * @return
     */
    @GET("UserCenterCouponList.aspx")
    Call<NetBaseEntity<Coupon>> userCenterCouponList(@Query("token") String token, @Query("type") int type);

    /**
     * 72、申请提现
     * token 	String	是
     * type	Int	否		0=获取提现页信息，1=保存提现信息
     * money	Decimal	否		提现金额
     * id	Int	否		银行ID不传返回已经绑定银行
     *
     * @param token
     * @param type
     * @return
     */
    @GET("UserCenterWithdrawal.aspx")
    Call<UserCenterWithdrawal> userCenterWithdrawal(@Query("token") String token, @Query("type") int type);

    /**
     * 75、提现记录页
     *
     * @param token
     * @return
     */
    @GET("UserCenterWithdrawalList.aspx")
    Call<WithdrawalLog> userCenterWithdrawalList(@Query("token") String token);

    /**
     * 提现申请保存
     *
     * @param token
     * @param type
     * @param money
     * @param id
     * @return
     */
    @GET("UserCenterWithdrawal.aspx")
    Call<WithdrawalsSave> userCenterWithdrawal(@Query("token") String token, @Query("type") int type, @Query("money") String money, @Query("id") String id);

    /**
     * 26、取消订单
     */
    @GET("Order_Cancel.aspx")
    Call<NetBaseEntity> orderCancel(@Query("token") String token, @Query("orderid") String orderid);

    /**
     * 65、评价晒单
     * <p>
     * token 	String	是
     * orderNumber	String	是	33999222	订单号
     * Fwtd	int	是	5	服务态度
     * Cpzl	Int	是	5	产品质量
     * Shsd	Int	是	5	送货速度
     * Content	String	是	不错	评价内容
     * isimg	String	是	1	是否晒图1=是
     * Productlist	String	否	1:5,2:5	产品打分
     *
     * @return
     */
    @GET("AddShowProduct.aspx")
    Call<NetBaseEntity<AddShowProduct>> addShowProduct(@Query("token") String token,
                                                       @Query("orderNumber") String orderNumber,
                                                       @Query("Fwtd") int fwtd,
                                                       @Query("Cpzl") int cpzl,
                                                       @Query("Shsd") int shsd,
                                                       @Query("Content") String content,
                                                       @Query("isimg") int isimg,
                                                       @Query("Productlist") String productlist);

    /**
     * 66、上传晒单图片
     * token 	String	是
     * Type	Int	是	1	2=评价晒单
     * showid	int	是	1	评论ID
     * file_img	File	是		上传图片文件
     * file_img2	File	是		上传图片文件
     * file_img3	File	是		上传图片文件
     */
    @Multipart
    @POST("SaveImage.aspx")
    Call<NetBaseEntity> saveImage(@Query("token") String token, @Query("Type") int type, @Query("showid") int showid,
                                  @Part("file_img") MultipartBody.Part file_img1,
                                  @Part("file_img2") MultipartBody.Part file_img2,
                                  @Part("file_img3") MultipartBody.Part file_img3);

    //搜索页
    @GET("EShop.aspx")
    Call<NetBaseEntity<ShopInfoEntity>> getShopInfo(@Query("token") String token,@Query("id") String userId);

    @GET("ProductList.aspx")
    Call<NetBaseEntity<ArrayList<ProdectItemEntity>>> getProdectList(@Query("cid") String cid, @Query("key") String key, @Query("p") String p, @Query("Size") String Size,
                                                                     @Query("sp") String sp, @Query("sc") String sc, @Query("bid") String bid, @Query("lp") String lp,
                                                                     @Query("hp") String hp);


    @GET("SpecialChannelList.aspx")
    Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> getSpecialOfferList(@Query("state") int state, @Query("page") int page);

    @GET("IntegralMall.aspx")
    Call<DemoFormtEntity> getCreditList();

    @GET("IntegralMallCate.aspx")
    Call<ClassifyEntity> getClassify();

    @GET("IntegralMallSalesRankingList.aspx")
    Call<RankingEntity> getRankingList(@Query("p") int page, @Query("size") String size);

    @GET("IntegralMallPointEarn.aspx")
    Call<ProfitEntity> getProfit(@Query("token") String token);

//    @GET("GetShopPositionList.aspx")
//    Call<ShopPositionList> getShopPositionList(@Query("x") String x, @Query("y") String y, @Query("oa") String od);

    @GET("GetShopPositionList.aspx")
    Call<NetBaseEntity<ShopList>> getHotShopByS(@Query("x") String x, @Query("y") String y, @Query("os") String os, @Query("p") String p);

    @GET("GetShopPositionList.aspx")
    Call<NetBaseEntity<ShopList>> getHotShopByD(@Query("x") String x, @Query("y") String y, @Query("od") String od, @Query("p") String p, @Query("size") int size);

    @GET("DeleteDeliveryInfo.aspx")
    Call<NetBaseEntity> deleteDeliveryInfo(@Query("token") String token, @Query("id") String id);

    @GET("GetSearchResult.aspx")
    Call<SearchResultBean> getSearchResult(@Query("k") String k, @Query("x") String x, @Query("y") String y);

    @GET("ProductInfo.aspx")
    Call<ProInfo> getProInfo(@Query("id") int id,@Query("token") String token);

    @GET("AddProductCollect.aspx")
    Call<NetBaseEntity> addProductCollect(@Query("token") String token,@Query("pid") int pid);

    @GET("AddShopCollect.aspx")
    Call<NetBaseEntity> addShopCollect(@Query("token") String token,@Query("shopid") int shopid);

    @GET("CollectShopDelete.aspx")
    Call<NetBaseEntity> deleteShopCollect(@Query("token") String token,@Query("shopid") int shopid);

    @GET("EShopInfo.aspx")
    Call<ShopInformationEntity> getShopInformation(@Query("Id") String id);

    @GET("EShopCategoryList.aspx")
    Call<ShopCategoryEntity> getShopClassify(@Query("Id") String id);

    @GET("GetEShopProductList.aspx")
    Call<HotGoodsEntity> getHotGoods(@Query("Id") String id, @Query("cid")  String cid, @Query("p")  int page, @Query("size")  String pageSize);

    @GET("GetEShopEvaluate.aspx")
    Call<EvaluateEntity> getEvaluate(@Query("Id") String id);

    @GET("GetEShopEvaluateContent.aspx")
    Call<EvaluatesEntity> getEvaluates(@Query("id") String id, @Query("t") String status, @Query("p") int page, @Query("size") String pageSize);

    @GET("CollectProductDelete.aspx")
    Call<NetBaseEntity> deleteCollectProduct(@Query("token") String token,@Query("pid") int pid);

    @GET("Order_Sign.aspx")
    Call<NetBaseEntity> orderSign(@Query("token") String token,@Query("orderid") String orderid);

    @GET("GetAreaDictionary.aspx")
    Call<City> getAreaDictionary(@Query("pid") int pid);

    @GET("GetDeliveryInfo.aspx")
    Call<DeliveryInfo> getDeliveryInfo(@Query("token") String token,@Query("id") int id);

    @GET("MemberOrderInfo.aspx")
    Call<MemberOrderInfo> memberOrderInfo(@Query("token") String token, @Query("id") String id);

    @GET("IntegralMallProductDatails.aspx")
    Call<MallProductDatails> getMallProductDatails(@Query("id") String id);

    @GET("IntegralMallMyList.aspx")
    Call<MallMyList> integralMallMyList(@Query("token") String token, @Query("cid") int cid, @Query("o") int o, @Query("p") int p, @Query("psize") int psize);
}
