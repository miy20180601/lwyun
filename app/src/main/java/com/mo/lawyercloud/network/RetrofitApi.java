package com.mo.lawyercloud.network;


import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.HomeBean;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
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
    Observable<BaseEntity<String>> register(@Body RequestBody params
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
    Observable<BaseEntity<String>> logout(@Body RequestBody params);

    /**
     * 修改密码
     * password       是   原密码
     * newPassword    是   新密码
     */
    @Headers("Content-Type: application/json; charset=utf-8")
    @PUT("security/password/edit")
    Observable<BaseEntity<String>> updatePassword(@Body RequestBody params);

    /**
     * 首页内容
     */
    @GET("home/index")
    Observable<BaseEntity<HomeBean>> homeIndex();






}

