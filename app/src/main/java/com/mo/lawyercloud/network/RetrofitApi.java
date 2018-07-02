package com.mo.lawyercloud.network;


import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.AdvisoryOrderBean;
import com.mo.lawyercloud.beans.apiBeans.BankCardInfo;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.BillDetailBean;
import com.mo.lawyercloud.beans.apiBeans.BillingRecordsBean;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.ContactBean;
import com.mo.lawyercloud.beans.apiBeans.FeeDescriptionlBean;
import com.mo.lawyercloud.beans.apiBeans.HomeBean;
import com.mo.lawyercloud.beans.apiBeans.InvoiceListBean;
import com.mo.lawyercloud.beans.apiBeans.LegalBean;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.beans.apiBeans.OrderAdvisoryBean;
import com.mo.lawyercloud.beans.apiBeans.OrderListBean;
import com.mo.lawyercloud.beans.apiBeans.PayResultBean;
import com.mo.lawyercloud.beans.apiBeans.RecruitmentBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveOrderBean;
import com.mo.lawyercloud.beans.apiBeans.TimeMsgBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveTimeBean;
import com.mo.lawyercloud.beans.apiBeans.SolicitorDetailBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.beans.apiBeans.WechatOrderBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


/**
 * Created by Mohaifeng on 2017/6/7.
 */

public interface RetrofitApi {


    /**
     * 请求验证码
     * 参数：手机号码 mobile
     */
    @GET("msm/register")
    Observable<BaseEntity<RegisterResult>> getMsmCode(
            @Query("mobile") String mobile
    );

    /**
     * 注册接口
     * username     是       手机号码
     * password     是       32位MD5加密过的密码，示例报文内的密码明文为123456
     * type         是       用户类型 1普通用户 2律师
     * mobileCode   是       验证码
     * realName     是       真实姓名
     * company      否       执业机构
     * location     否       所在地
     * profession   否       律师专业
     * resume       否       个人简历
     * paperwork    否       证件照
     * channels     否       擅长领域 以逗号分隔
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("security/register")
    Observable<BaseEntity<RegisterResult>> register(@Body RequestBody params
    );

    /**
     * 登录接口
     * 参数：手机号码 phone  密码 password
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("security/login")
    Observable<BaseEntity<RegisterResult>> login(@Body RequestBody params);

    /**
     * 查询当前用户信息
     */
    @GET("security/info")
    Observable<BaseEntity<MemberBean>> getUserInfo();


    /**
     * 查询可选擅长领域
     */
    @GET("home/channels")
    Observable<BaseEntity<List<ChannelBean>>> getChannels();

    /**
     * 退出登录
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("security/logout")
    Observable<BaseEntity<Object>> logout();

    /**
     * 问题反馈
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("feedback/submit")
    Observable<BaseEntity<Object>> submitFeedback(@Body RequestBody params);

    /**
     * 修改密码
     * password       是   原密码
     * newPassword    是   新密码
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("security/password/edit")
    Observable<BaseEntity<Object>> updatePassword(@Body RequestBody params);

    /**
     * 修改密码
     * username       是   手机号
     * password       是   新密码
     * mobileCode     是   验证码
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("security/password/forget")
    Observable<BaseEntity<Object>> forgetPassword(@Body RequestBody params);

    /**
     * 修改用户资料
     * realName     否       姓名
     * gender       否       性别
     * location     否       所在地
     * company      否       执业机构
     * resume       否       个人简历
     * profession   否       律师专业
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("security/modifyInfo")
    Observable<BaseEntity<Object>> modifyInfo(@Body RequestBody params);

    /**
     * 首页内容
     */
    @GET("home/index")
    Observable<BaseEntity<HomeBean>> homeIndex();

    /**
     * 新手指引
     */
    @GET("guide")
    Observable<BaseEntity<WebViewBean>> noviceGuidelines();

    /**
     * 关于我们
     */
    @GET("aboutus")
    Observable<BaseEntity<WebViewBean>> aboutus();

    /**
     * 招聘信息
     */
    @GET("recruitment")
    Observable<BaseEntity<BaseListEntity<RecruitmentBean>>> recruitment(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize);

    /**
     * 法规常识
     */
    @GET("legalKnowledge")
    Observable<BaseEntity<BaseListEntity<LegalBean>>> legalKnowledge(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize);

    /**
     * 我的客服
     */
    @GET("customerService")
    Observable<BaseEntity<ContactBean>> customerService();

    /**
     * 律师列表页
     * name     否       律师姓名
     * location   否       地区
     * channel    否       擅长领域    多个以逗号分隔
     * pageNo     否       页码  默认为1
     * pageSize   否       显示数量    默认为10
     */
    @GET("home/solicitor/list")
    Observable<BaseEntity<BaseListEntity<SolicitorDetailBean>>> solicitorList(
            @Query("name") String name,
            @Query("location") String location,
            @Query("channel") Integer channel,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize);

    /**
     * 律师详情页
     * id   是   律师id
     */
    @GET("home/solicitor/info")
    Observable<BaseEntity<SolicitorDetailBean>> solicitorInfo(
            @Query("id") Integer id);

    /**
     * 获取对应律师的可预约时间
     * id       是   律师id
     * pageNo   否   默认为1
     * pageSize 否   默认为5
     */
    @GET("timeMsg/listReserve")
    Observable<BaseEntity<BaseListEntity<ReserveTimeBean>>> reserveTimeList(
            @Query("id") Integer id,
            @Query("pageNo") Integer pageNo,
            @Query("pageSize") Integer pageSize

    );


    /**
     * 上传头像
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("upload/avatar")
    Observable<BaseEntity<UploadFileBean>> uploadAvatar(
            @Body RequestBody params
    );

    /**
     * 律师查看自己的时间列表
     * {
     * "startTime":"1526353200000",
     * "endTime": "1526356800000"
     * }
     * ?pageNo=1&pageSize=10
     */
    @GET("timeMsg/list")
    Observable<BaseEntity<TimeMsgBean>> getTimeMsgList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    /**
     * 律师添加空闲时间
     * {
     * "startTime":"1526353200000",
     * "endTime": "1526356800000"
     * }
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("timeMsg/create")
    Observable<BaseEntity<Object>> createTimeMsg(@Body RequestBody params);

    /**
     * 通用图片上传
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("upload/image")
    Observable<BaseEntity<UploadFileBean>> uploadImage(
            @Body RequestBody params
    );

    /**
     * 视频预约
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("order/submit")
    Observable<BaseEntity<Object>> reserveSubmit(
            @Body RequestBody params
    );


    /**
     * 咨询管理
     */
    @GET("order/advisory")
    Observable<BaseEntity<BaseListEntity<AdvisoryOrderBean>>> myAdvisoryOrder(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    /**
     * 我的预约
     * status   否   0全部  1审核中   2已审核
     */
    @GET("order/list")
    Observable<BaseEntity<BaseListEntity<ReserveOrderBean>>> myReserveOrder(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("status") int status
    );

    /**
     * 预约通知
     *
     * @return ?pageNo=1&pageSize=10&status=0 0、全部
     * 1、审核中
     * 2.已审核
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("order/list")
    Observable<BaseEntity<OrderListBean>> getOrderList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("status") int status
    );


    /**
     * 我的发票
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("invoice/list")
    Observable<BaseEntity<InvoiceListBean>> getInvoiceList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    /**
     * 获取账单记录
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("billRecord/list")
    Observable<BaseEntity<BaseListEntity<BillingRecordsBean>>> billRecordList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    /**
     * 删除时间
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("timeMsg/remove")
    Observable<BaseEntity<Object>> removeTime(
            @Body RequestBody params
    );

    /**
     * 申请发票
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("invoice/register")
    Observable<BaseEntity<Object>> registerInvoice(@Body RequestBody params
    );

    /**
     * 视频聊天开始时调用
     * id   是   订单id
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("order/orderStart")
    Observable<BaseEntity<Object>> videoOrderStart(@Body RequestBody params
    );

    /**
     * 视频聊天结束时调用
     * id   是   订单id
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("order/orderEnd")
    Observable<BaseEntity<Object>> videoOrderEnd(@Body RequestBody params
    );

    /**
     * 拒绝预约
     * id   是   订单id
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("order/unreserve")
    Observable<BaseEntity<Object>> unreserve(@Body RequestBody params);

    /**
     * 接受预约
     * id   是   订单id
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("order/reserve")
    Observable<BaseEntity<Object>> reserve(@Body RequestBody params);

    /**
     * 微信支付统一下单
     * amount   是   金额
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("recharge/wechat")
    Observable<BaseEntity<WechatOrderBean>> rechargeWechat(@Body RequestBody params);

    /**
     * 提现
     * price   是   金额
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("withdrawal/submit")
    Observable<BaseEntity<Object>> withdrawal(@Body RequestBody params);


    /**
     * 获取支付结果
     * id   是   支付订单id
     */
    @GET("pay/business/queryResult")
    Observable<BaseEntity<PayResultBean>> paymentResults(@Query("id") int id);

    /**
     * 服务协议
     */
    @GET("serviceAgreement")
    Observable<BaseEntity<WebViewBean>> serviceAgreement();

    /**
     * 账单详情与充值记录
     * id   是
     */
    @GET("billRecord/detail")
    Observable<BaseEntity<BillDetailBean>> billRecordDetail(@Query("id") int id);

    /**
     * 收费标准
     */
    @GET("feeDescription")
    Observable<BaseEntity<FeeDescriptionlBean>> feeDescription();

    /**
     * 推送消息
     * alias        是       用户别名（为username）
     * alert         是      推送消息标题
     * content      是       推送消息内容
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("push/messageToOne")
    Observable<BaseEntity<Object>> pushMessageToOne(@Body RequestBody params);

    /**
     * 添加或修改银行卡信息
     * alias        是       用户别名（为username）
     * alert         是      推送消息标题
     * content      是       推送消息内容
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("bankCard/modify")
    Observable<BaseEntity<Object>> modifyBankCard(@Body RequestBody params);

    /**
     * 获取银行详情
     */
    @GET("bankCard/info")
    Observable<BaseEntity<BankCardInfo>> bankCardInfo();

    /**
     * 上传文件
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("upload/file")
    Observable<BaseEntity<Object>> uploadFile(@Body RequestBody params);

}

