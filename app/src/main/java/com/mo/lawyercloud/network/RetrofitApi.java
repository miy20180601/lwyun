package com.mo.lawyercloud.network;


import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.ContactBean;
import com.mo.lawyercloud.beans.apiBeans.HomeBean;
import com.mo.lawyercloud.beans.apiBeans.LegalBean;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.beans.apiBeans.RecruitmentBean;
import com.mo.lawyercloud.beans.apiBeans.TimeMsgBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;

import java.util.List;

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
     * 修改密码
     * password       是   原密码
     * newPassword    是   新密码
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("security/password/edit")
    Observable<BaseEntity<Object>> updatePassword(@Body RequestBody params);

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
     * 问题反馈
     * 参数："content": "问题反馈反馈反馈"
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("feedback/submit")
    Observable<BaseEntity<Object>> submitFeedback(@Body RequestBody params);

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
     * 客服信息
     */
    @GET("customerService")
    Observable<BaseEntity<ContactBean>> customerService();

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
     * 上传头像
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("upload/avatar")
    Observable<BaseEntity<UploadFileBean>> uploadAvatar(
            @Body RequestBody params
    );
    /**
     * 律师添加空闲时间
     * {
     "startTime":"1526353200000",
     "endTime": "1526356800000"
     }
     ?pageNo=1&pageSize=10
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("timeMsg/list")
    Observable<BaseEntity<TimeMsgBean>> getTimeMsgList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
            );
    /**
     * 律师添加空闲时间
     * {
     "startTime":"1526353200000",
     "endTime": "1526356800000"
     }
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("timeMsg/create")
    Observable<BaseEntity<Object>> createTimeMsg(@Body RequestBody params);
}

